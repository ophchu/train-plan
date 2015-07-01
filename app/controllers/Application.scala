package controllers

import play.api.libs.iteratee.Enumerator
import play.api.mvc._

class Application extends Controller {

  def index = Action {req =>
    Ok(views.html.index("Your new application is ready. " + req.getQueryString("val") ))
  }

  def test = TODO
  def ids(id: Long, sd: String) = Action{
    Ok(s"<html><body><h1>The id is: $id --> $sd</h1></body></html>").as(HTML)
  }
}
