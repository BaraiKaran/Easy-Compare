package services

import models.InteractionWithDb

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Success, Try}
import scala.util.hashing.MurmurHash3
import scala.concurrent.ExecutionContext.Implicits.global
object Comparison {


  /**
    *
    * @param list list of all the sentences as individual elements
    * @return hash of each individual sentence using Murmurhash
    */
  def hashContentsOfList(list: Try[List[String]]) : Try[List[Int]] = {
    for (item <- list) yield item.map(x => MurmurHash3.stringHash(x))
  }

  /**
    * *
    * @param list1 contains list of hash of all sentences in document 1
    * @param list2 contains list of hash of all sentences in document 2
    * @return similarity score using Jaccard similarity
    */

  def calculateSimilarityScore(list1: List[String],list2: List[String]) : Double = {
    val intersectLength =  list1.intersect(list2).length.toDouble
    val unionLength = list1.union(list2).toSet.size.toDouble
     "%.2f".format(intersectLength/unionLength).toDouble
  }

  /**
    * This function gets the files from the database and pass them to similarity score function.
    * @param filename1 Name of file 1 selected from dropdown
    * @param filename2 Name of file 2 selected from dropdown
    * @return similarity score of the two documents
    */

  def getDocument(filename1: Option[String], filename2: Option[String]) : Future[Double] = {
    val fname1 = InteractionWithDb.getfileContents(filename1)
    val fname2 = InteractionWithDb.getfileContents(filename2)
    getSimilarityScore(fname1,fname2)
  }

  def flattenFuture[T](lst: Future[Option[List[T]]]) : Future[List[T]] = lst.map(x=> getListFromOption(x))

  def getListFromOption[T](to: Option[List[T]]): List[T] = to.toList.flatten

  def getSimilarityScore(list1: Future[Option[List[String]]], list2: Future[Option[List[String]]]) : Future[Double] = for(ws1<-flattenFuture(list1);ws2<-flattenFuture(list2)) yield calculateSimilarityScore(ws1,ws2)
}
