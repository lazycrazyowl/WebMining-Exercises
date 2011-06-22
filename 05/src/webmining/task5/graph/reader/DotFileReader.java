package webmining.task5.graph.reader;

import webmining.helpers.File;
import webmining.task5.graph.DirectedGraph;
import webmining.task5.graph.Node;

public class DotFileReader {
	private final String dotFile;

	public DotFileReader(String dotFile) {
		super();
		this.dotFile = dotFile;
	}

	public DirectedGraph createDirectedGraph() {

		DirectedGraph graph = new DirectedGraph();
		for (String line : File.readLines(dotFile)) {
			processLine(line, graph);
		}
		return graph;
	}

	private void processLine(String line, DirectedGraph graph) {
		String[] tokens = line.split("->");
		if (tokens.length == 2) {
			int from = Integer.parseInt(tokens[0].trim());
			int to = Integer.parseInt(tokens[1].trim());

			Node fromNode = graph.findOrCreate(from);
			Node toNode = graph.findOrCreate(to);
			fromNode.linkNeighbor(toNode);
		}
	}

}
