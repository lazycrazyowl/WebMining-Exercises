import scala.util._
import scala._
import libsvm._
import java.io.BufferedReader
import java.io.FileReader
import java.util.StringTokenizer

object Main {
  def main(args: Array[String]) {
    val problem = new svm_problem()
    val param = new svm_parameter();
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
    param.weight_label = Array();
    param.weight = Array();

    //svm.svm_set_print_string_function(print_func);

    print("hello");
  }
  
  private void read_problem() throws IOException
	{
		val fp = new BufferedReader(new FileReader(input_file_name));
		val vy = new Vector<Double>();
		val vx = new Vector[Array[svm_node]]();
		val max_index = 0;

		var running = true
		while(true)
		{
			val line = fp.readLine();
			if(line == null) break;

			val st = new StringTokenizer(line," \t\n\r\f:");

			vy.addElement(atof(st.nextToken()));
			var m = st.countTokens()/2;
			Array[svm_node] x = Array()
			for(var j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}
			if(m>0) max_index = Math.max(max_index, x[m-1].index);
			vx.addElement(x);
		}

		val prob = new svm_problem();
		prob.l = vy.size();
		prob.x = new svm_node[prob.l][];
		for(int i=0;i<prob.l;i++)
			prob.x[i] = vx.elementAt(i);
		prob.y = new double[prob.l];
		for(int i=0;i<prob.l;i++)
			prob.y[i] = vy.elementAt(i);

		if(param.gamma == 0 && max_index > 0)
			param.gamma = 1.0/max_index;

		if(param.kernel_type == svm_parameter.PRECOMPUTED)
			for(int i=0;i<prob.l;i++)
			{
				if (prob.x[i][0].index != 0)
				{
					System.err.print("Wrong kernel matrix: first column must be 0:sample_serial_number\n");
					System.exit(1);
				}
				if ((int)prob.x[i][0].value <= 0 || (int)prob.x[i][0].value > max_index)
				{
					System.err.print("Wrong input format: sample_serial_number out of range\n");
					System.exit(1);
				}
			}

		fp.close();
	}
}