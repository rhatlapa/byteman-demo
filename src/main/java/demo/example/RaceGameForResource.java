package demo.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceGameForResource implements Runnable {

    private static final AtomicInteger shiftRacer = new AtomicInteger(0);

    public static void main(String[] args) {
        new Thread(new RaceGameForResource()).start();
    }

    public void run() {
        try {
            runImpl();
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void runImpl() throws InterruptedException, IOException {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(2, 4, 6, 8, 10));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9));

        Thread thread1 = new Thread(() -> {
            while (true) {
                moveListItem(list1, list2, shiftRacer.addAndGet(1) % 11);
            }
        });
        Thread thread2 = new Thread(() -> {
            while (true) {
                moveListItem(list2, list1, shiftRacer.addAndGet(1) % 11);
            }
        });
        System.out.println("press enter to start game");
        System.in.read();
        thread1.start();
        Thread.sleep(20);
        System.out.println("Race is up!");
        thread2.start();
    }

    private static void moveListItem(List<Integer> from, List<Integer> to, Integer shiftRacer) {
        synchronized (from) {
            synchronized (to) {
                if (from.remove(shiftRacer)) {
                    to.add(shiftRacer);
                }
                from.forEach(d -> System.out.print(d + "; "));
                System.out.println(from.size() + "/" + RaceGameForResource.shiftRacer.toString());
                to.forEach(d -> System.out.print(d + "; "));
                System.out.println(to.size() + "/" + RaceGameForResource.shiftRacer.toString());
            }
        }
    }
}
