package webmining.task5.commands;

import java.text.BreakIterator;
import java.util.HashSet;

import webmining.helpers.File;
import webmining.helpers.Log;
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
			String content = File.read(dataDir + "/" + n.getId() + ".txt");

			n.setDocument(splitDocument(content));
		}

	}

	private HashSet<String> splitDocument(String content) {
		HashSet<String> words = new HashSet<String>();
		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(content);

		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary
				.next()) {
			words.add(content.substring(start, end).toLowerCase());
		}

		Log.debug("Read " + words.size() + " words.");
		return words;
	}

}
