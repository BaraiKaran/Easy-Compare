package services

import scala.util.Try
import models.InteractionWithDb

object ValidationService {

  def getFileType(filePath: String) : String = {
    filePath.substring(filePath.lastIndexOf(".")+1,filePath.length)
  }

  def checkIfFileNameExists(filename: String) : Boolean = InteractionWithDb.getUploadedFileNames().contains(filename)
}
