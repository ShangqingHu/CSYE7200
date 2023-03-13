ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.8"
libraryDependencies += "net.liftweb" %% "lift-json" % "3.5.0"

libraryDependencies += "com.lihaoyi" %% "requests" % "0.6.6"
libraryDependencies += "io.circe" %% "circe-parser" % "0.14.1"
libraryDependencies += "io.circe" %% "circe-generic" % "0.14.1"

libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.3"

lazy val root = (project in file("."))
  .settings(
    name := "WorkingwithAPI"
  )
