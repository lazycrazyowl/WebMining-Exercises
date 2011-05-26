import sbt._
import jenshaase.uimaScala.descriptor.UimaTypeSystem
import jenshaase.uimaScala.sbt.UimaScalaPlugin

class Project(info: ProjectInfo) extends DefaultProject(info) with IdeaProject with UimaScalaPlugin {

  val uima = "UIMA" at "http://people.apache.org/repo/m2-incubating-repository"

  val UimaScalaRepo = "sbt-uima-repo" at "http://jenshaase.github.com/maven/"
  val uimaScalaCore = "jenshaase" %% "uima-toolkit" % "0.1.3"

  // jsoup: Extracts the text from html
  val jsoup = "org.jsoup" % "jsoup" % "1.5.2"

  val typSystem = UimaTypeSystem("webmining")(
    _.description("Contains all type system descriptor for this task"),

    _.withType("de.tudarmstadt.ke.webmining2011.types.Stem", "uima.tcas.Annotation")(
      _.description("A stemming annotation")
    ),

    _.withType("de.tudarmstadt.ke.webmining2011.types.Category", "uima.tcas.Annotation")(
      _.description("Category of the annotation"),
      _.withFeature("category", "uima.cas.String")()
    ),

    _.withType("de.tudarmstadt.ke.webmining2011.types.TokenTF", "uima.tcas.Annotation")(
      _.description("Term frequency of a token"),
      _.withFeature("token", "uima.cas.String")(),
      _.withFeature("count", "uima.cas.Integer")(),
      _.withFeature("propability", "uima.cas.Float")(),
      _.withFeature("tfidf", "uima.cas.Float")(),
      _.withFeature("stopword", "uima.cas.Boolean")()
    ),

    _.withType("de.tudarmstadt.ke.webmining2011.types.StemTF", "uima.tcas.Annotation")(
      _.description("Term frequency of a token"),
      _.withFeature("token", "uima.cas.String")(),
      _.withFeature("count", "uima.cas.Integer")(),
      _.withFeature("propability", "uima.cas.Float")(),
      _.withFeature("tfidf", "uima.cas.Float")(),
      _.withFeature("stopword", "uima.cas.Boolean")()
    ),

    _.withType("de.tudarmstadt.ke.webmining2011.types.TokenWOStopwordTF", "uima.tcas.Annotation")(
      _.description("Term frequency of a token"),
      _.withFeature("token", "uima.cas.String")(),
      _.withFeature("count", "uima.cas.Integer")(),
      _.withFeature("propability", "uima.cas.Float")(),
      _.withFeature("tfidf", "uima.cas.Float")(),
      _.withFeature("stopword", "uima.cas.Boolean")()
    )
  )
}
