package services

import play.api.data.Form
import play.api.data.Forms._


case class BasicForm(path: String)


object BasicForm {
  val form: Form[BasicForm] = Form(
    mapping(
      "path" -> text
    )(BasicForm.apply)(BasicForm.unapply)
  )
}



