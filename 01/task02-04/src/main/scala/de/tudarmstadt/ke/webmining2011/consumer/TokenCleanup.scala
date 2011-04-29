package de.tudarmstadt.ke.webmining2011.consumer

import jenshaase.uimaScala.core.SCasAnnotator_ImplBase
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.toolkit.types.Token
import org.uimafit.factory.AnalysisEngineFactory
import jenshaase.uimaScala.core.Implicits._

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