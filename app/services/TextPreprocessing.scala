package services

import java.nio.file.Paths
import models.InteractionWithDb

import scala.util.{Failure, Success, Try}

object preprocess {

  /**
    * this function applies the necessary text pre-processing required for document comparision.
    * @param filePath absolute path of the file
    */
    def apply(filePath: String)  = {
      if (ValidationService.getFileType(filePath)=="txt") {
        val text = readTextFile(filePath)
        val textOfDoc = convertListToString(text)
        val textWithoutSpaces = removeWhiteSpaces(textOfDoc)
        val sentences = splitText(textWithoutSpaces)
        val hashSentences: List[Int] = Comparison.hashContentsOfList(sentences)
        InteractionWithDb.insert(hashSentences.mkString(","), getFileName(filePath))
        "File uploaded successfully"
      }
      else {
         "File type should be .txt"
      }
    }

  /**
    *
    * @param filePath path of the file
    * @return List of String with each line as an element
    */
  def readTextFile(filePath : String): Try[List[String]] = Try(scala.io.Source.fromFile(filePath, "ISO-8859-1").getLines().map(_.toLowerCase).toList)

  /**
    *
    * @param str string from which to remove whitespaces
    * @return string without any whitespaces
    */
  def removeWhiteSpaces(str: Try[String]) = for(st <- str) yield st.replaceAll("\\s","")

  /**
    *
    * @param list list => string
    * @return all values of list concatenated as a string
    */
  def convertListToString(list: Try[List[String]]): Try[String] = list.map(_.mkString("."))

  /**
    *
    * @param filePath absolute path of the file
    * @return name of the file
    */
  def getFileName(filePath: String) : String = {
    filePath.substring(filePath.lastIndexOf("\\")+1,filePath.length)
  }

  /**
    * @param txt string to split
    * @return List of string after splitting the string by "."
    */

  def splitText(txt : Try[String]) = for (t<-txt) yield t.split("\\.").toList

  def uploadFile(path: String) = {
  }
}