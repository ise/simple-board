package controllers

import play.api._
import play.api.mvc._
import models._

object Application extends Controller {

  def index = TODO
  def thread(threadId: Long) = Action {
    val res = Response.findAllByThreadId(threadId)
    Ok(views.html.index("Your new application is ready."))
  }

}