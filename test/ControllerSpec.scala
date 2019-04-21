import controllers.UploadController
import org.scalatest._
import org.scalatest.concurrent.{Futures, ScalaFutures}
import org.scalatest.time.{Millis, Seconds, Span}
import play.api.mvc.{Result, Results}
import play.api.test.{FakeRequest, Helpers}
import play.api.test.Helpers._

import scala.concurrent.Future
class ControllerSpec extends FlatSpec with Results with Matchers with Futures with ScalaFutures{

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  "Controller forms" should "be valid" in {
    val controller = new UploadController(Helpers.stubControllerComponents())
    val result = controller.forms.apply(FakeRequest())
    whenReady(result) {i=> i.header.status shouldBe 200}
  }
}
