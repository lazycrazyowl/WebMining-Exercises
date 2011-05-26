package de.tudarmstadt.ke.webmining2011.reader

import org.uimafit.component.JCasCollectionReader_ImplBase
import org.uimafit.descriptor.ConfigurationParameter
import java.io.File
import org.apache.uima.collection.CollectionReader
import org.uimafit.factory.{TypeSystemDescriptionFactory, CollectionReaderFactory}
import collection.mutable.Queue
import org.apache.uima.UimaContext
import org.apache.uima.jcas.JCas
import org.apache.uima.util.ProgressImpl
import org.jsoup.Jsoup
import de.tudarmstadt.ke.webmining2011.types.Category

class CourseReader extends JCasCollectionReader_ImplBase {

  @ConfigurationParameter(name=CourseReader.PARAM_PATH, mandatory=true)
  protected var path: File = null

  var files: Queue[File] = null

  var total: Int = 0

  override def initialize(context: UimaContext) = {
    files = collectFiles(path)
    total = files.size
  }

  def getNext(cas: JCas) = {
    val file = files.dequeue

    var text: String = ""
    try {
      val data = scala.io.Source.fromFile(file, "UTF-8").mkString
      text = Jsoup.parse(data).text
    } catch {
      case _ => { println("Read error for: " + file.toString); text = "" }
    }

    cas.setDocumentText(text)

    val cat = if (file.getAbsolutePath.indexOf("non-course") >= 0) {
      "non-course"
    } else {
      "course"
    }

    val catAnnotation = new Category(cas, 0, text.length)
    catAnnotation.setCategory(cat)
    catAnnotation.addToIndexes
  }

  def hasNext =
    !files.isEmpty

  def getProgress =
    Array(new ProgressImpl(total - files.size, total, "file"))

  def collectFiles(path: File): Queue[File] = {
    val files = path.listFiles
    Queue() ++= files.filter(f => f.isFile) ++
      files.filter(_.isDirectory).flatMap(collectFiles)
  }
}

object CourseReader {
  final val PARAM_PATH = "Path"

  def apply(path: String): CollectionReader = {
    CollectionReaderFactory.createCollectionReader(classOf[CourseReader],
      TypeSystemDescriptionFactory.createTypeSystemDescription,
      PARAM_PATH, path
    )
  }
}