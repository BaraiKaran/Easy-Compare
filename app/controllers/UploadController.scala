package controllers

import javax.inject.Inject
import play.api.mvc._
import services.BasicForm
import services.preprocess

import scala.concurrent.ExecutionContext


class UploadController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {
  def forms = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.upload(BasicForm.form))
  }

  def simpleFormPost = Action { implicit request =>
    val formData: BasicForm = BasicForm.form.bindFromRequest.get
    Ok(preprocess.apply(formData.path).toString())
  }
}
