package de.tudarmstadt.webmining2011.crawler

import scala.xml._
import factory.XMLLoader
import URLUtils._
import org.jsoup.Jsoup
import java.text.BreakIterator

/**
 * Processes a downloaded url
 */
class Processor(queue: MyQueue[String]) {

  /**
   * Process a download
   */
  def process(d: Download) = d match {
    case f: FetchedDownload => processFetched(f)
    case r: RefusedDownload => processRefused(r)
  }

  /**
   * Process a fetched page:
   *  1. Extract links
   *  2. Filter new links
   *  3. Store new links
   *  4. Add new links to queue
   */
  def processFetched(f: FetchedDownload) = {
    val links = extractLinks(Html.loadString(f.html)).normalizeLinks(f.url)
    val newlinks = links.filter(link => !PageStorage.alreadyFound(link))
    val language = findLanguage(Jsoup.parse(f.html).text)

    PageStorage.store(CrawledPage(f.url, links, newlinks, language))
    newlinks.foreach(l => {
      val p = FuturePage(l)
      // Store empty page in queue => Url was found
      PageStorage.store(p)

      // Add link to queue
      queue.enqueue(l)
    })
  }

  /**
   * Process pages with a download error
   *  Currently do nothing
   */
  def processRefused(r: RefusedDownload) = {

  }

  /**
   * Extracts all links in an html document
   */
  protected def extractLinks(html: Elem): List[String] = {
    (html \\ "a").map(l => (l \ "@href").text.trim.toLowerCase).filterNot(url => {
      url == "" || url.startsWith("#")
    }).toList
  }


  val stopwords: Map[String, Seq[String]] = loadStopwords
  /**
   * Finds the language of a text
   */
  protected def findLanguage(text: String) = {
    val tokens = tokenize(text)

    val result = stopwords.
      map(kv => (kv._1, tokens.filter(t => kv._2.contains(t)).size)).
      toList.
      sortBy(_._2).
      reverse.
      head

    if (result._2 > 0) {
      result._1
    } else {
      "<unknown>"
    }
  }

  protected def tokenize(text: String) = {
    val iter = BreakIterator.getWordInstance
    iter.setText(text)
    var tokens: List[String] = List.empty

    var last = iter.first
    var next = iter.next
    while (next != BreakIterator.DONE) {
      tokens = tokens :+ text.substring(last, next).toLowerCase

      last = next
      next = iter.next
    }

    tokens
  }

  protected def loadStopwords = {
    Map(
      ("danish" -> loadStopwordFile("src/main/resources/stopwords/danish.txt")),
      ("dutch" -> loadStopwordFile("src/main/resources/stopwords/dutch.txt")),
      ("english" -> loadStopwordFile("src/main/resources/stopwords/english.txt")),
      ("finnish" -> loadStopwordFile("src/main/resources/stopwords/finnish.txt")),
      ("french" -> loadStopwordFile("src/main/resources/stopwords/french.txt")),
      ("german" -> loadStopwordFile("src/main/resources/stopwords/german.txt")),
      ("hungarian" -> loadStopwordFile("src/main/resources/stopwords/hungarian.txt")),
      ("italian" -> loadStopwordFile("src/main/resources/stopwords/italian.txt")),
      ("norwegian" -> loadStopwordFile("src/main/resources/stopwords/norwegian.txt")),
      ("portuguese" -> loadStopwordFile("src/main/resources/stopwords/portuguese.txt")),
      ("russian" -> loadStopwordFile("src/main/resources/stopwords/russian.txt")),
      ("spanish" -> loadStopwordFile("src/main/resources/stopwords/spanish.txt")),
      ("swedish" -> loadStopwordFile("src/main/resources/stopwords/swedish.txt")),
      ("turkish" -> loadStopwordFile("src/main/resources/stopwords/turkish.txt"))
    )
  }

  protected def loadStopwordFile(file: String) = {
    scala.io.Source.fromFile(file).getLines.toSeq
  }

  /**
   * Html parser
   */
  object Html extends XMLLoader[Elem] {
    override def parser: SAXParser = {
      val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
      parserFactory.newSAXParser()
    }
  }
}