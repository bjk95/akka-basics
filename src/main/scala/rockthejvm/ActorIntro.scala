package rockthejvm

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorIntro extends App {

  val actorSystem = ActorSystem("FirstActorSystem")
  println(actorSystem.name)

  // word count actor

  class WordCountActor extends Actor {

    var totalWords = 0
    def receive: PartialFunction[Any, Unit] = {
      case message: String => totalWords += message.split(' ').length
      case msg => println(s"Word count = $totalWords. $msg was not a string")
    }

  }
    val wordCounter: ActorRef = actorSystem.actorOf(Props[WordCountActor], "wordCounter")

  wordCounter ! "this is the first message"
  wordCounter ! 6
  wordCounter ! "this is the second message beech"
  wordCounter ! 9

  class SayHello(name: String) extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case greeting: String => println(s"$greeting $name")
    }
  }

  object Greeter {
    def props(name: String) = Props(new SayHello(name))
  }

  val greeterActor: ActorRef = actorSystem.actorOf(Greeter.props("Beech"))
  greeterActor ! "How the bloody hell are ya"

}
