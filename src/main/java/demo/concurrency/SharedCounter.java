package demo.concurrency;

public class SharedCounter {

	private volatile int count = 0;

	public synchronized void increment() {
		int currentCount = count;
		int newCount = ++currentCount;
		defineNewValue(newCount);
	}

	public int getCurrentCount() {
		return count;
	}

	private void defineNewValue(int newCount) {
		System.out.println(String.format("[%s] Count is being updated to %d", Thread.currentThread().getName(), newCount));
		this.count = newCount;
	}
}
