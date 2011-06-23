package de.tudarmstadt.ke.webmining2011

import org.uimafit.pipeline.SimplePipeline
import jenshaase.uimaScala.toolkit.annotator.BreakIteratorTokenizer
import de.tudarmstadt.ke.webmining2011.reader._
import de.tudarmstadt.ke.webmining2011.annotator._
import de.tudarmstadt.ke.webmining2011.consumer._

object Pipeline {
  
  def main(args: Array[String]) = {
     println("Start pipeline")
    /*for (i <- List(100, 1000, 10000, 50000, 100000, -1)) {
    	val tokenFile = "src/main/resources/result/tokens-" + i + ".txt"
    	val trainingFile = "src/main/resources/result/training-" + i + ".txt"
    	val testFile = "src/main/resources/result/test-" + i + ".txt"
      
	    SimplePipeline.runPipeline(
	        DatasetReader("src/main/resources/data/training"),
	        BreakIteratorTokenizer(),
	        TokenCleanup(),
	        TermFrequency(),
	        TokenConsumer(tokenFile, i)
	    )
	    
	    SimplePipeline.runPipeline(
	        DatasetReader("src/main/resources/data/training"),
	        BreakIteratorTokenizer(),
	        TokenCleanup(),
	        TermFrequency(),
	        TFConsumer(tokenFile, trainingFile)
	    )
	    
	    SimplePipeline.runPipeline(
	        DatasetReader("src/main/resources/data/test"),
	        BreakIteratorTokenizer(),
	        TokenCleanup(),
	        TermFrequency(),
	        TFConsumer(tokenFile, testFile)
	    )
    }*/
  }

}
