package models

import akka.actor.Status.Success
import javax.xml.datatype.DatatypeConstants
import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.Try


object InteractionWithDb {

  case class document(id: Option[Int], Document_Name: String, Document_Text: String)
  val db = Database.forConfig("postgresDb")
  val doc = TableQuery[documents]

  /**
    * Create a table in Postgresql if the table is not already present.
    * @param tag
    */

  class documents(tag: Tag) extends Table[document](tag, "tblDocumentInformation") {
    def id = column[Int]("ID", O.PrimaryKey,O.AutoInc)
    def document_name = column[String]("Document_Name")
    def document_text = column[String]("Document_Text")
    def * = (id.?, document_name, document_text) <> (document.tupled,document.unapply)
  }

  /**
    * This function inserts sentences and filename in the database.
    * @param sentences string of hashes of all the sentences in the document
    * @param filename name of the file
    */
  def insert(sentences : Try[String], filename: String) = {

      Await.result(db.run(DBIO.seq(
        doc.schema.createIfNotExists,
        doc += document(None,filename,sentences.get),
        doc.result.map(println))),Duration.Inf)
        db.close()
  }

  /**
    *
    * @param filename1 name of the file 1
    * @param filename2 name of the file 2
    * @return tuple of list of string which contains hashed value of each sentence.
    */
  def sequence[X](xfo: Option[Future[X]]): Future[Option[X]] = xfo match {
    case Some(xf) => xf map (Some(_))
    case None => Future.successful(None)
  }


  def getComparison(filename1: Option[String], filename2: Option[String]): Future[Option[(List[String], List[String])]] = {
    val zipBothFiles = getZipped(filename1, filename2)
    for ((wso1, wso2) <- zipBothFiles) yield for (ws1 <- wso1; ws2 <- wso2) yield (ws1, ws2)
  }

  def getfileContents(filename: Option[String]): Future[Option[List[String]]] = {
    val dsfo: Option[Future[Seq[document]]] = for (f <- filename) yield db.run(doc.filter(_.document_name === f).result)
    val dsof: Future[Option[Seq[document]]] = sequence(dsfo)
    for (dso <- dsof) yield for (ds <- dso; d <- ds.headOption) yield d.Document_Text.split("\\,").map(_.trim).toList
  }


  def getZipped(filename1: Option[String], filename2: Option[String]): Future[(Option[List[String]], Option[List[String]])] = {
    val content1 = getfileContents(filename1)
    val content2 = getfileContents(filename2)
    content1 zip content2
  }

  /**
    *
    * @return returns name of all the files uploaded in the database
    */
  def getUploadedFileNames() = {
    Await.result(db.run(doc.map(_.document_name).result), Duration.Inf)
  }
}