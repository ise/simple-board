package models

import java.util.Date

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play._
import org.joda.time.DateTime

/**
 * Created by mastakeu on 2014/09/14.
 */
case class Response(id: Long,
                    message: String,
                    userId: String,
                    threadId: Long,
                    createdAt: Date,
                    deletedAt: Option[Date] = None)

object Response {

  val simple = {
    get[Long]("id") ~
    get[String]("message") ~
    get[String]("user_id") ~
    get[Long]("thread_id") ~
    get[Date]("created_at") map {
      case id~message~userId~threadId~createdAt =>
        Response(id, message, userId, threadId, createdAt)
    }
  }

  def findAllByThreadId(threadId: Long): List[Response] = {
    DB.withConnection { implicit c =>
      SQL(
        "select * from response where thread_id={thread_id} order by created_at asc"
      ).on('thread_id -> threadId).as(simple *)
    }
  }

  def create(message: String, userId: String, threadId: Long): Unit = {
    DB.withConnection { implicit c =>
      SQL(
        "insert into response (message, user_id, thread_id, created_at)"
          + " values ({message}, {user_id}, {thread_id}, {created_at})"
      ).on(
          'message -> message, 'user_id -> userId, 'thread_id -> threadId, 'created_at -> DateTime.now().toDate()
        ).executeUpdate()
    }

  }
}
