name := """LOUD API"""
organization := "lagos Software Engineering group"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.2"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.3"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3"
libraryDependencies += "joda-time" % "joda-time" % "2.7"
libraryDependencies += "org.joda" % "joda-convert" % "1.7"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.7"
libraryDependencies += "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0"
libraryDependencies += jdbc % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "lagos Software Engineering group.controllers._"

// Adds additional packages into conf/routes
 //play.sbt.routes.RoutesKeys.routesImport += "lagos Software Engineering group.binders._"
