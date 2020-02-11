
package demo.example;

import java.util.Date;


public class DatePrinter implements Runnable {

    public static void main(String[] args) {
        new Thread(new DatePrinter()).start();
    }

    public void run() {
        System.out.println("This is printing current time and date every second");
        while (true) {
            try {
                Thread.sleep(1000);
                printDate();
            } catch (Exception ex) {
                System.err.println("Consumed exception, hahaha");
            }
        }
    }

    private void printDate() {
        Date d1 = new Date();
        Date d2 = null;
        printDate(d2);
    }

    private void printDate(Date d) {
        System.out.println(d.toString());
    }

}
