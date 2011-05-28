package webmining.svm;

import java.io.IOException;

import libsvm.svm_parameter;
import libsvm.svm_problem;

public class Main {

	public static void main(String[] args) throws IOException {

		SvmHelper.setDiscardOutputOn();

		PerformanceMonitor prog = PerformanceMonitor.watch("program");

		SvmJob job = SvmJob.create("token", 1600);
		job.train();
		job.test();

		prog.stop();
		System.out.println(prog.toString());

	}

	private static void fixSvmParameter(svm_parameter param, svm_problem prob,
			int max_index) {
		if (param.gamma == 0 && max_index > 0)
			param.gamma = 1.0 / max_index;

		if (param.kernel_type == svm_parameter.PRECOMPUTED)
			for (int i = 0; i < prob.l; i++) {
				if (prob.x[i][0].index != 0) {
					System.err
							.print("Wrong kernel matrix: first column must be 0:sample_serial_number\n");
					System.exit(1);
				}
				if ((int) prob.x[i][0].value <= 0
						|| (int) prob.x[i][0].value > max_index) {
					System.err
							.print("Wrong input format: sample_serial_number out of range\n");
					System.exit(1);
				}
			}
	}

}
