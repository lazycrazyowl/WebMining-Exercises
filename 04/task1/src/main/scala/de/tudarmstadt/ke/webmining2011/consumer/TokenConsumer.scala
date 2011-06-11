package de.tudarmstadt.ke.webmining2011.consumer

import org.uimafit.factory.AnalysisEngineFactory
import org.uimafit.component.JCasConsumer_ImplBase
import java.io.File
import org.uimafit.descriptor.ConfigurationParameter
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.core.Implicits._
import de.tudarmstadt.ke.webmining2011.types.TokenTF
import java.io.BufferedWriter
import java.io.FileWriter

class TokenConsumer extends JCasConsumer_ImplBase {

  @ConfigurationParameter(name = TokenConsumer.PARAM_OUTPUT_PATH, mandatory = true)
  protected var outputFile: File = null
  
  @ConfigurationParameter(name = TokenConsumer.PARAM_MAX_TOKENS, mandatory = false)
  protected var maxTokens: Int = -1
  
  var tokens: Map[String, Int] = Map.empty
  
  def process(cas: JCas) =
    cas.select(classOf[TokenTF]).foreach { t =>
      val count = tokens.getOrElse(t.getToken, 0) + 1
      tokens += ((t.getToken, count))
    }
  
  override def collectionProcessComplete = {
    var writer = new BufferedWriter(new FileWriter(outputFile))
    
    val writeTokens = if (maxTokens > 0 && tokens.size > maxTokens) {
      tokens.toList.sortBy(_._2).take(maxTokens).toMap
    } else {
      tokens
    }
    
    writeTokens.foreach(t => writer.write(t._1 + "\n"))
    writer.close
  }
}

object TokenConsumer {
  final val PARAM_OUTPUT_PATH = "OutputPath"
  final val PARAM_MAX_TOKENS = "maxTokens"

  def apply(outputPath: String, maxTokens: Int = -1) =
    AnalysisEngineFactory.createPrimitive(classOf[TokenConsumer],
      PARAM_OUTPUT_PATH, outputPath,
      PARAM_MAX_TOKENS, maxTokens.asInstanceOf[Object])
}