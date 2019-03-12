package services

import java.nio.file.Paths

import scala.util.Try

object preprocess {
    def apply(filePath: String) : Try[List[String]] = {
      val filename = filePath
      val text : Try[List[String]] = Try(scala.io.Source.fromFile(filename, "ISO-8859-1").getLines().map(_.toLowerCase).toList)
      val textOfDoc = convertListToString(text)
      val textWithoutSpaces = removeWhiteSpaces(textOfDoc)
      val sentences = Try(textWithoutSpaces.get.split("\\.").toList)
      sentences
    }

  def removeWhiteSpaces(str: Try[String]) = Try(str.get.replaceAll("\\s",""))

  def convertListToString(list: Try[List[String]]): Try[String] = list.map(_.mkString("."))

  def getFileName(filePath: String) : Try[String] = {
    Try(filePath.substring(filePath.lastIndexOf("\\")+1,filePath.length))
  }

  def uploadFile(path: String) = {
  }
}