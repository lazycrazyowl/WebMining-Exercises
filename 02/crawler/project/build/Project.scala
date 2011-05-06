import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) with IdeaProject with AkkaProject {

  // Http Client
  val dispatchHttp = "net.databinder" %% "dispatch-http" % "0.8.0"

  // HTML Parser
  val tagSoup = "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2"

  // Adding akka modules
  val akkaTypedActor = akkaModule("typed-actor")

  // jsoup: Extracts the text from html
  val jsoup = "org.jsoup" % "jsoup" % "1.5.2"
}
