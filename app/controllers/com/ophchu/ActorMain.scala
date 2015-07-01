package controllers.com.ophchu

/**
 * Created by ophchu on 7/1/15.
 */

import akka.util.Timeout
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import controllers.com.ophchu.HelloActor.SayHello
import org.slf4j.LoggerFactory
import play.api.mvc._
import akka.actor._
import javax.inject._
import scala.concurrent.duration._
import akka.pattern.ask


@Singleton
class ActorMain @Inject()(system: ActorSystem) extends Controller {
  implicit val timeout = Timeout(5 seconds)

  val helloActor = system.actorOf(HelloActor.props, "hello-actor")

  def sayHello(name: String) = Action.async {
      (helloActor ? SayHello(name)).mapTo[String].map { message =>
      Ok(message)
    }
  }

  val cancellable = system.scheduler.schedule(
    0.microseconds, 300.microseconds, helloActor, SayHello("Tick! -> " + System.currentTimeMillis().toString))
}
