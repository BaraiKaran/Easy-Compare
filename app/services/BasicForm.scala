package services

import play.api.data.Form
import play.api.data.Forms._


case class BasicForm(path: String)

// this could be defined somewhere else,
// but I prefer to keep it in the companion object
object BasicForm {
  val form: Form[BasicForm] = Form(
    mapping(
      "path" -> text
    )(BasicForm.apply)(BasicForm.unapply)
  )
}



