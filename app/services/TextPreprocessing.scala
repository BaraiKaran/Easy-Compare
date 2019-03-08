package services

import java.nio.file.Paths

import scala.util.Try

object preprocess {
    def apply(filePath: String) : Try[List[String]] = {
      val filename = filePath
      val text : Try[List[String]] = Try(scala.io.Source.fromFile(filename, "ISO-8859-1").getLines().map(_.toLowerCase).toList)
      def convertListToString(list: Try[List[String]]): Try[String] = list.map(_.mkString("."))
      val textOfDoc = convertListToString(text)
      val textWithoutSpaces = Try(textOfDoc.get.replaceAll("\\s",""))
      val sentences = Try(textWithoutSpaces.get.split("\\.").toList)
      sentences
    }

  def getFileName(filePath: String) : Try[String] = {
    Try(filePath.substring(filePath.lastIndexOf("\\")+1,filePath.length))
  }

  def uploadFile(path: String) = {

  }
}