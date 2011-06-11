package de.tudarmstadt.ke.webmining2011

import scala.io.Source
import java.io.BufferedWriter
import java.io.FileWriter

object NaivesBayes {

  def main(args: Array[String]) = {
    println("Naives Bayes")
    args(0) match {
      case "train" => train(args(1), args(2))
      case "eval" => eval(args(1), args(2))
      case "train-eval" => {
        val tmpDir = "target/tmp-model.txt"
        train(args(1), tmpDir)
        eval(tmpDir, args(2))
      }
    }
  }

  def train(inputFile: String, outputFile: String) = {
    println("start training")
    var model: Map[String, List[Int]] = Map.empty
    var classes: Map[String, Int] = Map.empty

    // Read training file
    Source.fromFile(inputFile).getLines.foreach { l =>
      val data = l.split(" ").toList
      val cat = data.head
      val counts = data.tail.map(_.toInt)

      classes += ((cat, classes.getOrElse(cat, 0) + 1))

      val r = model.get(cat) match {
        case Some(list) => list.zip(counts).map(v => v._1 + v._2)
        case None => counts
      }

      model += ((cat, r))
    }
    println("finished: Read file")

    // normalization of classes
    val totalClasses = classes.map(_._2).reduceLeft(_ + _).toFloat
    val classProb = classes.map(kv => (kv._1, kv._2.toFloat / totalClasses))

    // Smoothing (add 1)
    var smoothing = model.map(kv => (kv._1, kv._2.map(_ + 1)))

    // Normalization
    val total = smoothing.map(_._2).reduceLeft((l1, l2) => l1.zip(l2).map(v => v._1 + v._2))
    val result = smoothing.map(kv => (kv._1, kv._2.zip(total).map(v => v._1.toFloat / v._2.toFloat)))

    println("Start writing file")
    // Write to file
    val writer = new BufferedWriter(new FileWriter(outputFile));
    result.foreach { kv =>
      writer.write(kv._1)
      writer.write(" " + classProb.getOrElse(kv._1, 0f).toString)
      kv._2.foreach(e => writer.write(" " + e.toString))
      writer.write("\n")
    }
    
    writer.close
    println("training finished")
  }

  def eval(modelFile: String, testFile: String) = {
    println("start evaluation")
    val model = Source.fromFile(modelFile).getLines.map { line =>
      val data = line.split(" ")
      (data.head, data.tail.head.toFloat, data.tail.tail.map(_.toFloat))
    }.toList

//    val modelPar = model.par

    val classes = model.map(_._1)

    var j = 0
    val classification = Source.fromFile(testFile).getLines.map { line =>
      val data = line.split(" ")
      val expectedCat = data.head
      val counts = data.tail.map(_.toInt)

      val actualCat = model.map { triple =>
        var sum = Math.log(triple._2)
        for (i <- 0 to triple._3.size - 1) {
          sum += Math.log(Math.pow(triple._3(i), counts(i)))
        }

        (triple._1, sum)
      }.maxBy(_._2)._1

      j += 1
      if (j % 100 == 0) println("Processed: " + j)

      (expectedCat, actualCat)
    }.toList

    val matrix = classes.map { c1 =>
      classes.map { c2 =>
        classification.filter(_ == Pair(c1, c2)).size
      }
    }

    println(classes)
    matrix.foreach(println)
  }
}
