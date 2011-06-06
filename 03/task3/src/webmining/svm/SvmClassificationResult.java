package webmining.svm;

public class SvmClassificationResult {
	private final String name;
	private final double accuracy;
	private final int features;
	private final PerformanceMonitor trainTime;
	private final PerformanceMonitor testTime;

	public SvmClassificationResult(String name, int features, double accuracy,
			PerformanceMonitor trainTime, PerformanceMonitor testTime) {
		super();
		this.name = name;
		this.accuracy = accuracy;
		this.features = features;
		this.trainTime = trainTime;
		this.testTime = testTime;
	}

	public String getName() {
		return name;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public int getFeatures() {
		return features;
	}

	public long getRuntimeInMs() {
		return testTime.getRuntime() + trainTime.getRuntime();
	}

	private String t(String a, String b) {
		return a + "\t" + b;
	}

	@Override
	public String toString() {
		return t(t(Integer.toString(features), Double.toString(accuracy)),
				Long.toString(getRuntimeInMs()));
	}
}
