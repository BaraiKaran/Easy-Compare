package models

import javax.xml.datatype.DatatypeConstants
import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.Try


object InsertToDb {

  case class document(id: Option[Int], Document_Name: String, Document_Text: String)


  class documents(tag: Tag) extends Table[document](tag, "tblDocumentInformation") {
    def id = column[Int]("ID", O.PrimaryKey,O.AutoInc)
    def document_name = column[String]("Document_Name")
    def document_text = column[String]("Document_Text")
    def * = (id.?, document_name, document_text) <> (document.tupled,document.unapply)
  }

  def insert(sentences : String, filename: String) = {
    val db = Database.forConfig("postgresDb")
    val doc = TableQuery[documents]
    try {
      Await.result(db.run(DBIO.seq(
        doc.schema.createIfNotExists,
        doc += document(None,filename,sentences),
        doc.result.map(println))),Duration.Inf)
    } finally db.close()
  }
}