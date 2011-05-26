package de.tudarmstadt.ke.webmining2011.annotator

import jenshaase.uimaScala.core.SCasAnnotator_ImplBase
import org.apache.uima.UimaContext
import org.apache.uima.jcas.JCas
import org.tartarus.snowball.ext.englishStemmer
import jenshaase.uimaScala.toolkit.types.Token
import jenshaase.uimaScala.core.Implicits._
import de.tudarmstadt.ke.webmining2011.types.Stem
import org.uimafit.factory.AnalysisEngineFactory

class SnowballStemmer extends SCasAnnotator_ImplBase {

  val stemmer = new englishStemmer()

  def process(cas: JCas) = {
    cas.select(classOf[Token]).foreach(t => {
      stemmer.setCurrent(t.getCoveredText)
      stemmer.stem
      val stem = stemmer.getCurrent

      if (stem.length > 0) new Stem(cas, t.getBegin, t.getBegin+stem.length).addToIndexes
    })
  }
}

object SnowballStemmer {

  def apply() = AnalysisEngineFactory.createPrimitive(classOf[SnowballStemmer])
}