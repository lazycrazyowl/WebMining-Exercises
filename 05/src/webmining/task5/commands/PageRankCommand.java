package webmining.task5.commands;

import webmining.helpers.Log;
import webmining.task5.graph.DirectedGraph;
import webmining.task5.graph.Node;

public class PageRankCommand implements Command {

	private double dumpingFactor = 0.85;

	@Override
	public void execute(DirectedGraph g) {
		initPagerank(g);

		int iterations = 0;
		double pageRankChange = 1;

		while (!isCalculationFinished(iterations, pageRankChange)) {
			iterations++;
			pageRankChange = 0;
			for (Node n : g.nodes())
				pageRankChange += pageRank(g, n);
		}
		Log.debug("Ranking done after " + iterations + " iterations");
	}

	private boolean isCalculationFinished(int iterations, double pageRankChange) {
		return iterations > 10000 || pageRankChange < 1.0 / 10000;
	}

	private double pageRank(DirectedGraph g, Node n) {
		double pageRank = (1 - dumpingFactor) / g.nodeCount() + dumpingFactor
				* (pedecessorPageRankSum(g, n));

		double delta = Math.abs(n.getPageRank() - pageRank);
		n.setPageRank(pageRank);
		return delta;
	}

	private double pedecessorPageRankSum(DirectedGraph g, Node n) {
		double pr = 0;
		for (Node pred : n.predecessors()) {
			pr += pred.getPageRank() / pred.successorCount();
		}
		return pr;
	}

	private void initPagerank(DirectedGraph g) {
		for (Node n : g.nodes())
			n.setPageRank(1.0 / g.nodeCount());
	}
}
