package models

import java.util.Date
import org.joda.time.DateTime

/**
 * Created by mastakeu on 2014/09/14.
 */

case class Thread(id: Long,
                  title: String,
                  userId: String,
                  isClosed: Boolean,
                  createdAt: Date,
                  deletedAt: Option[Date] = None)

object Thread {

  val dummy = Thread(1, "テスト用スレッド", "admin", false, new DateTime("2014-09-01").toDate())

  def findOpened: List[Thread] = {
    List(dummy)
  }
  def findById(id: Long): Thread = {
    dummy
  }

}
