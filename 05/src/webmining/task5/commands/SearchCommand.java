package webmining.task5.commands;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import webmining.helpers.Log;
import webmining.task5.graph.DirectedGraph;
import webmining.task5.graph.Node;

public class SearchCommand implements Command {

	private final String[] query;
	private final int resultCount;

	public SearchCommand(String[] query, int resultCount) {
		super();
		this.query = query;
		this.resultCount = resultCount;
	}

	@Override
	public void execute(DirectedGraph g) {
		Log.info("Query is:" + join(query, " + "));
		SortedSet<Node> resultSet = search(g);
		Log.info("Found " + resultSet.size() + " hits.");
		printResults(resultSet);
	}

	private String join(String[] query, String delimiter) {
		String r = "";
		for (String e : query)
			r += e + delimiter;
		return r.substring(0, r.length() - delimiter.length());
	}

	private void printResults(SortedSet<Node> resultSet) {

		Iterator<Node> iter = resultSet.iterator();
		for (int i = 0; i < Math.min(resultSet.size(), resultCount); i++) {
			printResult(iter.next());
		}

	}

	private void printResult(Node next) {
		Log.info(next.toString());
	}

	private SortedSet<Node> search(DirectedGraph g) {
		SortedSet<Node> result = new TreeSet<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if (o1.getPageRank() < o2.getPageRank())
					return 1;
				return -1;
			}
		});

		for (Node page : g.nodes()) {

			boolean isMatch = true;
			for (String subQuery : query) {
				isMatch &= page.find(subQuery.toLowerCase());
			}

			if (isMatch)
				result.add(page);
		}
		return result;
	}
}
