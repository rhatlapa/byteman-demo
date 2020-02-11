package demo.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import demo.helpers.SimpleServer1;

/**
 *
 * @author jvanek
 */
public class NetworkCalc implements Runnable {

    final int port;

    public NetworkCalc(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        SimpleServer1 server = new SimpleServer1();
        NetworkCalc client = new NetworkCalc(server.getServerSocket());
        new Thread(server).start();
        new Thread(client).start();
    }

    public void run() {
        while (true) {
            try (Socket clientSocket = new Socket("localhost", port)) {
                Thread.sleep(1000);
                BufferedWriter out
                        = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "utf-8"));
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream(), "utf-8"));
                out.write("op\n");
                out.flush();
                String op = readPartOfRemoteOrder(in);
                out.write("dig\n");
                out.flush();
                String d1 = readPartOfRemoteOrder(in);
                out.write("dig\n");
                out.flush();
                String d2 = readPartOfRemoteOrder(in);
                out.close();
                in.close();
                callByRemoteOrder(op, d1, d2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void callByRemoteOrder(String op, String d1, String d2) throws InvocationTargetException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException {
        Method m = this.getClass().getDeclaredMethod(op, int.class, int.class);
        Object result = m.invoke(this, Integer.valueOf(d1), Integer.valueOf(d2));
        Integer r = (Integer) result;
    }

    public int add(int a, int b) {
        int r = a + b;
        System.out.println(a + " + " + b + " = " + r);
        return r;
    }

    public int sub(int a, int b) {
        int r = a + b;
        System.out.println(a + " - " + b + " = " + r);
        return r;
    }

    public int mul(int a, int b) {
        int r = a * b;
        System.out.println(a + " * " + b + " = " + r);
        return r;
    }

    public Integer div(int a, int b) {
        int r = a / b;
        System.out.println(a + " / " + b + " = " + r);
        return r;
    }

    private String readPartOfRemoteOrder(BufferedReader in) throws IOException {
        return in.readLine();
    }

}
