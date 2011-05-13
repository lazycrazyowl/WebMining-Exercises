package de.tudarmstadt.webmining2011.crawler

import xml.XML
import java.io.{FileWriter, File}

object Crawler {

  def main(args: Array[String]) = {
    args(0) match {
      case "crawl-dfs" => crawlDFS
      case "crawl-bfs" => crawlBFS
      case "eval" => evaluate(args(1), args(2))
      case _ => println("Use one of the following actions:\n  crawl-dfs\n  crawl-bfs\n  eval <filename>")
    }
  }

  def crawlDFS = {
    val queue = new DFSQueue[String];
    val downloader = new Downloader
    val processor = new Processor(queue)

    queue.enqueue("http://news.google.de")

    println("[INFO] Start crawling")
    while(PageStorage.crawledPages.size < 1000) {
      println("[INFO] " + PageStorage.crawledPages.size + " Pages are crawled")
      val url = queue.dequeue
      processor.process(downloader.download(url))
    }

    println("[INFO] Write to xml")
    PageStorage.writeToFile("target/result-dfs.xml")
  }

  def crawlBFS = {
    val queue = new BFSQueue[String];
    val downloader = new Downloader
    val processor = new Processor(queue)

    queue.enqueue("http://news.google.de")

    println("[INFO] Start crawling")
    while(PageStorage.crawledPages.size < 1000) {
      println("[INFO] " + PageStorage.crawledPages.size + " Pages are crawled")
      val url = queue.dequeue
      processor.process(downloader.download(url))
    }

    println("[INFO] Write to xml")
    PageStorage.writeToFile("target/result-bfs.xml")
  }

  def evaluate(file: String, outputFolder: String) = {
    val xml = XML.loadFile(file)
    val output = new File(outputFolder)
    output.mkdirs

    val pages = xml \\ "page"
    // Task 1
    val newVsFoundLinks = pages.map(elem => (elem \ "foundLinks").text + " " + (elem \ "newLinks").text).reduceLeft(_ +"\n"+ _)
    val writer1 = new FileWriter(outputFolder + "/newVsFoundUrls.gp")
    writer1.write(newVsFoundLinks)
    writer1.close

    // Task 2
    val urlHistogram = pages.
      map(elem => (elem \ "foundLinks").text.toInt).
      groupBy(i => i).
      map(kv => (kv._1, kv._2.length)).
      toList.sortBy(_._1).map(kv => kv._1 + " " +kv._2).reduceLeft(_ +"\n"+ _)
    val writer2 = new FileWriter(outputFolder + "/histogram.gp")
    writer2.write(urlHistogram)
    writer2.close

    // Task 3
    val distLinks = (xml \\ "link").map(_.text).
      groupBy(l => l).
      map(kv => (kv._1, kv._2.length)).
      groupBy(_._2).map(kv => (kv._1, kv._2.size)).
      toList.sortBy(_._1).map(kv => kv._1 + " " +kv._2).reduceLeft(_ +"\n"+ _)
    val writer3 = new FileWriter(outputFolder + "/distLinks.gp")
    writer3.write(distLinks)
    writer3.close

    // Task 4
    val differentHosts = (xml \\ "host").map(_.text).distinct.size
    println(differentHosts)
    println("============")

    // Task 5
    val distLang = (xml \\ "language").map(_.text).groupBy(l => l).map(kv => (kv._1, kv._2.length))
    println(distLang)
    println("============")
  }
}