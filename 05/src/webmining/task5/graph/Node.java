package webmining.task5.graph;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node {
	private final int id;
	private String url;
	private Set<String> document;
	private double pageRank;

	public double getPageRank() {
		return pageRank;
	}

	public void setPageRank(double pageRank) {
		this.pageRank = pageRank;
	}

	public Set<String> getDocument() {
		return document;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Node(int id) {
		super();
		this.id = id;
		this.successors = new HashSet<Node>();
		this.predecessors = new HashSet<Node>();
	}

	public int getId() {
		return id;
	};

	private final Set<Node> successors;
	private final Set<Node> predecessors;

	public void linkNeighbor(Node toNode) {
		this.successors.add(toNode);
		toNode.predecessors.add(this);
	}

	public void setDocument(Set<String> document) {
		this.document = document;

	}

	public Set<Node> predecessors() {
		return Collections.unmodifiableSet(this.predecessors);

	}

	public double successorCount() {
		return successors.size();
	}

	public Set<Node> successors() {
		return Collections.unmodifiableSet(this.successors);
	}

	public boolean find(String subQuery) {
		return document.contains(subQuery);
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.#####");
		return "#" + this.getId() + "	PR(" + df.format(this.getPageRank())
				+ "), " + this.getUrl();

	}
}
