package demo.example;

import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class StringUniquer implements Runnable {

    public static class Agent {

        private final String name;
        private final String information;

        public Agent(String name, String information) {
            this.name = name;
            this.information = information;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Agent agent = (Agent) o;
            return Objects.equals(information, agent.information);
        }

        @Override
        public int hashCode() {
            return Objects.hash(information);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final Set<Agent> setOfString = new HashSet<>();

    public static void main(String[] args) {
        new Thread(new StringUniquer()).start();
    }

    public void run() {
        Random r = new Random();
        final String[] ss = new String[]{"agent alice", "agent bob"};
        System.out.println("set of our two agents as they are appearing in field, with they current doings:");
        while (true) {
            try {
                Set<Agent> ls = setOfString;
                proceed(ls, new Agent(ss[r.nextInt(ss.length)], String.valueOf(r.nextInt(5))));
                System.out.println(ls);
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void proceed(Set<Agent> ls, Agent agent) {
        ls.add(agent);
    }
}
