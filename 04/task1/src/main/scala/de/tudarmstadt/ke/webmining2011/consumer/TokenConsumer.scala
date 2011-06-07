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
  
  var tokens: Set[String] = Set.empty
  
  def process(cas: JCas) = tokens ++= cas.select(classOf[TokenTF]).map(_.getToken)
  
  override def collectionProcessComplete = {
    var writer = new BufferedWriter(new FileWriter(outputFile))
    tokens.foreach(t => writer.write(t + "\n"))
    writer.close
  }
}

object TokenConsumer {
  final val PARAM_OUTPUT_PATH = "OutputPath"

  def apply(outputPath: String) =
    AnalysisEngineFactory.createPrimitive(classOf[TokenConsumer],
      PARAM_OUTPUT_PATH, outputPath)
}