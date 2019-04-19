package services

import java.io.ByteArrayInputStream
import java.nio.file.{Files, Paths}
import java.util

import models.InteractionWithDb
import org.apache.poi.xwpf.usermodel.{XWPFDocument, XWPFRun}
import play.api.Logger

import scala.collection.JavaConversions._
import scala.util.{Failure, Success, Try}

object preprocess {
  val logger: Logger = Logger(this.getClass())
  /**
    * this function applies the necessary text pre-processing required for document comparision.
    * @param filePath absolute path of the file
    */
    def apply(filePath: String) : String  = {
        val total = new StringBuilder
        if (ValidationService.getFileType(filePath) == "docx") {
          val path = Paths.get(filePath)
          val byteData = Files.readAllBytes(path)
          val doc = new XWPFDocument(new ByteArrayInputStream(byteData))
          for (para <- doc.getParagraphs) {
            val runs = para.getRuns
            for (run <- runs) {
              total.append(run.getText(-1))
            }
          }
          textPreProcessAndStore(Try(total.toString()), filePath)
        }
        else if (ValidationService.getFileType(filePath) == "txt") {
          val text: Try[List[String]] = readTextFile(filePath)
          val textOfDoc = convertListToString(text)
          textPreProcessAndStore(textOfDoc, filePath)
        }
        "File uploaded Successfully"
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
  def removeWhiteSpaces(str: Try[String]) : Try[String] = for(st <- str) yield st.replaceAll("\\s","")

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

  def splitText(txt : Try[String]) : Try[List[String]] = for (t<-txt) yield t.split("\\.").toList


  def textPreProcessAndStore(text: Try[String],filePath: String) : Unit = {
    val textWithoutSpaces: Try[String] = removeWhiteSpaces(text)
    val sentences: Try[List[String]] = splitText(textWithoutSpaces)
    val hashSentences: Try[List[Int]] = Comparison.hashContentsOfList(sentences)
    val hashSentencesStr =  for(hs <- hashSentences) yield hs.mkString(",")
    InteractionWithDb.insert(hashSentencesStr, getFileName(filePath), sentences.get.mkString("-"))
  }
}