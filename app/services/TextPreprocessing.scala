package services

import java.nio.file.Paths

import models.InteractionWithDb

import scala.util.{Failure, Success, Try}

object preprocess {
    def apply(filePath: String)  = {
        val text = readTextFile(filePath)
        val textOfDoc = convertListToString(text)
        val textWithoutSpaces = removeWhiteSpaces(textOfDoc)
        val sentences = splitText(textWithoutSpaces)
        val hashSentences = Try(Comparison.hashContentsOfList(sentences))
        InteractionWithDb.insert(hashSentences.get.mkString(","),getFileName(filePath).get)
    }


  def readTextFile(filePath : String) = Try(scala.io.Source.fromFile(filePath, "ISO-8859-1").getLines().map(_.toLowerCase).toList)

  def removeWhiteSpaces(str: Try[String]) = Try(str.get.replaceAll("\\s",""))

  def convertListToString(list: Try[List[String]]): Try[String] = list.map(_.mkString("."))

  def getFileName(filePath: String) : Try[String] = {
    Try(filePath.substring(filePath.lastIndexOf("\\")+1,filePath.length))
  }

  def splitText(txt : Try[String]) = Try(txt.get.split("\\.").toList)

  def uploadFile(path: String) = {
  }
}