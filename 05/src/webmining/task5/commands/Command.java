package webmining.task5.commands;

import webmining.task5.graph.DirectedGraph;

public interface Command {
	public void execute(DirectedGraph g);

}
