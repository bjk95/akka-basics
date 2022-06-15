// package com.verity.akka.test

// import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
// import org.scalatest.wordspec.AnyWordSpecLike
// import com.verity.akka.HelloWorld.{helloActor, BaseActor}



// class EchoActorTest extends ScalaTestWithActorTestKit with AnyWordSpecLike {

//     "Hello actor" must "receive the string" in {
//         val baseActorInstance = testKit.spawn(BaseActor(), "base")
//         val prob = testKit.createTestProbe[BaseActor]()

//     }
// }