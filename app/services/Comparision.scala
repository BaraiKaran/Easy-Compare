package services

import scala.util.Try
import scala.util.hashing.MurmurHash3
object Comparision {

  def hashContentsOfList(list: Try[List[String]]) = for (item <- list.get) yield MurmurHash3.stringHash(item)

  def getSimilarityScore(list1: List[Int],list2: List[Int]) : Double = {
    val intersectLength = list1.intersect(list2).length.toDouble
    val unionLength = list1.union(list2).toSet.size.toDouble
     "%.2f".format(intersectLength/unionLength).toDouble
  }
}
