import org.scalatest._
import services.ValidationService

class ValidationSpec extends FlatSpec {

  "getFileType" should "return type of the file" in {
    val filetype = ValidationService.getFileType("C:\\Example\\example.txt")
    assert(filetype == "txt")
  }
}
