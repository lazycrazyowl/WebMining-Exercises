package de.tudarmstadt.webmining2011.crawler

import scala.xml._
import factory.XMLLoader
import URLUtils._

class Processor {

  def process(d: Download) = d match {
    case f: FetchedDownload => processFetched(f)
    case r: RefusedDownload => processRefused(r)
  }

  def processFetched(f: FetchedDownload) = {
    val links = extractLinks(Html.load(f.html))
    val newlinks = links.filter(link => !PageStorage.alreadyFound(link)).normalizeLinks(f.url)
    val language = findLanguage(Jsoup.parse(f.html).text)

    PageStorage.store(CrawledPage(f.url, links, newlinks, language))
    newlinks.foreach(l => {
      PageStorage.store(EmptyPage(newLink))
      // TODO: Add to queue
    })
  }

  def processRefused(r: RefusedDownload) = {

  }

  protected def extractLinks(html: Elem): List[String] = {
    (html \\ "a").map(l => (l \ "@href").text).filterNot(url => {
      url == "" || url.startsWith("#") || url.startsWith("javascript")
    }).toList
  }

  protected def findLanguage(text: String) = {

  }

  object Html extends XMLLoader[Elem] {
    override def parser: SAXParser = {
      val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
      parserFactory.newSAXParser()
    }
  }
}