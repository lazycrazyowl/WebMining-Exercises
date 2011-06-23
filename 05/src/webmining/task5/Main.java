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
		DotFileReader rdr = new DotFileReader("data/graph.dot");

		DirectedGraph g = rdr.createDirectedGraph();
		Log.debug("Read " + g.nodeCount() + " nodes to the directed graph");

		Executor e = new Executor(new AnnotateDocumentCommand("data"),
				new AnnotateUrlCommand("data/node.dict"),
				new PageRankCommand(), new PrintDirectedGraphCommand(
						"pr_graph.dot"));

		e.execute(g);

		Log.debug("Done");
	}
}
