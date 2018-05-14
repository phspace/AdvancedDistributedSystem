package com.exercise5.RingBased;

public class Main {

    public static void main(String[] args) {
        UI ui = new UI();
        Client client = new Client("1", "localhost", "9901", "9001");
        client.setNextClient("2", "localhost", 9902);

        Client client2 = new Client("2", "localhost", "9902", "9002");
        client2.setNextClient("3", "localhost", 9903);

        Client client3 = new Client("3", "localhost", "9903", "9003");
        client3.setNextClient("1", "localhost", 9901);

        Thread c1 = new Thread(client);
        Thread c2 = new Thread(client2);
        Thread c3 = new Thread(client3);

        c1.start();
        c2.start();
        c3.start();

        Util util1 = new Util();
        String message = "{\"start\":\"0\", \"time\":\"1000\"}";
        util1.TCPSend("localhost", 9001, message);
        util1.TCPSend("localhost", 9901, "ThisIsToken!");

    }
}
