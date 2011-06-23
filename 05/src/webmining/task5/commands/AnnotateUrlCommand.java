package webmining.task5.commands;

import java.util.Map;

import webmining.task5.graph.DirectedGraph;
import webmining.task5.graph.reader.DictionaryReader;

public class AnnotateUrlCommand implements Command {

	private final String dictFile;

	public AnnotateUrlCommand(String dictFile) {
		super();
		this.dictFile = dictFile;
	}

	@Override
	public void execute(DirectedGraph graph) {
		DictionaryReader rdr = new DictionaryReader(dictFile);
		for (Map.Entry<Integer, String> entry : rdr.read().entrySet()) {
			graph.find(entry.getKey()).setUrl(entry.getValue());
		}
	}
}
