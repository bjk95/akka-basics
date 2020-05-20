import HelloWorld.beechCheckerActor
import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

object HelloWorld extends App {
  class BaseActor extends Actor {
    implicit val timeout = Timeout(1 seconds)

    def receive = {
      case s: String => {
        val message =checkIfBeech(s)
        Thread.(1000)
        println(self)
        println(message)
      }

    }
    def checkIfBeech(name: String) = {

      if (name.toLowerCase.contains("tiff")) beechCheckerActor ? name
      else {
        println(self)
        s"Hello ${name.capitalize}"
      }
    }
  }

  class BeechCheckActor extends Actor {
    def receive = {
      case s: String => {
        println(self)
        s"You a BEECH, ${s.capitalize}"
      }
    }
  }

  val system = ActorSystem("Hello-world-system")

  val helloActor = system.actorOf(Props[BaseActor], "hello-world-actor")
  val beechCheckerActor = system.actorOf(Props[BeechCheckActor], "beech-check-actor")

  implicit val timeout = Timeout(1 seconds)
  helloActor ! "brad"
  helloActor ! "tiffani"
  helloActor ! "tiffani"

  system.terminate()



}
