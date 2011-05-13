package de.tudarmstadt.webmining2011.crawler

import URLUtils._
import java.io.{FileWriter, File}
import xml.{Unparsed, XML}

/**
 * A abstract page has an url
 */
abstract class Page(val url: String) {
  def host = url.host
}

/**
 * A page the will be downloaded in future
 */
case class FuturePage(aUrl: String) extends Page(aUrl)

/**
 * A page that was already crawled, with the processed results
 */
case class CrawledPage(aUrl: String,
                        val links: List[String],
                        val newLinks: List[String],
                        val language: String) extends Page(aUrl)

/**
 * Stores page that are already crawled or will be crawled in future.
 * This storage is used to find already crawled pages and to save the result
 *
 * Results can be exportet to xml
 */
object PageStorage {

  // The storage map
  protected var storage: Map[String,Page] = Map.empty

  /**
   * Store a page
   */
  def store(p: Page) = storage += ((p.url, p))

  /**
   * Check if the url was already crawled
   */
  def wasCrawled(url: String) = storage.get(url) match {
    case Some(x: CrawledPage) => true
    case _ => false
  }

  /**
   * Checks if the url is in the storage (future crawled or already crawled)
   */
  def alreadyFound(url: String) = storage.contains(url)

  /**
   * Returns the crawled pages
   */
  def crawledPages: Iterable[CrawledPage] = storage.values.filter(_.isInstanceOf[CrawledPage]).asInstanceOf[Iterable[CrawledPage]]

  /**
   * Coverts the crawled pages to xml
   */
  def toXml = {
    val crawled = crawledPages
    val totalHost = crawled.groupBy(_.host).size;
    <crawledPages>
      <total>{crawled.size}</total>
      <totalHosts>{totalHost}</totalHosts>
      {for (page <- crawled) yield
        <page>
          <url>{page.url}</url>
          <host>{page.host}</host>
          <foundLinks>{page.links.size}</foundLinks>
          <newLinks>{page.newLinks.size}</newLinks>
          <language>{page.language}</language>
          <links>
            {for (l <- page.links) yield
              <link>{Unparsed("<![CDATA["+l+"]]>")}</link>
            }
          </links>
        </page>
      }
    </crawledPages>
  }

  /**
   * Writes the xml to a file
   */
  def writeToFile(filename: String) = XML.save(filename, toXml, "UTF-8")
}