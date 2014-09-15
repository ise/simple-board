import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._

import play.api.test._
import models._

/**
 * Created by mastakeu on 2014/09/15.
 */
@RunWith(classOf[JUnitRunner])
class ThreadSpec extends Specification {
  "Thread Model" should {
    "スレッド一覧を取得できる" in new WithApplication {
      val threads = Thread.findOpened
      threads.head.id should beEqualTo(1)
    }
  }
}
