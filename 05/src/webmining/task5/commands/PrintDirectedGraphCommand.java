package webmining.task5.commands;

import webmining.helpers.File;
import webmining.task5.graph.DirectedGraph;
import webmining.task5.graph.PrettyPrinter;

public class PrintDirectedGraphCommand implements Command {

	private final String outputFile;

	public PrintDirectedGraphCommand(String outputFile) {
		super();
		this.outputFile = outputFile;
	}

	@Override
	public void execute(DirectedGraph g) {
		PrettyPrinter pp = new PrettyPrinter();

		File.write(outputFile, pp.print(g));
	}

}
