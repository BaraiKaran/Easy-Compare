import org.scalatest._
import services.preprocess

import scala.util.{Success, Try}

class PreprocessSpec extends FlatSpec {

  "convertListToString" should "Convert input list to string" in {
    val strFromList = preprocess.convertListToString(Try(List("Hello","This is a", "Scala Test")))
    assert(strFromList === Success("Hello.This is a.Scala Test"))
  }

  "removeWhiteSpaces" should "Removes spaces from the String" in {
    val str = preprocess.removeWhiteSpaces(Try("Hello This is a Scala test"))
    assert(str === Success("HelloThisisaScalatest"))
  }







}
