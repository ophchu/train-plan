import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beSome.which (status(_) == NOT_FOUND)
    }

    "render the index page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Your new application is ready.")
    }

    "render test page with string should return BAD_REQUEST" in new WithApplication {
      val test = route(FakeRequest(GET, "/ids/ophir")).get
      status(test) must equalTo(BAD_REQUEST)
    }

    "render test page with long id should return html page" in new WithApplication {
      val test = route(FakeRequest(GET, "/ids/100")).get
      status(test) must equalTo(OK)
      contentType(test) must beSome.which(_ == "text/html")
      contentAsString(test) must contain ("The id is: 100 --> tmp")
    }

    "render test page with long id and string should return html page" in new WithApplication {
      val test = route(FakeRequest(GET, "/ids/100?sd=aab")).get
      status(test) must equalTo(OK)
      contentType(test) must beSome.which(_ == "text/html")
      contentAsString(test) must contain ("The id is: 100 --> aab")
    }
  }
}
