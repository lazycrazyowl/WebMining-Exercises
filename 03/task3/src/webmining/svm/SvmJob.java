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

		return new SvmJob(
				"/home/frank/Projects/TextClassification/TextClassification/bin/Debug/out/train/"
						+ testName + ".txt",
				"/home/frank/Projects/TextClassification/TextClassification/bin/Debug/out/test/"
						+ testName + ".txt");
	}

	public void train() {
		trainTime = PerformanceMonitor.watch("train");

		try {
			svm_problem problem = SparseFileReader.readProblem(this.trainData);

			model = svm.svm_train(problem,
					SvmHelper.createDefaultSvmParameters());

			System.out.println(model);

		} catch (IOException e) {
			e.printStackTrace();
		}

		trainTime.stop();
		System.out.println(trainTime);
	}

	public double classify() {
		double accuracy = 0;

		testTime = PerformanceMonitor.watch("classification");
		try {
			BufferedReader br = new BufferedReader(new FileReader(trainData));
			// System.out.println(testData);
			accuracy = SvmHelper.predictAccuracy(br, model, 1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		testTime.stop();
		System.out.println(testTime);

		return accuracy;
	}

	public PerformanceMonitor getTrainTime() {
		return trainTime;
	}

	public PerformanceMonitor getTestTime() {
		return testTime;
	}
}
