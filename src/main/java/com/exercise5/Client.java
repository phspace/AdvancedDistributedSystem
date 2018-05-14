package com.exercise5;


import java.util.HashMap;

/**
 * object Client:
 * initialized with id, ipAddress, port
 * Alwways alive
 * Wait for token
 * Pass token if not need
 * Wait for user to start election
 * Election (wait till the time is expire)
 * Pass token after election
 */

public class Client implements Runnable {
    private HashMap<String, String> client;

    private volatile String message;
    private volatile String messageFromUser;
    private int nextClientPort;
    private String nextClientID;
    private String nextClientIP;
    private volatile int electionTime;
    private Util util;

    public volatile int state;

    //public final int userPort = 9000;


    public Client(String id, String ipAdress, String port, String userPort) {
        client = new HashMap<>();
        client.put("id", id);
        client.put("ipAddress", ipAdress);
        client.put("port", port);
        client.put("userPort", userPort);
        message = null;
        messageFromUser = null;
        electionTime = 0;
        state = 0;
        util = new Util();
        WaitUser waitUser = new WaitUser(this);
        Thread waitUserInput = new Thread(waitUser);
        waitUserInput.start();
    }

    public void setId(String id) {
        client.replace("id", id);
    }

    public void setIpAdress(String ipAdress) {
        client.replace("ipAddress", ipAdress);
    }

    public void setPort(String port) {
        client.replace("port", port);
    }

    public void checkState() {
        if (state != 0) {
            electionTime = Integer.parseInt(util.getJSON(messageFromUser, "time"));
        }
    }

    public synchronized void checkMessageFromUser() {
        System.out.println("Waiting for user input...");
        messageFromUser = util.TCPReceive(Integer.parseInt(client.get("userPort")));
        state = Integer.parseInt(util.getJSON(messageFromUser, "start"));
    }

    public void setNextClient(String id, String ipAddress, int port) {
        nextClientID = id;
        nextClientIP = ipAddress;
        nextClientPort = port;
    }

    private boolean inCriticalSession(int delay) {
        try {
            System.out.println("Client " + client.get("id") + " is in election for " + delay + " seconds.");
            Thread.sleep(delay);
            sendToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        state = 0;
        electionTime = 0;
        return false;
    }

    public void receiveToken() {
        message = util.TCPReceive(Integer.parseInt(client.get("port")));
        System.out.println("Client " + client.get("id") + " received token " + message);
    }

    public void sendToken() {
        Util util = new Util();
        System.out.println("Client " + client.get("id") + " send token " + message + " to " + nextClientPort);
        util.TCPSend(nextClientIP, nextClientPort, message);
    }

    @Override
    public void run() {
        while (true) {
            if (state == 0) {
                //System.out.println("Passing token around");
                receiveToken();
                sendToken();
            } else {
                receiveToken();
                inCriticalSession(electionTime);
                state = 0;
            }
        }
    }

    class WaitUser implements Runnable {
        private Client client;

        public WaitUser(Client client) {
            this.client = client;
        }

        @Override
        public void run() {
            while (true) {
                client.checkMessageFromUser();
                client.checkState();
            }
        }
    }
}
