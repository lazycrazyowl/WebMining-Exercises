package webmining.svm;

import java.util.ArrayList;
import java.util.List;

public class SvmClassificationSetup {
	private final int[] features;
	private String testSetName;

	public SvmClassificationSetup(String testSetName, int... features) {
		super();
		this.features = features;
		this.testSetName = testSetName;
	}

	public List<SvmClassificationResult> run() {

		List<SvmClassificationResult> resultList = new ArrayList<SvmClassificationResult>();

		for (int feature : features) {
			SvmJob job = SvmJob.create(testSetName, feature);
			job.train();
			double accuracy = job.classify();
			resultList.add(new SvmClassificationResult(testSetName, feature,
					accuracy, job.getTrainTime(), job.getTestTime()));
		}

		return resultList;
	}
}
