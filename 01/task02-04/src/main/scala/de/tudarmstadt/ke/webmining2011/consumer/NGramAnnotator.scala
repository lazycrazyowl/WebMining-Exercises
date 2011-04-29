package de.tudarmstadt.ke.webmining2011.consumer

import jenshaase.uimaScala.core.SCasAnnotator_ImplBase
import org.uimafit.descriptor.ConfigurationParameter
import org.apache.uima.jcas.JCas
import jenshaase.uimaScala.toolkit.types.Token
import jenshaase.uimaScala.core.SCasAnnotator_ImplBase
import jenshaase.uimaScala.core.Implicits._
import de.tudarmstadt.ke.webmining.types.{Bigramm, Unigramm}
import org.uimafit.factory.AnalysisEngineFactory
import org.apache.uima.jcas.tcas.Annotation

class NGramAnnotator extends SCasAnnotator_ImplBase {

  @ConfigurationParameter(name = NGramAnnotator.PARAM_N, mandatory = true)
  protected var n: Int = 0

  def process(cas: JCas) {
    cas.select(classOf[Token]).foreach(t => {
      for (i <- t.getBegin to t.getEnd-n) {
        add(createAnnotation(cas, i , i+n))
      }
    })
  }

  def createAnnotation(cas: JCas, begin: Int, end: Int): Annotation = n match {
    case 1 => new Unigramm(cas, begin, end)
    case 2 => new Bigramm(cas, begin, end)
    case _ => throw new Exception("No valid n")
  }
}

object NGramAnnotator {
  final val PARAM_N = "n"

  def apply(n: Int) = AnalysisEngineFactory.createPrimitive(classOf[NGramAnnotator], PARAM_N, n.asInstanceOf[AnyRef])
}