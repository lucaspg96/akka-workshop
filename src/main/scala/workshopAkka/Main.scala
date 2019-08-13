package workshopAkka

import akka.actor.{ActorSystem, Props}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Main extends App {

  val actorSystem = ActorSystem("PingPong")
  val pong = actorSystem.actorOf(Props(classOf[Pong]), "pong" )
  val ping = actorSystem.actorOf(Props(classOf[Ping], pong), "ping")

  actorSystem
    .scheduler
    .scheduleOnce(delay = 500 millis)(ping ! Pong)
}


