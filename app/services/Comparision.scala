package services

import models.InteractionWithDb

import scala.util.Try
import scala.util.hashing.MurmurHash3
object Comparision {

  def hashContentsOfList(list: Try[List[String]]) = for (item <- list.get) yield MurmurHash3.stringHash(item)

  def getSimilarityScore(list1: List[String],list2: List[String]) : Double = {
    val intersectLength = list1.intersect(list2).length.toDouble
    val unionLength = list1.union(list2).toSet.size.toDouble
     "%.2f".format(intersectLength/unionLength).toDouble
  }

  def getDocument(filename1: String, filename2: String) : Double = {
    val filename = InteractionWithDb.getfileContents(filename1,filename2)
    getSimilarityScore(filename._1,filename._2)
  }
}
