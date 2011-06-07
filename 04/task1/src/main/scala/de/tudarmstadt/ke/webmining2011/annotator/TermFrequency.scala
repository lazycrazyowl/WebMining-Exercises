package de.tudarmstadt.ke.webmining2011.annotator

import jenshaase.uimaScala.core.SCasAnnotator_ImplBase
import jenshaase.uimaScala.core.Implicits._
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.toolkit.types.{Stopword, Token}
import de.tudarmstadt.ke.webmining2011.types.TokenTF
import org.uimafit.factory.AnalysisEngineFactory

class TermFrequency extends SCasAnnotator_ImplBase {

  def process(cas: JCas) = {
    cas.select(classOf[Token]).
      groupBy(_.getCoveredText).
      map(kv => (kv._1, kv._2.size)).foreach(kv => {
        val a = new TokenTF(cas, 0, cas.getDocumentText.length)
        a.setToken(kv._1)
        a.setCount(kv._2)
        a.addToIndexes
      })
  }
}

object TermFrequency {
  def apply() = AnalysisEngineFactory.createPrimitive(classOf[TermFrequency])
}