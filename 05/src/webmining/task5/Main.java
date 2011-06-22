package webmining.task5;

import webmining.helpers.Log;
import webmining.task5.commands.AnnotateDocumentCommand;
import webmining.task5.commands.AnnotateUrlCommand;
import webmining.task5.commands.Executor;
import webmining.task5.commands.PageRankCommand;
import webmining.task5.commands.PrintDirectedGraphCommand;
import webmining.task5.graph.DirectedGraph;
import webmining.task5.graph.reader.DotFileReader;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DotFileReader rdr = new DotFileReader(
				"/home/frank/workspace_scala/WebMining-Exercises/05/data/graph.dot");

		DirectedGraph g = rdr.createDirectedGraph();
		Log.debug("Read " + g.nodeCount() + " nodes to the directed graph");

		Executor e = new Executor(
				new AnnotateDocumentCommand(
						"/home/frank/workspace_scala/WebMining-Exercises/05/data"),
				new AnnotateUrlCommand(
						"/home/frank/workspace_scala/WebMining-Exercises/05/data/graph.dot"),
				new PageRankCommand(), new PrintDirectedGraphCommand(
						"/home/frank/Desktop/pr_graph.dot"));

		e.execute(g);

		Log.debug("Done");
	}
}
