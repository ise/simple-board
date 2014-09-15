package controllers

import play.api._
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

  def index = TODO
  def thread(threadId: Long) = Action { implicit request => {
    val res = Response.findAllByThreadId(threadId)
    Ok(views.html.thread(postForm)(Thread.findOpened.head)(res))
  }}
  def postResponse = Action { implicit request => {
    //thread_idチェック
    val threadId = request.getQueryString("thread_id").getOrElse("1").toInt
    postForm.bindFromRequest.fold(
      formWithErrors => {
        val thread = Thread.findById(threadId)
        val res = Response.findAllByThreadId(threadId)
        Ok(views.html.thread(formWithErrors)(thread)(res))
      },
      message => {
        Response.create(message, "user1", threadId)
        Redirect(routes.Application.thread(threadId)).flashing("success" -> "投稿が完了しました")
      }
    )
  }}

}
