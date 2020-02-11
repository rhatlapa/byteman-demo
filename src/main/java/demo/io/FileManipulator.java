package demo.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManipulator {

	private static final int BUFFER_SIZE = 256;

	public boolean moveFile(File sourceFile, File destFile) {
		boolean fileCopied = false;
		try (FileInputStream input = new FileInputStream(sourceFile);
				FileOutputStream output = new FileOutputStream(destFile)) {
			byte[] data;
			while ((data = input.readNBytes(BUFFER_SIZE)) != null && data.length > 0) {
				output.write(data);
			}
			fileCopied = true;
		} catch (IOException ex) {
			System.err.println("Failed to copy file due ex" + ex);
			fileCopied = false;
		}
		return fileCopied && sourceFile.delete();
	}
}
