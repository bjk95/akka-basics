package http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.actor.typed.scaladsl.AskPattern._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import scala.concurrent.Future

import com.verity.typed.TypedActorCapabilities._
import com.verity.typed.TypedActorCapabilities.PrinterActor._


object ListenToPort{

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route = concat(
      path("hello") {
        post { 
            decodeRequest{
                entity(as[String]){ r => 
                    val query = printerActorSystem.ask(ref => Message(r, ref))
                    printerActorSystem ? r.toInt
                    complete(r + r)
                
                }
            }
        }
      }
    )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}