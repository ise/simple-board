package controllers

import java.security.MessageDigest
import java.util.UUID

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import models._

object Application extends Controller {

  val postForm = Form(
    "message" -> text
      .verifying("投稿できる文字数は140文字までです", {_.length <= 140})
      .verifying("投稿内容を入力してください", {_.length > 0})
  )

  def generateUserCookie: Cookie = {
    val h = MessageDigest.getInstance("SHA1").digest(UUID.randomUUID().toString.getBytes)
    val id = h.map{ b => "%02x".format(b) }.mkString
    Cookie("user_id", id, Option(60 * 60 * 1))
  }

  def cookie(request: Request[AnyContent]): Cookie = {
    request.cookies.get("user_id").getOrElse(generateUserCookie)
  }

  def index = TODO
  def thread(threadId: Long) = Action { implicit request => {
    val res = Response.findAllByThreadId(threadId)
    Ok(views.html.thread(postForm)(Thread.findOpened.head)(res)).withCookies(
      cookie(request)
    )
  }}
  def postResponse = Action { implicit request => {
    val threadId = request.getQueryString("thread_id").getOrElse(Thread.defaultId.toString).toInt
    if (!Thread.isValid(threadId))
      Redirect(routes.Application.thread(Thread.defaultId))
    else {
      postForm.bindFromRequest.fold(
        formWithErrors => {
          val thread = Thread.findById(threadId)
          val res = Response.findAllByThreadId(threadId)
          Ok(views.html.thread(formWithErrors)(thread)(res))
        },
        message => {
          Response.create(message, cookie(request).value, threadId)
          Redirect(routes.Application.thread(threadId)).flashing("success" -> "投稿が完了しました")
        }
      )
    }
  }}

}
