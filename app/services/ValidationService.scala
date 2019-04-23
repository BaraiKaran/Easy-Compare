package services

import scala.util.Try
import models.InteractionWithDb
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

object ValidationService {

  def getFileType(filePath: String) : String = {
    filePath.substring(filePath.lastIndexOf(".")+1,filePath.length)
  }

  def checkIfFileNameExists(filename: String) : Future[Boolean] = for (ws <- InteractionWithDb.getUploadedFileNames) yield ws.contains(filename)
}
