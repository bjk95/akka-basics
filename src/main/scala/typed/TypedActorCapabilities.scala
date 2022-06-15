package com.verity.typed

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import akka.actor.typed.{ ActorRef, ActorSystem, Behavior }
import TypedActorCapabilities.PrinterActor.Message
import http.ListenToPort._

object TypedActorCapabilities {

    object PrinterActor {
        final case class Message(m: String, actorRef: ActorRef[Message])

        // def apply():Behavior[Message] =  Behaviors.receive { (context, message) =>
        //         context.log.info(s"Hello ${message.m}") 
        //         Behaviors.same
        // }

        def apply(): Behavior[Int] = Behaviors.receive{(context, message) => 
            context.log.info((message * 2).toString())
            Behaviors.same
        }
    }

    val printerActorSystem = ActorSystem(PrinterActor(), "basic-actor-system")


}
