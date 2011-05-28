package webmining.svm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_print_interface;

public class SvmHelper {
	public static svm_parameter createDefaultSvmParameters() {
		svm_parameter param = new svm_parameter();
		// default values
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.degree = 3;
		param.gamma = 0; // 1/num_features
		param.coef0 = 0;
		param.nu = 0.5;
		param.cache_size = 100;
		param.C = 1;
		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 0;
		param.nr_weight = 0;
		param.weight_label = new int[0];
		param.weight = new double[0];

		return param;
	}

	public static double atof(String s) {
		double d = Double.valueOf(s).doubleValue();
		if (Double.isNaN(d) || Double.isInfinite(d)) {
			throw new IllegalArgumentException();
		}
		return (d);
	}

	public static int atoi(String s) {
		return Integer.parseInt(s);
	}

	private static svm_print_interface svmPrintNull = new svm_print_interface() {
		public void print(String s) {
		}
	};

	public static void setDiscardOutputOn() {
		svm.svm_set_print_string_function(svmPrintNull);
	}

	public static double predictAccuracy(BufferedReader input, svm_model model,
			int predict_probability) throws IOException {
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		int svm_type = svm.svm_get_svm_type(model);
		int nr_class = svm.svm_get_nr_class(model);
		double[] prob_estimates = null;

		if (predict_probability == 1) {
			if (svm_type == svm_parameter.EPSILON_SVR
					|| svm_type == svm_parameter.NU_SVR) {
				System.out
						.print("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="
								+ svm.svm_get_svr_probability(model) + "\n");
			} else {
				int[] labels = new int[nr_class];
				svm.svm_get_labels(model, labels);
				prob_estimates = new double[nr_class];
				/*
				 * output.writeBytes("labels"); for (int j = 0; j < nr_class;
				 * j++) output.writeBytes(" " + labels[j]);
				 * output.writeBytes("\n");
				 */
			}
		}
		while (true) {
			String line = input.readLine();
			if (line == null)
				break;

			StringTokenizer st = new StringTokenizer(line, " \t\n\r\f:");

			double target = atof(st.nextToken());
			int m = st.countTokens() / 2;
			svm_node[] x = new svm_node[m];
			for (int j = 0; j < m; j++) {
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}

			double v;
			if (predict_probability == 1
					&& (svm_type == svm_parameter.C_SVC || svm_type == svm_parameter.NU_SVC)) {
				v = svm.svm_predict_probability(model, x, prob_estimates);
				/*
				 * output.writeBytes(v + " "); for (int j = 0; j < nr_class;
				 * j++) output.writeBytes(prob_estimates[j] + " ");
				 * output.writeBytes("\n");
				 */
			} else {
				v = svm.svm_predict(model, x);
				/* output.writeBytes(v + "\n"); */
			}

			if (v == target)
				++correct;
			error += (v - target) * (v - target);
			sumv += v;
			sumy += target;
			sumvv += v * v;
			sumyy += target * target;
			sumvy += v * target;
			++total;
		}
		/*
		 * if (svm_type == svm_parameter.EPSILON_SVR || svm_type ==
		 * svm_parameter.NU_SVR) { System.out.print("Mean squared error = " +
		 * error / total + " (regression)\n");
		 * System.out.print("Squared correlation coefficient = " + ((total *
		 * sumvy - sumv * sumy) * (total * sumvy - sumv sumy)) / ((total * sumvv
		 * - sumv * sumv) * (total * sumyy - sumy sumy)) + " (regression)\n"); }
		 * else
		 */
		System.out.print("Accuracy = " + (double) correct / total * 100 + "% ("
				+ correct + "/" + total + ") (classification)\n");

		return (double) correct / total;
	}
}
