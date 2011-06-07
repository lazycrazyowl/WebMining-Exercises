
scalaVersion := "2.9.0-1"

name := "exercise4"

organization := "de.tudarmstadt.ke.webmining2011"

resolvers += "uimaScala repo" at "http://jenshaase.github.com/maven"

libraryDependencies ++= Seq(
  "jenshaase" %% "uima-scala-toolkit" % "0.2-SNAPSHOT",
  "org.jsoup" % "jsoup" % "1.5.2"
)