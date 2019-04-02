package controllers
import play.api.mvc._
import services.{BasicForm, Comparison, preprocess}
import models.InteractionWithDb
import javax.inject.Inject
import akka.actor.ActorSystem
import akka.stream.Materializer

class UploadController @Inject()(cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  /**
    * GET request to load the form.
    */
  def forms = Action { implicit request: Request[AnyContent] =>
    val name = InteractionWithDb.getUploadedFileNames()
    Ok(views.html.upload(BasicForm.form,name.map(x=> (x,x))))
  }

  /**
    * POST request to upload the file and it's details in the database
    */
  def simpleFormPost = Action { implicit request =>
    //val formData: String = BasicForm.form.bindFromRequest.get.path
    val file = request.body.asMultipartFormData.map(_.files)
    val message = file map { fileseq => fileseq map { file => preprocess.apply(file.filename) } }
    Ok(message.get.head)
  }
  /**
    *
    * @return Comparision score of two documents
    */
  def compare  = Action { implicit request =>
    val encoded: Option[Map[String, Seq[String]]] = request.body.asFormUrlEncoded
    val doc1: Option[String] = for (b <- encoded; z <- b.get("document1"); s <- z.headOption ) yield s
    val doc2: Option[String] = for (b <- encoded; z <- b.get("document2"); s <- z.headOption ) yield s
    Ok(Comparison.getDocument(doc1, doc2).toString)
  }
}
