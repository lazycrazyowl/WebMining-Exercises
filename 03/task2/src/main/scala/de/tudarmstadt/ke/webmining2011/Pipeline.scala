package de.tudarmstadt.ke.webmining2011

import annotator.{TokenCleanup, TermFrequency, SnowballStemmer}
import consumer.{SVMConsumer, IDFConsumer}
import org.uimafit.pipeline.SimplePipeline
import reader.CourseReader
import jenshaase.uimaScala.toolkit.annotator.{StopwordTagger, BreakIteratorTokenizer}


object Pipeline {

  def main(args: Array[String]) = {
    /*SimplePipeline.runPipeline(
      CourseReader("src/main/resources/train"),
      BreakIteratorTokenizer(),
      TokenCleanup(),
      StopwordTagger("src/main/resources/stopwords/english.txt"),
      SnowballStemmer(),
      TermFrequency(),
      IDFConsumer()
    ) */

    /*SimplePipeline.runPipeline(
      CourseReader("src/main/resources/train"),
      BreakIteratorTokenizer(),
      TokenCleanup(),
      StopwordTagger("src/main/resources/stopwords/english.txt"),
      SnowballStemmer(),
      TermFrequency(),
      SVMConsumer("train")
    )*/

    SimplePipeline.runPipeline(
      CourseReader("src/main/resources/test"),
      BreakIteratorTokenizer(),
      TokenCleanup(),
      StopwordTagger("src/main/resources/stopwords/english.txt"),
      SnowballStemmer(),
      TermFrequency(),
      SVMConsumer("test")
    )
  }
}