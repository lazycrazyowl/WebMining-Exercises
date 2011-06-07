import sbt._
import Keys._

import jenshaase.uimaScala.sbt._
import SbtUimaPlugin._

object MyBuild extends Build {

  override def projects = Seq(root)

  lazy val root = Project("root", file("."),
    settings = Defaults.defaultSettings ++ uimaSettings ++ rootSettings)

  val myTypeSystem = UimaTypeSystem("webmining")(
	    _.description("Contains all type system descriptor for this task"),
	
	    _.withType("de.tudarmstadt.ke.webmining2011.types.Category", "uima.tcas.Annotation")(
	      _.description("Category of the annotation"),
	      _.withFeature("category", "uima.cas.String")()),
	
	    _.withType("de.tudarmstadt.ke.webmining2011.types.TokenTF", "uima.tcas.Annotation")(
	      _.description("Term frequency of a token"),
	      _.withFeature("token", "uima.cas.String")(),
	      _.withFeature("count", "uima.cas.Integer")(),
	      _.withFeature("propability", "uima.cas.Float")(),
	      _.withFeature("tfidf", "uima.cas.Float")(),
	      _.withFeature("stopword", "uima.cas.Boolean")())
	  )

  lazy val rootSettings = Seq(
    name := "exercise4",
    organization := "de.tudarmstadt.ke.webmining2011",
    scalaVersion := "2.9.0-1",

    resolvers += "uimaScala repo" at "http://jenshaase.github.com/maven",

    libraryDependencies ++= Seq(
      "jenshaase" %% "uima-scala-toolkit" % "0.2-SNAPSHOT",
      "org.jsoup" % "jsoup" % "1.5.2"),

    typeSystem := Seq(myTypeSystem))
}
