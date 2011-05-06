package de.tudarmstadt.webmining2011.crawler

import dispatch._

abstract class Download(url: String)
case class FetchedDownload(url: String, header: Map[String, Set[String]], html: String) extends Download(url)
case class RefusedDownload(url: String, exceptionMessage: String) extends Download(url)

class Downloader {

  def download(link: String): Download = try {
    val site = Http(url(link) >+ (handler => (
        handler >:> { h => h },
        handler >- { s => s}
      )))

    FetchedDownload(link, site._1, site._2.replace("\n", ""))
  } catch {
    case e: dispatch.StatusCode => RefusedDownload(link, e.code.toString)
    case e: Exception => RefusedDownload(link, e.getMessage())
  }
}