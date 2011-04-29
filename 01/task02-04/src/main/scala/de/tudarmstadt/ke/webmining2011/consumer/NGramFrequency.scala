package de.tudarmstadt.ke.webmining2011.consumer

import org.uimafit.component.JCasConsumer_ImplBase
import org.apache.uima.jcas.JCas
import org.uimafit.factory.AnalysisEngineFactory

class NGramFrequency extends JCasConsumer_ImplBase {
  def process(cas: JCas) = {

  }

  override def collectionProcessComplete() = {

  }
}

object NGramFrequency {
  def apply() = AnalysisEngineFactory.createPrimitive(classOf[NGramFrequency])
}