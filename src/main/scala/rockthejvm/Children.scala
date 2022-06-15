package rockthejvm

import akka.actor.{Actor, ActorSystem}
import akka.actor.Props
import akka.actor.ActorRef

object Children extends App {


    object Parent {
        case class CreateChild(name: String)
        case class TellChild(m: String)
    }

    class Parent extends Actor {
        import Parent._

        def receive: Actor.Receive = {
            case CreateChild(name) => {
                println(s"actor $self is creating a child")

                val childRef = context.actorOf(Props[Child], "kiddy")
                context.become(withChild(childRef))
            }
        }

        def withChild(childRef: ActorRef): Receive = {
            case TellChild(m) => if (childRef != null) childRef forward(m)
        }
    }

    class Child extends Actor {
        def receive: Actor.Receive = {
            case m => println(s"This actor $self received $m")
        }
    }

    import Parent._

    val childSystem = ActorSystem("ParentChildSystem")
    val parent = childSystem.actorOf(Props[Parent], "parent")
    parent ! CreateChild("hiii")
}