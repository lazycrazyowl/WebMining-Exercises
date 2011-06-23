package de.tudarmstadt.ke.webmining2011.hits

import jenshaase.uimaScala.toolkit.reader.TextFileReader
import jenshaase.uimaScala.toolkit.annotator.BreakIteratorTokenizer
import jenshaase.uimaScala.toolkit.configuration.Configuration
import org.uimafit.pipeline.SimplePipeline
import org.uimafit.factory.CollectionReaderFactory
import jenshaase.uimaScala.core.SCasAnnotator_ImplBase
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.core.Implicits._
import jenshaase.uimaScala.toolkit.types.Token
import org.apache.uima.collection.CollectionReader
import org.uimafit.factory.AnalysisEngineFactory
import org.uimafit.component.JCasConsumer_ImplBase
import org.uimafit.descriptor.ConfigurationParameter
import java.io.File
import jenshaase.uimaScala.toolkit.types.DocumentAnnotation
import java.io._
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParserFactory
import scala.collection.JavaConversions._

/**
 * Creates an inverted index from the 
 * text files
 */
object Index {

  def main(args: Array[String]) = {
    SimplePipeline.runPipeline(
        WikipediaReader("src/main/resources/data/text"),
        BreakIteratorTokenizer(),
        TokenCleanup(),
        IndexConsumer("src/main/resources/data/index.txt")
    )
  }
  
  def read(file: String): Map[String, Seq[String]] =
    scala.io.Source.fromFile(file).getLines.foldLeft[Map[String, Seq[String]]](Map()) { (map, line) => 
      val d = line.split("\t")
      map + (d.head -> d.tail.toSeq)
    }
}


class IndexConsumer extends JCasConsumer_ImplBase {
  
  @ConfigurationParameter(name = IndexConsumer.PARAM_OUTPUT_PATH, mandatory = true)
  protected var outputFile: File = null
  
  var index: Map[String, Set[String]] = Map.empty
  
  def process(cas: JCas) = {
    val docName = cas.selectSingle(classOf[DocumentAnnotation]).getName.replaceFirst(".txt", "")
    
    cas.select(classOf[Token]).foreach { t =>
      val set = index.getOrElse(t.getCoveredText, Set.empty) ++ Set(docName)
      index += ((t.getCoveredText, set))
    }
  }
  
  override def collectionProcessComplete = {
    var writer = new BufferedWriter(new FileWriter(outputFile))
    
    index.foreach { kv =>
      writer.write(kv._1 + "\t")
      kv._2.foreach { v => writer.write(v + "\t") }
      writer.write("\n");
    }

    writer.close
  }
}

object IndexConsumer {
  final val PARAM_OUTPUT_PATH = "outputFile"
  
  def apply(path: String) = 
    AnalysisEngineFactory.createPrimitive(classOf[IndexConsumer], PARAM_OUTPUT_PATH, path)
}

class WikipediaReader extends TextFileReader {
  
  override def getNext(cas: JCas) = {
    val file = files.dequeue
    
    val text = scala.io.Source.fromFile(file).mkString
    val pf = new MediaWikiParserFactory();
    val parser = pf.createParser();
    val page = parser.parse(text);
    
    cas.setDocumentLanguage(getLocale.getLanguage)
    cas.setDocumentText(page.getParagraphs.foldLeft(""){ (t, p) => t + " " +p.getText })
    val doc = new DocumentAnnotation(cas,0,0)
    doc.setName(file.getName)
    doc.setSource(file.getAbsolutePath)
    doc.addToIndexes
  }
}

object WikipediaReader {
	def apply(path: String, pattern: String = ".*\\.txt", locale: String = "en"): CollectionReader =
	    CollectionReaderFactory.createCollectionReader(classOf[WikipediaReader],
	      TextFileReader.PARAM_PATH, path,
	      TextFileReader.PARAM_FILENAME_PATTERN, pattern,
	      Configuration.PARAM_LOCALE, locale
	    )
}

/**
 * Removes token with length smaller than 3
 */
class TokenCleanup extends SCasAnnotator_ImplBase {
  def process(cas: JCas) {
    cas.select(classOf[Token]).filter(_.getCoveredText.length < 3).foreach(_.removeFromIndexes)
  }
}

/**
 * Token cleanup factory
 */
object TokenCleanup {
  def apply() = AnalysisEngineFactory.createPrimitive(classOf[TokenCleanup])
}