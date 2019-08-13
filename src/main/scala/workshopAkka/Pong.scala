package workshopAkka

import akka.actor.{Actor, PoisonPill}
import scala.concurrent.duration._

case object Pong
class Pong() extends Actor {
  var counter: Int = 100

  def receive = {
    case Ping =>
      if (counter > 0) {
        println(s"workshopAkka.Pong of $counter")
        counter -= 1
        sender() ! Pong
        delayedMessage(counter)
      } else {
        self ! PoisonPill
      }
  }

  def delayedMessage(p: Int): Unit = {
    Thread.sleep(1000)
    println(s"Message $p")
  }
}
