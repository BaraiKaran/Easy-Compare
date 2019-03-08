package controllers

import javax.inject.Inject
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import services.BasicForm
import services.preprocess
import slick.lifted.Tag
import slick.model.Table
import models.InsertToDb
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try
import java.awt.Desktop

class UploadController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def forms = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.upload(BasicForm.form))
  }

  def simpleFormPost = Action { implicit request =>
    val formData: BasicForm = BasicForm.form.bindFromRequest.get
    val sentences = preprocess.apply(formData.path).get.mkString(",")
    InsertToDb.insert(sentences,preprocess.getFileName(formData.path).get)
    Ok("done")
  }
}
