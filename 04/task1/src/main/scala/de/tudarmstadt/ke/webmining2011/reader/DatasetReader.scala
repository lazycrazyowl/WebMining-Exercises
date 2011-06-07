package de.tudarmstadt.ke.webmining2011.reader

import de.tudarmstadt.ke.webmining2011.types.Category
import java.io.File
import org.apache.uima.collection.CollectionReader
import org.apache.uima.jcas.JCas
import org.apache.uima.util.ProgressImpl
import org.apache.uima.UimaContext
import org.jsoup.Jsoup
import org.uimafit.component.JCasCollectionReader_ImplBase
import org.uimafit.descriptor.ConfigurationParameter
import org.uimafit.factory.{ CollectionReaderFactory, TypeSystemDescriptionFactory }
import scala.collection.mutable.Queue

class DatasetReader extends JCasCollectionReader_ImplBase {

  @ConfigurationParameter(name = DatasetReader.PARAM_PATH, mandatory = true)
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

    val cat = file.getParentFile.getName

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

object DatasetReader {
  final val PARAM_PATH = "Path"

  def apply(path: String): CollectionReader = {
    CollectionReaderFactory.createCollectionReader(classOf[DatasetReader],
      TypeSystemDescriptionFactory.createTypeSystemDescription,
      PARAM_PATH, path)
  }
}