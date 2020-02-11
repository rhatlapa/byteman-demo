package demo.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(BMUnitRunner.class)
public class FaultInjectionTest {

	@Rule
	public TemporaryFolder temporaryFolder = TemporaryFolder.builder().assureDeletion().build();

	private File inputFile;
	private File destFile;
	private FileManipulator fileManipulator = new FileManipulator();

	@Before
	public void setUp() throws IOException {
		inputFile = temporaryFolder.newFile();
		Files.writeString(inputFile.toPath(), "=".repeat(10000));
		destFile = temporaryFolder.newFile();
	}

	@Test
	public void fileManipulatorMoveFile() throws IOException {
		long originalFileSize = inputFile.length();
		boolean result = fileManipulator.moveFile(inputFile, destFile);

		assertThat(result).isTrue();
		assertThat(destFile).isFile();
		assertThat(destFile.length()).isEqualTo(originalFileSize);
		assertThat(inputFile).doesNotExist();
	}

	@Test
	@BMRule(name = "writes fail before end on IO issue",
			targetClass = "FileOutputStream",
			targetMethod = "write(byte[]Ø)",
			condition = "incrementCounter($0) == 2",
			action = "throw new java.io.IOException( \"Insufficient space!\" )"
	)
	public void fileManipulatorMoveFileFailsInMiddle() throws IOException {
		long originalFileSize = inputFile.length();
		boolean result = fileManipulator.moveFile(inputFile, destFile);

		assertThat(result).isFalse();
		assertThat(inputFile).exists().hasSize(originalFileSize);
	}
}
