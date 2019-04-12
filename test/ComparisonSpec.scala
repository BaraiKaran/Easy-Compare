import org.scalatest._
import services.Comparison

import scala.util.{Success, Try}

class ComparisonSpec extends FlatSpec {

  "getSimilarityScore" should "return jaccard similarity score of two documents" in {
    val list1 = List("0","1","2","5","6")
    val list2 = List("0","2","3","4","5","7","9")
    val list3 = List("1","2","3")
    val list4 = List("3","2","1")
    assert(Comparison.calculateSimilarityScore(list1,list2) == 0.33)
    assert(Comparison.calculateSimilarityScore(list3,list4) == 1.0)
  }

  "hashContentsOfList" should "hash the inputted list of string using Murmur Hash 3" in {
    val lst1 = Comparison.hashContentsOfList(Try(List("Hello", "my", "name","is","tom")))
    val lst2 = Comparison.hashContentsOfList(Try(List("Hello", "my", "name","is","tom")))
    assert(lst1.get == lst2.get)
  }
}
