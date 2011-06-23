package webmining.task5.commands;

import java.util.Collection;
import java.util.Vector;

import webmining.helpers.Log;
import webmining.task5.graph.DirectedGraph;

public class Executor {
	private final Collection<Command> commands;

	public Executor(Command... c) {
		commands = new Vector<Command>();
		for (Command cmd : c)
			commands.add(cmd);
	}

	public void execute(DirectedGraph g) {
		for (Command c : this.commands) {
			Log.debug("Executing command " + c.getClass().getName());
			c.execute(g);
		}
	}

}
