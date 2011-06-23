package webmining.task5.graph.reader;


import java.util.HashMap;
import java.util.Map;

import webmining.helpers.File;

public class DictionaryReader {
	private final String dictFile;

	public DictionaryReader(String dictFile) {
		super();
		this.dictFile = dictFile;
	}

	public Map<Integer, String> read() {
		Map<Integer, String> dict = new HashMap<Integer, String>();
		for (String line : File.readLines(dictFile))
			processLine(dict, line);
		return dict;
	}

	private void processLine(Map<Integer, String> dict, String line) {
		String[] tokens = line.split("->");

		if (tokens.length == 2) {
			int id = Integer.parseInt(tokens[0].trim());
			dict.put(id, tokens[1].trim());
		}
	}
}
