package workshopAkka

import akka.actor.{Actor, ActorRef}

case object Ping
class Ping(actorRef: ActorRef) extends Actor {

  def receive = {
    case Pong =>
      println(s"Pinging of a Pong")
      actorRef ! Ping
  }
}