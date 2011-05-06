package de.tudarmstadt.webmining2011.crawler

import URLUtils._
import java.io.{FileWriter, File}
import xml.XML

abstract class Page(url: String) {
  def host = url.host
}
case class FuturePage(aUrl: String) extends Page(aUrl)
case class CrawledPage (url: String, links: List[String], newLinks: List[String], language: String) extends Page(url)

case class EmptyPage(url: String)

object PageStorage {

  val storage: Map[String,Page] = Map.empty

  def store(p: Page) = storage += ((p.url, p))

  def wasCrawled(url: String) = storage.get(url) match {
    case Some(x: CrawledPage) => true
    case _ => false
  }

  def alreadyFound(url: String) = storage.contains(url)

  def writeToFile(f: File) = {
    val writer = new FileWriter(f)
    writer.write()
  }

  def crawledPages: Iterable[CrawledPage] = storage.values.filter(_.isInstanceOf[CrawledPage])

  def toXml = {
    val crawled = crawledPages
    val totalHost = crawled.groupBy(_.host).size;
    <crawledPages>
      <total>{crawled.size}</total>
      <totalHosts>{totalHost}</totalHosts>
      {for (page <- crawled) yield
        <page>
          <url>{page.url}</url>
          <host>{page.host}</url>
          <foundLinks>{page.links.size}</foundLinks>
          <newLinks>{page.newLinks.size}</newLinks>
          <language>{page.language}</newLinks>
        </page>
      }
    </crawledPages>
  }

  def writeToFile(filename: String) = XML.save(filename, toXml)
}