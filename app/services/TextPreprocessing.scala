package services

object preprocess {
    def apply(filePath: String) = {
      val filename = filePath
      val text = scala.io.Source.fromFile(filename, "ISO-8859-1").getLines().map(_.toLowerCase).toList
      def convertListToString(list: List[String]): String = text.mkString(".")
      val textOfDoc = convertListToString(text)
      val sentences = textOfDoc.split("\\.").toList
      sentences
    }
}