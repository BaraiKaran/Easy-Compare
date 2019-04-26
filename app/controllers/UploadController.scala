package controllers
import java.nio.file.Paths

import play.api.mvc._
import services.{BasicForm, Comparison, preprocess}
import models.InteractionWithDb
import javax.inject.Inject
import akka.actor.ActorSystem
import akka.stream.Materializer
import org.slf4j
import org.slf4j.LoggerFactory
import play.api.Logger
import play.api.libs.Files

import scala.collection.immutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

class UploadController @Inject()(cc: ControllerComponents) extends BaseUploadController(cc) {

  def getUploadedFileNames: Future[Seq[String]] = InteractionWithDb.getUploadedFileNames
}

abstract class BaseUploadController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport with InitializeController {

  /**
    * GET request to load the form.
    */
  def forms: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] => {
    val wsf = getUploadedFileNames.recover{ case x: Throwable => UploadController.logger.error("cannot get previously loaded filenames", x); Nil}
    wsf foreach (ws => UploadController.logger.info(s"""UploadController.forms: filenames=${ws.mkString(", ")}"""))
    val tsf = for (ws <- wsf) yield for (w <- ws) yield w->w
    for (ts <- tsf) yield Ok(views.html.upload(BasicForm.form, ts))
  }
  }

  /**
    * POST request to upload the file and it's details in the database
    */


  def simpleFormPost: Action[AnyContent] = Action.async { implicit request =>
     {
       val wso = request.body.asMultipartFormData.map(_.files)
       val wfso = wso map { fileseq => fileseq map { file => preprocess.apply(file.filename) } }
       val wsfo = for (wfs <- wfso) yield Future.sequence(wfs)
       val wsof = InteractionWithDb.sequence(wsfo).recover{ case x: Throwable => UploadController.logger.error("cannot get upload filename", x); None}
       val wf = for (wso <- wsof) yield wfso.fold("") { x => x.mkString }
       for (w <- wf) yield Ok(w)
    }
  }

  /**
    *
    * @return Comparision score of two documents
    */
  def compare: Action[AnyContent] = Action.async {
      implicit request =>
        val encoded: Option[Map[String, Seq[String]]] = request.body.asFormUrlEncoded
        val doc1: Option[String] = for (b <- encoded; z <- b.get("Document1"); s <- z.headOption) yield s
        val doc2: Option[String] = for (b <- encoded; z <- b.get("Document2"); s <- z.headOption) yield s
        Comparison.getDocument(doc1, doc2).map(score => Ok("plagiarism score: " + score.toString))
      }

}

object UploadController {
  val logger: slf4j.Logger = LoggerFactory.getLogger("application")
}

trait InitializeController {

  def getUploadedFileNames: Future[Seq[String]]
}