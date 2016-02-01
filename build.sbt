//lazy val common = Project("common", file("modules/common"))
lazy val cloudServer = Project("cloudServer", file(".")).enablePlugins(PlayScala)//.aggregate(common).dependsOn(common)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)
libraryDependencies += "com.google.protobuf" % "protobuf-java" % "2.4.1"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.4"
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.2"


resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator