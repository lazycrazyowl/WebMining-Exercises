package webmining.svm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_problem;

public class SvmJob {

	private String trainData;
	private String testData;

	private PerformanceMonitor trainTime;
	private PerformanceMonitor testTime;
	private svm_model model;

	public SvmJob(String trainData, String testData) {
		this.trainData = trainData;
		this.testData = testData;
	}

	public static SvmJob create(String testCase, int tokens) {
		String testName = testCase + "." + tokens;

		System.out.println("Running test " + testCase + " with " + tokens
				+ " features");

		return new SvmJob("/home/frank/Desktop/train/" + testName + ".txt",
				"/home/frank/Desktop/test/" + testName + ".txt");
	}

	public void train() {
		trainTime = PerformanceMonitor.watch("train");

		try {
			svm_problem problem = SparseFileReader.readProblem(this.trainData);

			model = svm.svm_train(problem,
					SvmHelper.createDefaultSvmParameters());

		} catch (IOException e) {
			e.printStackTrace();
		}

		trainTime.stop();
		System.out.println(trainTime);
	}

	public void test() {
		testTime = PerformanceMonitor.watch("test");

		try {
			BufferedReader br = new BufferedReader(new FileReader(testData));

			double accuracy = SvmHelper.predictAccuracy(br, model, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		testTime.stop();
		System.out.println(testTime);
	}
}
