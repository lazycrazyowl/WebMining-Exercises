package webmining.task5.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DirectedGraph {
	private final Map<Integer, Node> nodes;

	public DirectedGraph() {
		super();
		this.nodes = new HashMap<Integer, Node>();
	}

	public Node find(int id) {
		return this.nodes.get(id);
	}

	public void add(Node node) {
		this.nodes.put(node.getId(), node);
	}

	public Node findOrCreate(int id) {
		if (this.nodes.containsKey(id))
			return this.nodes.get(id);

		Node n = new Node(id);
		this.nodes.put(id, n);
		return n;
	}

	public int nodeCount() {
		return nodes.size();
	}

	public Collection<Node> nodes() {
		return nodes.values();
	}
}
