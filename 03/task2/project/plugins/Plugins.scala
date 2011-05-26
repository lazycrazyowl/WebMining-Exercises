import sbt._
 
class Plugins(info: ProjectInfo) extends PluginDefinition(info) {

  val uima = "UIMA" at "http://people.apache.org/repo/m2-incubating-repository"

  // Idea plugin
  val sbtIdeaRepo = "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"
  val sbtIdea = "com.github.mpeltonen" % "sbt-idea-plugin" % "0.3.0"
  
  // Uima Scala plugin
  val sbtUimaScalaRepo = "sbt-uima-repo" at "http://jenshaase.github.com/maven/"
  val sbtUimaScala = "jenshaase" % "sbt-uimascala" % "0.1"
}

