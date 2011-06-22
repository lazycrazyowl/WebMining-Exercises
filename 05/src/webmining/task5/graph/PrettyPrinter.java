package webmining.task5.graph;

public class PrettyPrinter {

	String result = "";

	private void addLine(String line) {
		result += line + "\n";
	}

	public String print(DirectedGraph g) {
		this.result = "";

		addLine("digraph{");
		printNodes(g);

		addLine("}");

		return result;
	}

	private void printNodes(DirectedGraph g) {
		for (Node n : g.nodes()) {
			addLine(n.getId() + "[label=\"" + n.getPageRank() + "\"];");
		}
	}
}
