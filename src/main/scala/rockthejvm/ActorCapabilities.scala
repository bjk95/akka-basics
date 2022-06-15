package rockthejvm

import akka.actor.{Actor, ActorSystem}
import akka.actor.Actor.Receive
import akka.actor.Props
import akka.actor.ActorRef

object ActorCapabilities extends App {

  class SimpleActor() extends Actor  {
      def receive: Receive = {
        case m: String => println(s"$m was sent to ${self}")
        case i: Int => println(i * 2)
        case cc: Message => println(s"Message: ${cc.m}, number: ${cc.number}")
        case s: SendToSelf => {println("Sending to self")
          self ! s.m
        }
        case q: QuestionToAsk => {
          println(s"Asking ${q.m} to $self")
          q.actorRef ! MessageToReplyTo(q.m)
        }
        case m: MessageToReplyTo => sender() ! s"You asked ${m.m}. You totally are... BEECH"
        case _ => println("Sent message of invalid type")
      }
  }

  object SimpleActor{
    def props(name: String) = Props(new SimpleActor())
  }

  val actorSystem = ActorSystem("SimpleActorSystem")
  val printerActor = actorSystem.actorOf(SimpleActor.props("HelloActor"))

  // Sending different types of standard datatypes
  // 1 - messages can be of any type
  // 2 - messages must be serializeable by the4 JVM
  // Using classes and case objects is recommended
  printerActor ! "hey beeeeech"
  printerActor ! 10
  printerActor ! List.empty

  // Sending custom data types
  val customMessage = Message("hey, it me", scala.util.Random.nextInt())
  printerActor ! customMessage

  // Sending to self using self keyword
  val sendToSelf = SendToSelf("Hey, it me, but like talking to me")
  printerActor ! sendToSelf

  // Actors can reply to messages 
  val anotherPrinterActor = actorSystem.actorOf(SimpleActor.props("GdayActor"))
  val question = QuestionToAsk("Am I a beech?", anotherPrinterActor)
  printerActor ! question


  // Terminating the system
  actorSystem.terminate()

}

case class Message(m: String, number: Int)

case class SendToSelf(m: String)

case class QuestionToAsk(m: String, actorRef: ActorRef)

case class MessageToReplyTo(m: String)
