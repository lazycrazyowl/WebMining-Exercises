import sbt._
import jenshaase.uimaScala.descriptor.UimaTypeSystem
import jenshaase.uimaScala.sbt.UimaScalaPlugin

class Project(info: ProjectInfo) extends DefaultProject(info) with IdeaProject with UimaScalaPlugin {

  val uima = "UIMA" at "http://people.apache.org/repo/m2-incubating-repository"

  val UimaScalaRepo = "sbt-uima-repo" at "http://jenshaase.github.com/maven/"
  val uimaScalaCore = "jenshaase" %% "uima-toolkit" % "0.1.3"

  val typSystem = UimaTypeSystem("webmining")(
    _.description("Contains all type system descriptor for this task"),

    _.withType("de.tudarmstadt.ke.webmining.types.Unigramm", "uima.tcas.Annotation")(
      _.description("A unigram annotation")
    ),

    _.withType("de.tudarmstadt.ke.webmining.types.Bigramm", "uima.tcas.Annotation")(
      _.description("A bigram annotation")
    )
  )
}
