package webmining.svm;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

	public static void main1(String[] args) throws IOException {

		SvmHelper.setDiscardOutputOn();

		PerformanceMonitor prog = PerformanceMonitor.watch("program");

		SvmJob job = SvmJob.create("token", 1600);
		job.train();
		job.classify();

		prog.stop();
		System.out.println(prog.toString());

	}

	static int[] data = { 5, 10, 20, 50, 100, 200, 300, 400, 450, 500, 550,
			600, 650, 700, 800, 850, 900, 1000, 1100, 1200, 1300, 1400, 1500,
			1600, 1800, 2000, 2400, 3200, 6400 };

	public static void main(String[] args) throws IOException {

		SvmHelper.setDiscardOutputOn();

		PerformanceMonitor prog = PerformanceMonitor.watch("program");

		SvmClassificationSetup setup = new SvmClassificationSetup("token", data);

		writeResults(setup.run());

		SvmClassificationSetup setup2 = new SvmClassificationSetup("stem", data);
		writeResults(setup2.run());

		SvmClassificationSetup setup3 = new SvmClassificationSetup(
				"tokenWOStopword", data);
		writeResults(setup3.run());

		prog.stop();
		System.out.println(prog.toString());

	}

	public static void writeResults(List<SvmClassificationResult> resultList)
			throws IOException {

		FileWriter fileWriter = new FileWriter(resultList.get(0).getName());

		for (SvmClassificationResult r : resultList) {
			fileWriter.write(r.toString() + "\n");
		}
		fileWriter.close();
	}

}
