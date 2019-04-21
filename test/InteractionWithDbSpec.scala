import akka.actor.Status.Success
import org.scalatest._
import models.InteractionWithDb
import models.InteractionWithDb.{document, documents}
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.{Futures, ScalaFutures}
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
class InteractionWithDbSpec extends FlatSpec with Matchers with Futures with ScalaFutures with MockitoSugar {

  "Sequence" should "return Future[Option[X]]" in {
    val lstf: Future[Option[List[Int]]] = InteractionWithDb.sequence(Option(Future(List(1,2,3))))
    whenReady(lstf) {i=> assert(i==Await.result(Future(Option(List(1,2,3))),Duration.Inf))}
  }
}
