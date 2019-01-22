//Enable the default Java Application Archetype and adds support for other formats
enablePlugins(JavaAppPackaging)
//Enable support for building Docker images
enablePlugins(DockerPlugin)

lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion    = "2.5.11"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.helloworld.server",
      scalaVersion    := "2.11.12"
    )),
    name := "hello-world-server",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.1"         % Test
    )
  )

mainClass in Compile := Some("com.helloworld.server.HelloWorldServer")