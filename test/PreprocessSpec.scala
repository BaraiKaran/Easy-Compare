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

  "removeMultipleWhiteSpaces" should "Remove multiple white spaces in a string" in {
    val str = preprocess.removeWhiteSpaces(Try("Hello    This   is a        Scala test"))
    assert(str === Success("HelloThisisaScalatest"))
  }

  "splitText" should "Split the text and return as List" in {
    val stringToSplit = preprocess.splitText(Try("Hello.Thisisa.ScalaTest"))
    assert(stringToSplit === Success(List("Hello","Thisisa","ScalaTest")))
  }

  "getFileName" should "get the name of the file from absolute path" in {
    val str = "C:\\Example\\example.txt"
    assert("example.txt" === preprocess.getFileName(str))
  }
}
