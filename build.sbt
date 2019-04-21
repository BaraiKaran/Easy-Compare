name := "Easy_Compare"
 
version := "1.0" 
      
lazy val `easy_compare` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
      
scalaVersion := "2.12.2"
libraryDependencies += filters
resolvers += "Atlassian 3rd-Party" at "https://maven.atlassian.com/3rdparty/"
libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
  "org.apache.poi" % "poi-ooxml" % "3.17"
)
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "postgresql" % "postgresql" % "9.4.1208-jdbc42-atlassian-hosted"
libraryDependencies += "org.scalamock" %% "scalamock" % "4.1.0" % Test

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

      