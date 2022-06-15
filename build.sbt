name := "akka-basics"

version := "0.1"

scalaVersion := "2.13.2"

val akkaVersion = "2.6.6"

libraryDependencies ++= akkaDependencies ++ testingDependencies ++ loggingDependencies

val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http"   % "10.1.12",
   "com.typesafe.akka" %% "akka-protobuf" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion, //% Test
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  )
  
  
  val testingDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.1.2" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.3" % Test
    )

  val loggingDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
  )