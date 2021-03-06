package demo.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class DivisionRow implements Runnable {

    private static int N; // tune this for load size
    private static final Random r = new Random();

    public static void main(String[] args) {
        new Thread(new DivisionRow()).start();
    }

    public void run() {
        //if emptied, all allocated here will be dispossed, so the maps will get freed too.
        List<Result> randomDivisibles = new ArrayList<>();
        System.out.println("Incrementally growing list of magical numbers: ");
        while (true) {
            N = r.nextInt(100) * 10;
            try {
                int loopN = N >> 2;
                for (int i = 11; i <= loopN; i++) {
                    addObscureNumbers(i, randomDivisibles);

                }
                System.out.println("random keys divisible by [11-" + loopN + "]");
                System.out.print(randomDivisibles.size() + ": ");
                randomDivisibles.forEach(t -> System.out.print(t.getKey() + "; "));
                System.out.println();
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
                randomDivisibles = new ArrayList<>();
            }
        }
    }

    private static byte[] getLoad(int v) {
        //we need a lot of ram here; Each entry will eat a 1 kilos now
        byte[] b = new byte[1 * 1024];
        return b;
    }

    private static void addObscureNumbers(int i, List<Result> randomDivisibles) {
        List<Result> results = obscureQueryNumbersDivisibleBy(i);
        randomDivisibles.add(results.get(r.nextInt(results.size())));
    }

    private static List<Result> obscureQueryNumbersDivisibleBy(int divisibleBy) {
        List<Result> queriedEntries = new ArrayList<>();
        for (Map.Entry<Integer, byte[]> e : query().entrySet()) {
            if (e.getKey() % divisibleBy == 0) {
                queriedEntries.add(new Result(e));
            }
        }
        return queriedEntries;
    }

    private static Map<Integer, byte[]> query() {
        final Map<Integer, byte[]> queryResult = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            int v = r.nextInt();
            queryResult.put(v, getLoad(v));
        }
        return queryResult;
    }

    private final static class Result {

        Map.Entry<Integer, byte[]> entry;

        Result(Map.Entry<Integer, byte[]> entry) {
            this.entry = entry;
        }

        Integer getKey() {
            return entry.getKey();
        }

        byte[] getValue() {
            return entry.getValue();
        }
    }
}
