package de.tudarmstadt.ke.webmining2011.hits
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.File

object Graph {
  
  val index = Index.read("src/main/resources/data/index.txt")
  val completeGraph = readGraph("src/main/resources/data/graph.dot")
  
  def main(args: Array[String]) = {
    search(args.toList.reduceLeft(_ + " " + _))
  }
  
  /**
   * Builds a graph HITS graph based on the keywords
   */
  def search(keyword: String) = {
    println("Search for %s" format keyword)
    val words: Seq[String] = keyword.split(" ").toSeq
    val rootSet: Set[String] = words.foldLeft[Set[String]](Set()){ (s, w) => 
      s ++ index.getOrElse(w, Seq()).toSet
    }
    
    val baseSet: Set[String] = rootSet.foldLeft[Set[String]](Set()){ (s, node) =>
      s ++ 
      	completeGraph.get(node).map(_.incoming.toSet).getOrElse(Set()) ++
      	completeGraph.get(node).map(_.outgoing.toSet).getOrElse(Set())
    }
    var subgraph = completeGraph.
    	filter(kv => baseSet.contains(kv._1)).
    	map { kv =>
    	  (kv._1, Node(
    	      kv._2.name, 
    	      kv._2.incoming.filter(baseSet.contains(_)), 
    	      kv._2.outgoing.filter(baseSet.contains(_))))
    	}
    
    
    var diff = 1f
    var it = 0
    while (it < 100 && diff > (1f/10000f)) {
      val authBefore = subgraph.map(kv => kv._2.auth)
      val hubBefore  = subgraph.map(kv => kv._2.hub)
      
      // update auth values
      var norm = 0f
      subgraph = subgraph.map{ kv =>
      	val node = kv._2
        val auth = node.incoming.foldLeft(0f)((sum, key) => sum + subgraph.get(key).map(_.hub).getOrElse(0f))
        norm += scala.math.pow(auth, 2).toFloat
        (kv._1, node.setAuth(auth))
      }
      norm = scala.math.sqrt(norm).toFloat
      subgraph = subgraph.map( kv => (kv._1, kv._2.setAuth(kv._2.auth / norm)))
      
      // update hubs
      norm = 0f
      subgraph = subgraph.map{ kv =>
      	val node = kv._2
        val hub = node.outgoing.foldLeft(0f)((sum, key) => sum + subgraph.get(key).map(_.auth).getOrElse(0f))
        norm += scala.math.pow(hub, 2).toFloat
        (kv._1, node.setHub(hub))
      }
      norm = scala.math.sqrt(norm).toFloat
      subgraph = subgraph.map( kv => (kv._1, kv._2.setHub(kv._2.hub / norm)))
      
      val diffAuth = authBefore.zip(subgraph.map(kv => kv._2.auth)).foldLeft(0f)((sum, p) => sum + scala.math.abs(p._1 - p._2))
      val diffHub = hubBefore.zip(subgraph.map(kv => kv._2.hub)).foldLeft(0f)((sum, p) => sum + scala.math.abs(p._1 - p._2))
      
      diff = diffAuth + diffHub
      it += 1
    }

    drawGraph(subgraph, "src/main/resources/data/graphes/%s.dot" format keyword.replaceAll(" ", "_"))
    
    println("Completed with %s interations" format it)
    println("Last difference: %s" format diff)
    println("Sorted by auth")
    subgraph.
    	filter(kv => rootSet.contains(kv._1)).
    	toList.
    	sortBy(kv => kv._2.auth).
    	reverse.
    	foreach(kv => println(kv._1 + ": ("+kv._2.auth+", "+kv._2.hub+")"))
    	
    println("Sorted by hub")
	subgraph.
    	filter(kv => rootSet.contains(kv._1)).
    	toList.
    	sortBy(kv => kv._2.hub).
    	reverse.
    	foreach(kv => println(kv._1 + ": ("+kv._2.auth+", "+kv._2.hub+")"))
    
    println("Finished")
  }
  
  /**
   * Reads the complete graph from the file
   */
  def readGraph(inputFile: String): Map[String, Node] = {
    val lines = scala.io.Source.fromFile(inputFile).getLines.drop(1).toList.dropRight(1)
    
    val links = lines.map { l => 
      val d = l.split(" -> ")
      Pair(d(0), d(1))
    }
    
    val outgoing = links.groupBy(_._1).map(kv => (kv._1, kv._2.map(_._2)))
    val incoming = links.groupBy(_._2).map(kv => (kv._1, kv._2.map(_._1)))
    
    lines.foldLeft[Map[String, Node]](Map()){ (map, line) =>
      val key = line.split(" -> ")(0)
      map + (key -> Node(key, incoming.getOrElse(key, List()), outgoing.getOrElse(key, List())))
    }
  }
  
  def drawGraph(graph: Map[String, Node], file: String) = {
    val writer = new BufferedWriter(new FileWriter(new File(file)))
    writer.write("digraph m {\n");
    
    graph.foreach{ kv => 
      val name = kv._1
      kv._2.outgoing.foreach { node =>
        writer.write(kv._2.toString) 
        writer.write(" -> ") 
        writer.write(graph.get(node).map(_.toString).getOrElse(node))
        writer.write("\n")
      }
    }
    
    writer.write("}");
    writer.close
  }
}
    
    
case class Node(name: String, incoming: List[String], outgoing: List[String], auth: Float = 1, hub: Float = 1) {
  def setAuth(i: Float) =
    Node(name, incoming, outgoing, i, hub)
    
  def setHub(i: Float) =
    Node(name, incoming, outgoing, auth, i)
    
  override def toString = 
    "\"" + name + ("(Hub: %s, Auth: %s)" format(scala.math.round(hub*100), scala.math.round(auth*100))) + "\""
}