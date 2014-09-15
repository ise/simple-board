package controllers

import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import models._

object Application extends Controller {

  val postForm = Form(
    single("message" -> nonEmptyText)
  )

  def index = TODO
  def thread(threadId: Long) = Action {
    val res = Response.findAllByThreadId(threadId)
    Ok(views.html.thread(postForm)("test")(res))
  }
  def postResponse = Action { implicit request =>
    postForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.thread(postForm)("test")(null)),
      message => {
        Response.create(message, "user1", 1)
        Redirect(routes.Application.thread(1))
      }
    )
  }

}
