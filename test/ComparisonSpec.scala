import org.scalatest._
import org.scalatest.concurrent.{Futures, ScalaFutures}
import services.Comparison

import scala.concurrent.{Await, Future, duration}
import scala.util.{Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

class ComparisonSpec extends FlatSpec with Matchers with Futures with ScalaFutures {


  "calculateSimilarityScore" should "return jaccard similarity score of two documents" in {
    val list1 = List("0","1","2","5","6")
    val list2 = List("0","2","3","4","5","7","9")
    val list3 = List("1","2","3")
    val list4 = List("3","2","1")
    assert(Comparison.calculateSimilarityScore(list1,list2) == 0.33)
    assert(Comparison.calculateSimilarityScore(list3,list4) == 1.0)
  }

  "hashContentsOfList" should
    "hash the inputted list of string using Murmur Hash 3" in {
    val lst1 = Comparison.hashContentsOfList(Try(List("Hello", "my", "name","is","tom")))
    val lst2 = Comparison.hashContentsOfList(Try(List("Hello", "my", "name","is","tom")))
    val cmp1 = for(ls <- lst1) yield ls
    val cmp2 = for (ls <- lst2) yield ls
    assert(cmp1==cmp2)
  }

  "hashContentsOfListNotEqual" should "hash the inputted list of string using Murmur Hash 3" in {
    val lst1 = Comparison.hashContentsOfList(Try(List("Hello", "my", "name","is")))
    val lst2 = Comparison.hashContentsOfList(Try(List("Hello", "my", "name","is","tom")))
    val cmp1 = for(ls <- lst1) yield ls
    val cmp2 = for (ls <- lst2) yield ls
    assert(cmp1!=cmp2)
  }

  "hashContentsOfEmptyList" should "hash the inputted list of string using Murmur Hash 3" in {
    val lst1 = Comparison.hashContentsOfList(Try(List()))
    val lst2 = Comparison.hashContentsOfList(Try(List("Hello", "my", "name","is","tom")))
    val cmp1 = for(ls <- lst1) yield ls
    val cmp2 = for (ls <- lst2) yield ls
    assert(cmp1!=cmp2)
  }


  "getListFromOption" should "return list fro option type" in {
    val lst = Comparison.getListFromOption(Option(List(1,2,3)))
    assert(lst == List(1,2,3))
  }

  "flattenFuture" should "return List[T]" in {
      val lst =  Comparison.flattenFuture(Future(Option(List(1,2,3))))
      whenReady(lst) {i => assert(i==List(1,2,3))}
  }

  "getSimilarityScore" should "return similarity score between two lists" in {
    val score1 = Comparison.getSimilarityScore(Future(Option(List("Welcome","to","easy","compare"))),Future(Option(List("Welcome","to","easy","compare"))))
    val score2 = Comparison.getSimilarityScore(Future(Option(List("Welcome","to","easy","compare"))),Future(Option(List("Welcome","to","easy","compare","documents"))))
    whenReady(score1) {i=> assert(i==1.0)}
    whenReady(score2) {i=> assert(i<1.0)}
  }
}
