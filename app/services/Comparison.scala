package services

import models.InteractionWithDb

import scala.util.Try
import scala.util.hashing.MurmurHash3
object Comparison {


  /**
    *
    * @param list list of all the sentences as individual elements
    * @return hash of each individual sentence using Murmurhash
    */
  def hashContentsOfList(list: Try[List[String]]) = for (item <- list.get) yield MurmurHash3.stringHash(item)


  /**
    * *
    * @param list1 contains list of hash of all sentences in document 1
    * @param list2 contains list of hash of all sentences in document 2
    * @return similarity score using Jaccard similarity
    */

  def getSimilarityScore(list1: List[String],list2: List[String]) : Double = {
    val intersectLength = list1.intersect(list2).length.toDouble
    val unionLength = list1.union(list2).toSet.size.toDouble
     "%.2f".format(intersectLength/unionLength).toDouble
  }

  /**
    * This function gets the files from the database and pass them to similarity score function.
    * @param filename1 Name of file 1 selected from dropdown
    * @param filename2 Name of file 2 selected from dropdown
    * @return similarity score of the two documents
    */

  def getDocument(filename1: Option[String], filename2: Option[String]) : Double = {
    val filename = InteractionWithDb.getfileContents(filename1,filename2)
    getSimilarityScore(filename._1,filename._2)
  }
}
