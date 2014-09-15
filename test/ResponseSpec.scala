import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import models._

/**
 * Created by mastakeu on 2014/09/15.
 */
@RunWith(classOf[JUnitRunner])
class ResponseSpec extends Specification {
  "Response Model" should {
    "スレッドIDからレスポンス取得できる" in new WithApplication {
      val threadId = 1
      Response.create("test", "user1", threadId)
      val responses = Response.findAllByThreadId(threadId)
      responses.length should beEqualTo(1)
    }
  }
}
