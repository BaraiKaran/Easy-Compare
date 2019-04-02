package services

import scala.util.Try

object ValidationService {

  def getFileType(filePath: String) : String = {
    filePath.substring(filePath.lastIndexOf(".")+1,filePath.length)
  }
}
