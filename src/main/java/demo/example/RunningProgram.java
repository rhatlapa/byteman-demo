package demo.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunningProgram {
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String next = in.readLine();
			while (next != null && !next.trim().equalsIgnoreCase("end")) {
				final String arg = next;
				Thread thread = new Thread(arg) {
					public void run() {
						System.out.println(arg);
					}
				};
				thread.start();
				try {
					thread.join();
				} catch (Exception e) {
				}
				next = in.readLine();
			}
		} catch (Exception e) {
		}
	}
}
