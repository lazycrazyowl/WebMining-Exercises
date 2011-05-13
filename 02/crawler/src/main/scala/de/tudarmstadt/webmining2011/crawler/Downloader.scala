package de.tudarmstadt.webmining2011.crawler

import dispatch._

/**
 * A download object
 */
abstract class Download(url: String)

/**
 * A fetched download. This will used if the download was successful
 */
case class FetchedDownload(url: String, header: Map[String, Set[String]], html: String) extends Download(url)

/**
 * A refused download. This will be used if the download fails.
 */
case class RefusedDownload(url: String, exceptionMessage: String) extends Download(url)

/**
 * A downloader
 */
class Downloader {

  /**
   * Downloads a url
   */
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