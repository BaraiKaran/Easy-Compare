package services

import scala.util.Try

object preprocess {
    def apply(filePath: String) = {
      val filename = filePath
      val text : Try[List[String]] = Try(scala.io.Source.fromFile(filename, "ISO-8859-1").getLines().map(_.toLowerCase).toList)
      def convertListToString(list: Try[List[String]]): Try[String] = list.map(_.mkString("."))
      val textOfDoc = convertListToString(text)
      val sentences = textOfDoc.toString.split("\\.").toList
      sentences
    }
}