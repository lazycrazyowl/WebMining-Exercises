package de.tudarmstadt.webmining2011.crawler

import java.net.URL

/**
 * Implicities for URLs
 */
object URLUtils {

  implicit def toURL(url: String) = new URLWrapper(url)

  implicit def toUrlList[T <: Traversable[String]](urls: T) = new URLListWrapper(urls)
}

/**
 * URL Wrapper
 */
class URLWrapper(url: String) {

  /**
   * Get the host name of an url
   */
  def host() = new URL(url).getHost()

  /**
   * Get the absolute URL
   */
  def absolute(context: String): Option[String] = try {
    Some(new URL(new URL(context), url).toString)
  } catch {
    case _ => None
  }

  /**
   * Remove the reference (#) of an URL
   */
  def withoutRef() = {
    val u = new URL(url);
    val s = u.toString

    if (u.getRef.isInstanceOf[String]) {
      s.substring(0, s.length - u.getRef.length -1)
    } else {
      s
    }
  }

  /**
   * Checks if two URLs have the same host
   */
  def isSameDomain(context: String) = host == new URL(context).getHost()
}

class URLListWrapper[T <: Traversable[String]](urls: T) {

  import URLUtils._

  /**
   * Filters all external URLS
   */
  def filterExternalLinks(url: String): T =
    urls.filterNot(_.isSameDomain(url)).asInstanceOf[T]

  /**
   * Filters all internal URLS
   */
  def filterInternalLinks(url: String): T =
    urls.filter(_.isSameDomain(url)).asInstanceOf[T]

  /**
   * normalize all links
   */
  def normalizeLinks(context: String): T =
    urls.map(_.absolute(context)).flatten.map(_.withoutRef).asInstanceOf[T]
}