package demo.concurrency;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorkersManager {

	private final ExecutorService executorService;
	private final int numOfWorkers;

	public WorkersManager(int numOfWorkers) {
		this.numOfWorkers = numOfWorkers;
		this.executorService = Executors.newFixedThreadPool(numOfWorkers);
	}

	public int parallelCounting(int repeatIncrementByEachWorker) throws InterruptedException {
		SharedCounter sharedCounter = new SharedCounter();

		List<CounterTask> counterTasks = Stream.generate(() -> new CounterTask(sharedCounter, repeatIncrementByEachWorker))
				.limit(numOfWorkers)
				.collect(Collectors.toList());
		List<Future<Void>> futures = executorService.invokeAll(counterTasks);
		futures.stream().forEach(this::waitForResult);
		return sharedCounter.getCurrentCount();
	}

	private void waitForResult(Future<Void> future) {
		try {
			future.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class CounterTask implements Callable<Void> {

		private final SharedCounter sharedCounter;
		private final int repeat;
		private Random random = new Random();

		public CounterTask(SharedCounter sharedCounter, int repeat) {
			this.sharedCounter = sharedCounter;
			this.repeat = repeat;
		}

		@Override
		public Void call() throws Exception {
			for (int i = 0; i < repeat; i++) {
				Thread.sleep(Math.abs(random.nextInt() % 5));
				sharedCounter.increment();
			}
			return null;
		}
	}
}
