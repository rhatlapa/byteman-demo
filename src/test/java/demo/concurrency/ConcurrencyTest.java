package demo.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import org.jboss.byteman.contrib.bmunit.BMScript;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(BMUnitRunner.class)
public class ConcurrencyTest {

	@Test
	@BMScript(value = "concurrency.btm", dir = "target/test-classes")
	public void sharedCounter() throws InterruptedException {
		WorkersManager workersManager = new WorkersManager(2);

		int result = workersManager.parallelCounting(5);

		assertThat(result).isEqualTo(10);
	}
}
