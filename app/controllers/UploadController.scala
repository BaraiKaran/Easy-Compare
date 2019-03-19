package controllers

import javax.inject.Inject
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import services.{BasicForm, Comparision, preprocess}
import slick.lifted.Tag
import slick.model.Table
import models.InteractionWithDb
import scala.util.Try
import java.awt.Desktop
import play.api.libs.streams.ActorFlow
import javax.inject.Inject
import akka.actor.ActorSystem
import akka.stream.Materializer

class UploadController @Inject()(cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def forms = Action { implicit request: Request[AnyContent] =>
    val name = InteractionWithDb.getUploadedFileNames()
    Ok(views.html.upload(BasicForm.form,name.map(x=> (x,x))))
  }

  def simpleFormPost = Action { implicit request =>
    //val formData: String = BasicForm.form.bindFromRequest.get.path
    val file = request.body.asMultipartFormData.map(_.files)
    file map { fileseq => fileseq map { file => preprocess.apply(file.filename) } }
    Ok("File uploaded successfully")
  }

  def compare  = Action { implicit request =>  Ok(Comparision.getDocument("test.txt","test.txt").toString)}
}
