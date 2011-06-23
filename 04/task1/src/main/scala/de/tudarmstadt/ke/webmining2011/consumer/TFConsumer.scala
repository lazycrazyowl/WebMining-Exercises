package de.tudarmstadt.ke.webmining2011.consumer

import org.uimafit.component.JCasConsumer_ImplBase
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.core.Implicits._
import de.tudarmstadt.ke.webmining2011.types.TokenTF
import org.uimafit.descriptor.ConfigurationParameter
import java.io.File
import org.uimafit.factory.CollectionReaderFactory
import org.uimafit.factory.TypeSystemDescriptionFactory
import org.apache.uima.collection.CollectionReader
import java.io.BufferedWriter
import java.io.FileWriter
import org.uimafit.factory.AnalysisEngineFactory
import de.tudarmstadt.ke.webmining2011.types.Category
import org.apache.uima.UimaContext

class TFConsumer extends JCasConsumer_ImplBase {

  @ConfigurationParameter(name = TFConsumer.PARAM_TOKEN_LIST_PATH, mandatory = true)
  protected var tokenListFile: File = null
  
  @ConfigurationParameter(name = TFConsumer.PARAM_OUTPUT_PATH, mandatory = true)
  protected var outputFile: File = null
  
  var tokens: List[String] = List.empty
  var writer: BufferedWriter = null
  
  override def initialize(context: UimaContext) = {
    super.initialize(context)
    
    tokens = scala.io.Source.fromFile(tokenListFile).getLines.toList
    writer = new BufferedWriter(new FileWriter(outputFile))
  }
  
  def process(cas: JCas) = {
	writer.write(cas.selectSingle(classOf[Category]).getCategory)
	
	val tfs: Map[String, Int] = cas.select(classOf[TokenTF]).
		map(tf => (tf.getToken, tf.getCount)).toMap

    tokens.foreach(t => {
      writer.write(" ")
      writer.write(tfs.getOrElse(t, 0).toString)
    })
    writer.write("\n");
  }
  
  override def collectionProcessComplete = {
    writer.close
  }
}

object TFConsumer {
  final val PARAM_OUTPUT_PATH = "OutputPath"
  final val PARAM_TOKEN_LIST_PATH = "TokenListPath"
  
  def apply(tokenListPath: String, outputPath: String) = 
    AnalysisEngineFactory.createPrimitive(classOf[TFConsumer],
      PARAM_TOKEN_LIST_PATH, tokenListPath,
      PARAM_OUTPUT_PATH, outputPath
    )
}