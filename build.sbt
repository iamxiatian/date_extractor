organization := "ruc.irm"
version := "0.1"
name := "date_extractor"

javacOptions in (Compile, compile) ++= Seq("-source", "1.8", "-target", "1.8", "-g:lines")

crossPaths := false // drop off Scala suffix from artifact names.
autoScalaLibrary := false // exclude scala-library from dependencies

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.1"
libraryDependencies += "junit" % "junit" % "4.12" % "test"