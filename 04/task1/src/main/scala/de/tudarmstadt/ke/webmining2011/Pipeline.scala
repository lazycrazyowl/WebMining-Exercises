package de.tudarmstadt.ke.webmining2011

import org.uimafit.pipeline.SimplePipeline
import jenshaase.uimaScala.toolkit.annotator.BreakIteratorTokenizer
import de.tudarmstadt.ke.webmining2011.reader._
import de.tudarmstadt.ke.webmining2011.annotator._
import de.tudarmstadt.ke.webmining2011.consumer._

object Pipeline {
  
  def main(args: Array[String]) = {
    SimplePipeline.runPipeline(
        DatasetReader("src/main/resources/data/training"),
        BreakIteratorTokenizer(),
        TokenCleanup(),
        TermFrequency(),
        TokenConsumer("src/main/resources/result/tokens.txt")
    )
    
    SimplePipeline.runPipeline(
        DatasetReader("src/main/resources/data/training"),
        BreakIteratorTokenizer(),
        TokenCleanup(),
        TermFrequency(),
        TFConsumer("src/main/resources/result/tokens.txt", "src/main/resources/result/training.txt")
    )
    
    SimplePipeline.runPipeline(
        DatasetReader("src/main/resources/data/test"),
        BreakIteratorTokenizer(),
        TokenCleanup(),
        TermFrequency(),
        TFConsumer("src/main/resources/result/tokens.txt", "src/main/resources/result/test.txt")
    )
  }

}