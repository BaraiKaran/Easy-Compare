package controllers

import javax.inject.Inject

import play.api.mvc._

class UploadController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index = Action {
    Ok("It work!")
  }
}
