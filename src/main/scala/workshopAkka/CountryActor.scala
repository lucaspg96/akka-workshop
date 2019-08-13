package workshopAkka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.ask

case class AddState(name: String, capital: String)
case class GetCapital(stateName: String)

class CountryActor(name: String) extends Actor{
  var states: Map[String, ActorRef] = Map.empty
  override def receive: Receive = {
    case AddState(n,c) =>
      if(states.contains(n)){}
      else {
        val stateActor = context.system.actorOf(Props(classOf[StateActor],n,c))
        states += (n -> stateActor)
      }
    case msg @ GetCapital(state) =>
      states.get(state) match {
        case Some(stateActor) =>
          stateActor ! msg
        case None =>
          throw new Error(s"State $state not exist in $name")
      }
  }
}

class StateActor(name: String, capital: String) extends Actor{
  override def receive: Receive = {
    case GetCapital(n) =>
      if(name == n) println(s"Capital of $name is $capital")
  }
}

object CountryMain extends App{
  val actorSystem = ActorSystem("Earth")
  val brazilActor = actorSystem.actorOf(Props(classOf[CountryActor], "Brazil"))

  brazilActor ! AddState("Ceará", "Fortaleza")
  brazilActor ! AddState("Rio Grande do Norte", "Natal")

  brazilActor ! GetCapital("Acre")
}
