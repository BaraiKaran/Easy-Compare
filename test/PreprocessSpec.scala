import org.scalatest._
import services.preprocess

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

class PreprocessSpec extends FlatSpec {

  "convertListToString" should "Convert input list to string" in {
    val strFromList = preprocess.convertListToString(Try(List("Hello", "This is a", "Scala Test")))
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
    assert(stringToSplit === Success(List("Hello", "Thisisa", "ScalaTest")))
  }

  "getFileName" should "get the name of the file from absolute path" in {
    val str = "C:\\Example\\example.txt"
    assert("example.txt" === preprocess.getFileName(str))
  }

    "readTextFile" should "read the file text and return lowercase list of string" in {
    val filetext: Try[List[String]] = preprocess.readTextFile("resources/testexample.txt")
    val lststr = filetext match {
      case Success(x) => x
    }
    assert(lststr == List("you do not need to add scalatest to your build explicitly. ", "the proper version of scalatest will be brought in automatically as a transitive dependency of scalatest + play. ", "you will, however, need to select a version of scalatest + play that matches your play version. ", "you can do so by checking the releases compatibility matrix for scalatest + play."))
  }
}
