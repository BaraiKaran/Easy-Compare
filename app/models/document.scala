package models

import akka.actor.Status.Success
import javax.xml.datatype.DatatypeConstants
import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.Try


object InteractionWithDb {

  case class document(id: Option[Int], Document_Name: String, Document_Text: String)
  val db = Database.forConfig("postgresDb")
  val doc = TableQuery[documents]

  class documents(tag: Tag) extends Table[document](tag, "tblDocumentInformation") {
    def id = column[Int]("ID", O.PrimaryKey,O.AutoInc)
    def document_name = column[String]("Document_Name")
    def document_text = column[String]("Document_Text")
    def * = (id.?, document_name, document_text) <> (document.tupled,document.unapply)
  }

  def insert(sentences : String, filename: String) = {
    try {
      Await.result(db.run(DBIO.seq(
        doc.schema.createIfNotExists,
        doc += document(None,filename,sentences),
        doc.result.map(println))),Duration.Inf)
    } finally db.close()
  }

  def getfileContents(filename1: String, filename2: String) = {
    val file1 = db.run(doc.filter(_.document_name === filename1).result).toString().split("\\,").map(_.trim).toList
    val file2 = db.run(doc.filter(_.document_name === filename2).result).toString().split("\\,").map(_.trim).toList
    (file1,file2)
  }

  def getUploadedFileNames() = {
    Await.result(db.run(doc.map(_.document_name).result),Duration.Inf)
  }
}