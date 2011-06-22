package webmining.task5.graph;

public class PrettyPrinter {

	String result = "";

	private void addLine(String line) {
		result += line + "\n";
	}

	private double minVal;
	private double maxVal;

	public String print(DirectedGraph g) {
		this.result = "";

		minVal = minPageRank(g);
		maxVal = maxPageRank(g);

		addLine("digraph{");
		addLine("node[style=filled];");
		printNodes(g);
		printSucessors(g);

		addLine("}");

		return result;
	}

	private double minPageRank(DirectedGraph g) {
		double min = Double.MAX_VALUE;

		for (Node n : g.nodes())
			min = Math.min(n.getPageRank(), min);

		return min;
	}

	private double maxPageRank(DirectedGraph g) {
		double max = 0;
		for (Node n : g.nodes())
			max = Math.max(n.getPageRank(), max);
		return max;
	}

	private void printSucessors(DirectedGraph g) {
		for (Node n : g.nodes()) {
			for (Node succ : n.successors()) {
				addLine(n.getId() + "->" + succ.getId() + ";");
			}
		}
	}

	private void printNodes(DirectedGraph g) {
		for (Node n : g.nodes()) {
			addLine(n.getId() + "[label=\"" + n.getId()
					+ "\", colorscheme=piyg11, color="
					+ colorize(n.getPageRank()) + "];");
		}
	}

	private String colorize(double val) {
		int color = (int) ((val - minVal) / (maxVal - minVal) * 10);
		return Integer.toString((color + 1));
	}
}
