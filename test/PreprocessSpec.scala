import org.scalatest._
import services.preprocess
import services.Comparison

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

  "splitText" should "Split the text and return as List" in {
    val stringToSplit = preprocess.splitText(Try("Hello.Thisisa.ScalaTest"))
    assert(stringToSplit === Success(List("Hello","Thisisa","ScalaTest")))
  }

  "getSimilarityScore" should "return jaccard similarity score of two documents" in {
    val list1 = List(0,1,2,5,6)
    val list2 = List(0,2,3,4,5,7,9)
    val list3 = List(1,2,3)
    val list4 = List(3,2,1)
    assert(Comparison.getSimilarityScore(list1,list2) == 0.33)
    assert(Comparison.getSimilarityScore(list3,list4) == 1.0)
  }

}
