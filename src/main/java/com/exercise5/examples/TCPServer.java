package com.exercise5.examples;

import java.net.*;
import java.io.*;

public class TCPServer {
    public static void main(String args[]) {
        try {
            System.out.println("The Server is running");
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            Socket clientSocket = listenSocket.accept();
            System.out.println("New Connection");
            Connection c = new Connection(clientSocket);

        } catch (IOException e) {
            System.out.println(" Listen :" + e.getMessage());
        }
    }// main
}//class


class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            this.start();
        } catch (IOException e) {
            System.out.println(" Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            String data = in.readUTF();
            out.writeUTF(data);

            System.out.println("Sent data: " + data);
            clientSocket.close();
        } catch (EOFException e) {
            System.out.println(" EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println(" IO:" + e.getMessage());
        }
    }
}