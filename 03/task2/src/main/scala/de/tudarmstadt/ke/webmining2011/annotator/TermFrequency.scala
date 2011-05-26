package de.tudarmstadt.ke.webmining2011.annotator

import jenshaase.uimaScala.core.SCasAnnotator_ImplBase
import jenshaase.uimaScala.core.Implicits._
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.toolkit.types.{Stopword, Token}
import de.tudarmstadt.ke.webmining2011.types.{TokenWOStopwordTF, StemTF, Stem, TokenTF}
import org.uimafit.factory.AnalysisEngineFactory

class TermFrequency extends SCasAnnotator_ImplBase {

  def process(cas: JCas) = {
    val totalToken = cas.select(classOf[Token]).size.toFloat
    cas.select(classOf[Token]).
      groupBy(_.getCoveredText).
      map(kv => (kv._1, kv._2.size)).foreach(kv => {
        val a = new TokenTF(cas, 0, cas.getDocumentText.length)
        a.setToken(kv._1)
        a.setCount(kv._2)
        a.setPropability(kv._2.toFloat / totalToken)
        a.addToIndexes
      })

    val stemsWOStopwords = cas.select(classOf[Token]).
      filter(t => cas.selectCovered(classOf[Stopword], t).size == 0).
      map(t => cas.selectCovered(classOf[Stem], t).get(0))

    val totalStem = stemsWOStopwords.size.toFloat
    stemsWOStopwords.
      groupBy(_.getCoveredText).
      map(kv => (kv._1, kv._2.size)).foreach(kv => {
        val a = new StemTF(cas, 0, cas.getDocumentText.length)
        a.setToken(kv._1)
        a.setCount(kv._2)
        a.setPropability(kv._2.toFloat / totalStem)
        a.addToIndexes
      })

    val tokenWithoutStopword = cas.select(classOf[Token]).
      filter(t => cas.selectCovered(classOf[Stopword], t).size == 0)

    tokenWithoutStopword.
      groupBy(_.getCoveredText).map(kv => (kv._1, kv._2.size)).
      foreach(kv => {
        val a = new TokenWOStopwordTF(cas, 0, cas.getDocumentText.length)
        a.setToken(kv._1)
        a.setCount(kv._2)
        a.setPropability(kv._2.toFloat / tokenWithoutStopword.size.toFloat)
        a.addToIndexes
      })
  }
}

object TermFrequency {

  def apply() = AnalysisEngineFactory.createPrimitive(classOf[TermFrequency])
}