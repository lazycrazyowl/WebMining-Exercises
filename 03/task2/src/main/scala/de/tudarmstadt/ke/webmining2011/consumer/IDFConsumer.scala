package de.tudarmstadt.ke.webmining2011.consumer

import org.uimafit.component.JCasConsumer_ImplBase
import jenshaase.uimaScala.core.Implicits._
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.toolkit.types.{Stopword, Token}
import collection.immutable.Map._
import java.io.{BufferedWriter, FileWriter}
import org.uimafit.factory.AnalysisEngineFactory
import de.tudarmstadt.ke.webmining2011.types.{TokenWOStopwordTF, StemTF, TokenTF, Stem}

class IDFConsumer extends JCasConsumer_ImplBase {

  var tokens: Map[String, List[JCas]] = Map.empty
  var stems: Map[String, List[JCas]] = Map.empty
  var tokensWOStopwords: Map[String, List[JCas]] = Map.empty
  var docs: List[JCas] = List.empty

  def process(cas: JCas) = {
    docs = docs :+ cas

    cas.select(classOf[TokenTF]).foreach(tf => {
      val l = tokens.get(tf.getToken).getOrElse(List())
      tokens += ((tf.getToken, l ++ List(cas)))
    })

    cas.select(classOf[StemTF]).foreach(tf => {
      val l = stems.get(tf.getToken).getOrElse(List())
      stems += ((tf.getToken, l ++ List(cas)))
    })

    cas.select(classOf[TokenWOStopwordTF]).foreach(tf => {
      val l = tokensWOStopwords.get(tf.getToken).getOrElse(List())
      tokensWOStopwords += ((tf.getToken, l ++ List(cas)))
    })
  }

  override def collectionProcessComplete() = {
    val tokenIDFs = write(idfs(tokens), "tokens.txt")
    val stemIDFs = write(idfs(stems), "stems.txt")
    val tokenWOStopwordIDFs = write(idfs(tokensWOStopwords), "tokensWOStopwords.txt")
  }

  protected def idfs(wordList: Map[String, List[JCas]]) = {
    wordList.
      map(kv => (kv._1, scala.math.log(docs.length.toDouble / kv._2.size.toDouble).toFloat)).
      toList.
      sortBy(_._2)
  }

  protected def write(data: List[Pair[String, Float]], filename: String) = {
    val writer = new BufferedWriter(new FileWriter("src/main/resources/idfs/" + filename))
    writer.write(data.map(kv => kv._1 + " " + kv._2).reduceLeft(_ + "\n" + _))
    writer.close
  }
}

object IDFConsumer {
  def apply() = AnalysisEngineFactory.createPrimitive(classOf[IDFConsumer])
}