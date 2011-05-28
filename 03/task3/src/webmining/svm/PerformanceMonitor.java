package webmining.svm;

public class PerformanceMonitor {
	private long startTime;
	private long stopTime = -1;

	private String name;

	public static PerformanceMonitor watch(String Name) {
		PerformanceMonitor monitor = new PerformanceMonitor();
		monitor.name = Name;
		monitor.startTime = System.currentTimeMillis();

		return monitor;
	}

	public void stop() {
		stopTime = System.currentTimeMillis();

		long tookMs = stopTime - startTime;
	}

	@Override
	public String toString() {
		if (isFinished()) {
			return "Running " + name + " since "
					+ formatTime(System.currentTimeMillis() - startTime);
		}
		return "Finished " + name + " after "
				+ formatTime(stopTime - startTime);
	}

	public long getRuntime() {
		return stopTime - startTime;
	}

	private boolean isFinished() {
		return stopTime == -1;
	}

	private String formatTime(long timeValue) {
		return Long.toString(timeValue) + "ms";
	}
}
