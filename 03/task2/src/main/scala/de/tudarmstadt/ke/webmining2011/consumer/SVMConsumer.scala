package de.tudarmstadt.ke.webmining2011.consumer

import org.uimafit.component.JCasConsumer_ImplBase
import org.apache.uima.UimaContext
import io.Source
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.core.Implicits._
import de.tudarmstadt.ke.webmining2011.types.{Category, TokenWOStopwordTF, StemTF, TokenTF}
import java.io.{FileWriter, BufferedWriter}
import org.uimafit.descriptor.ConfigurationParameter
import org.uimafit.factory.AnalysisEngineFactory

class SVMConsumer extends JCasConsumer_ImplBase {

  @ConfigurationParameter(name=SVMConsumer.PARAM_MODE, mandatory=true)
  protected var mode: String = null

  val sortedTokens = read("tokens.txt")
  val sortedStems = read("stems.txt")
  val sortedTokenWOStopwords = read("tokensWOStopwords.txt")

  var tokensVectors: List[Pair[Int, List[Float]]] = List.empty
  var stemsVectors: List[Pair[Int, List[Float]]] = List.empty
  var tokenWOStopwordsVectors: List[Pair[Int, List[Float]]] = List.empty

  def process(cas: JCas) = {
    val tokensTF = cas.select(classOf[TokenTF]).map(tf => Pair(tf.getToken, tf.getPropability)).toMap
    val stemTF = cas.select(classOf[StemTF]).map(tf => Pair(tf.getToken, tf.getPropability)).toMap
    val tokenWOStopwordTF = cas.select(classOf[TokenWOStopwordTF]).map(tf => Pair(tf.getToken, tf.getPropability)).toMap

    val docClass: Int = cas.selectSingle(classOf[Category]).getCategory match {
      case "non-course" => -1
      case "course" => 1
      case _ => throw new Exception("This is not a valid category")
    }

    /*tokensVectors = tokensVectors :+
      Pair(docClass, sortedTokens.map(kv => kv._2.toFloat * tokensTF.getOrElse(kv._1, 0f)))*/
    /*stemsVectors = stemsVectors :+
      Pair(docClass, sortedStems.map(kv => kv._2.toFloat * stemTF.getOrElse(kv._1, 0f)))*/
    tokenWOStopwordsVectors = tokenWOStopwordsVectors :+
      Pair(docClass, sortedTokenWOStopwords.map(kv => kv._2.toFloat * tokenWOStopwordTF.getOrElse(kv._1, 0f)))
  }

  override def collectionProcessComplete() = {
    /*var n = 50;
    while (n < sortedTokens.size) {
      write(tokensVectors.map(p => Pair(p._1, p._2.take(n))), n, "token")
      n *= 2;
    } */

    /*var n = 50;
    while (n < sortedStems.size) {
      write(stemsVectors.map(p => Pair(p._1, p._2.take(n))), n, "stem")
      n *= 2;
    } */

    var n = 50;
    while (n < sortedTokenWOStopwords.size) {
      write(tokenWOStopwordsVectors.map(p => Pair(p._1, p._2.take(n))), n, "tokenWOStopword")
      n *= 2;
    }
  }

  def read(filename: String) = Source.fromFile("src/main/resources/idfs/" + filename).getLines.map(s => {
      val splits = s.split(" ")
      (splits(0), splits(1).toFloat)
    }).toList

  def write(data: List[Pair[Int, List[Float]]], n: Int, t: String) = {
    val writer = new BufferedWriter(new FileWriter("src/main/resources/result/"+mode+"/" + t+"."+n+".txt"))

    data.foreach(line => {
      writer.write(line._1 + " ")
      var index = 1
      line._2.foreach(v => {
        writer.write(index + ":" + v + " ")
        index += 1
      })
      writer.write("\n")
    })
    writer.close
  }
}

object SVMConsumer {
  final val PARAM_MODE = "mode"

  def apply(mode: String) = AnalysisEngineFactory.
    createPrimitive(classOf[SVMConsumer],
    PARAM_MODE, mode)
}