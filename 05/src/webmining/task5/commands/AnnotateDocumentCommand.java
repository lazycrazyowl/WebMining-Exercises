package webmining.task5.commands;

import webmining.helpers.File;
import webmining.task5.graph.DirectedGraph;
import webmining.task5.graph.Node;

public class AnnotateDocumentCommand implements Command {

	private final String dataDir;

	public AnnotateDocumentCommand(String dataDir) {
		super();
		this.dataDir = dataDir;
	}

	@Override
	public void execute(DirectedGraph graph) {
		for (Node n : graph.nodes()) {
			n.setDocument(File.read(dataDir + "/" + n.getId() + ".txt"));
		}

	}

}
