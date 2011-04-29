package de.tudarmstadt.ke.webmining2011.consumer

import org.uimafit.component.JCasConsumer_ImplBase
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.core.Implicits._
import org.uimafit.pipeline.SimplePipeline
import jenshaase.uimaScala.toolkit.reader.TextFileReader
import jenshaase.uimaScala.toolkit.annotator.StopwordRemover
import org.uimafit.factory.AnalysisEngineFactory
import jenshaase.uimaScala.toolkit.annotator.BreakIteratorTokenizer
import org.apache.commons.io.FileUtils
import java.io.File
import org.apache.uima.jcas.tcas.Annotation
import org.uimafit.descriptor.ConfigurationParameter
import collection.immutable.Map._
import de.tudarmstadt.ke.webmining.types._
import jenshaase.uimaScala.toolkit.types.{DocumentAnnotation, Token}

/**
 * Analyses the freqeuency of words to draw
 * a zipf graph
 *
 * Generates a data file that can be used to
 * create a gnuplot
 *
 * All supclasses must say which token they want to process
 */
abstract class ZipfAnalyser extends JCasConsumer_ImplBase {

  @ConfigurationParameter(name=ZipfAnalyser.OUTPUT_FOLDER, mandatory = true)
  protected var outputFolder: File = null

  var tokens: Map[String, Int] = Map.empty

  def process(cas: JCas) = {
    var docTokens: Map[String, Int] = Map.empty

    this.select(cas).foreach(a => {
      val token = a.getCoveredText.toLowerCase

      docTokens += ((token, docTokens.get(token).getOrElse(0) +1))
      tokens += ((token, tokens.get(token).getOrElse(0) + 1))
    })

    writeData(docTokens, cas.selectSingle(classOf[DocumentAnnotation]).getName)
  }

  override def collectionProcessComplete() = {
    writeData(tokens, "all.txt")
  }

  def writeData(data: Map[String, Int], filename: String) = {
    var i = 0
    val zipf = data.toList.sortBy(_._2).reverse.map(kv => {
      i += 1
      (i, kv._2)
    })
    val freq = zipf.groupBy(_._2).map(kv => (kv._1, kv._2.length)).toList.sortBy(_._1)

    outputFolder.mkdirs
    FileUtils.writeStringToFile(new File(outputFolder, "zipf-"+filename+".gp"), zipf.map(kv => kv._1 + "\t" + kv._2).reduceLeft(_+"\n"+_))
    FileUtils.writeStringToFile(new File(outputFolder, "freq-"+filename+".gp"), freq.map(kv => kv._1 + "\t" + kv._2).reduceLeft(_+"\n"+_))
  }

  def select(cas: JCas): Iterable[Annotation]
}

/**
 * Static data for ZipfAnalyser
 */
object ZipfAnalyser {
  final val OUTPUT_FOLDER = "outputFolder"
}

/**
 * Processes the token type
 */
class TokenZipfAnalyser extends ZipfAnalyser {
  def select(cas: JCas) = cas.select(classOf[Token])
}

/**
 * TokenZipfAnalyser factory
 */
object TokenZipfAnalyser {
  def apply(outputFolder: String) = AnalysisEngineFactory.createPrimitive(classOf[TokenZipfAnalyser],
    ZipfAnalyser.OUTPUT_FOLDER, outputFolder
  )
}

/**
 * Processes the Unigramm type
 */
class UnigrammZipfAnalyser extends ZipfAnalyser {
  def select(cas: JCas) = cas.select(classOf[Unigramm])
}

/**
 * UnigrammZipfAnalyser factory
 */
object UnigrammZipfAnalyser {
  def apply(outputFolder: String) = AnalysisEngineFactory.createPrimitive(classOf[UnigrammZipfAnalyser],
    ZipfAnalyser.OUTPUT_FOLDER, outputFolder
  )
}

/**
 * Processes the unigramm type
 */
class BigrammZipfAnalyser extends ZipfAnalyser {
  def select(cas: JCas) = cas.select(classOf[Bigramm])
}

/**
 * BigrammZipfAnalyser factory
 */
object BigrammZipfAnalyser {
  def apply(outputFolder: String) = AnalysisEngineFactory.createPrimitive(classOf[BigrammZipfAnalyser],
    ZipfAnalyser.OUTPUT_FOLDER, outputFolder
  )
}

/**
 * Prints the 30 most frequent words on the command line
 * for each document
 */
class MostWordsAnalyser extends JCasConsumer_ImplBase {

  @ConfigurationParameter(name=MostWordsAnalyser.OUTPUT_FOLDER, mandatory = true)
  protected var outputFolder: File = null

  def process(cas: JCas) = {
    var words: Map[String, Int] = Map.empty

    // count words
    cas.select(classOf[Token]).foreach(t => {
      val token = t.getCoveredText.toLowerCase

      words = words + ((token, words.get(token).getOrElse(0) + 1))
    })

    // sort by count and print first 30
    outputFolder.mkdirs
    FileUtils.writeStringToFile(new File(outputFolder, cas.selectSingle(classOf[DocumentAnnotation]).getName),
      words.toList.sortBy(_._2).reverse.take(30).map(kv => kv._1 + "\t" + kv._2).reduceLeft(_ + "\n" + _))
  }
}

/**
 * MostWordsAnalyser factory
 */
object MostWordsAnalyser {
  final val OUTPUT_FOLDER = "outputFolder"

  def apply(outputFolder: String) = AnalysisEngineFactory.createPrimitive(classOf[MostWordsAnalyser],
    OUTPUT_FOLDER, outputFolder
  )
}

/**
 * Main application
 * Runs the UIMA pipeline
 */
object Application extends Application {

  // Task 2 / 3
  SimplePipeline.runPipeline(
    TextFileReader(path = "src/main/resources/input/german", locale = "de"),
    BreakIteratorTokenizer(),
    TokenCleanup(),
    MostWordsAnalyser("src/main/resources/results/task2a"),
    TokenZipfAnalyser("src/main/resources/results/task3"),
    StopwordRemover("src/main/resources/stopwords/german.txt"),  // <- remove this line for task 02
    MostWordsAnalyser("src/main/resources/results/task2b")
  )

  // Task 4
  SimplePipeline.runPipeline(
    TextFileReader(path = "src/main/resources/input"),
    BreakIteratorTokenizer(),
    TokenCleanup(),
    NGramAnnotator(1),
    NGramAnnotator(2),
    UnigrammZipfAnalyser("src/main/resources/results/task4/unigram"),
    BigrammZipfAnalyser("src/main/resources/results/task4/bigram")
  )
}