package com.exercise5;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Util {
    public void TCPSend(String IP, int port, String message) {
        try {
            Socket s = new Socket(IP, port);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            DataInputStream in = new DataInputStream(s.getInputStream());
            out.writeUTF(message);
            String data = in.readUTF();
            //System.out.println("Received: " + data);
            s.close();
        } catch (UnknownHostException e) {
            System.out.println(" Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println(" EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println(" IO:" + e.getMessage() + " (Connection to " + port + ")");
        }
    }

    public String TCPReceive(int port) {
            ServerSocket listenSocket = null;
            DataInputStream in;
            DataOutputStream out;
            String message = "";
            try {

                listenSocket = new ServerSocket(port);
                Socket clientSocket = listenSocket.accept();
                out = new DataOutputStream(clientSocket.getOutputStream());
                in = new DataInputStream(clientSocket.getInputStream());
                message = in.readUTF();
                out.writeUTF(message);
                //System.out.println("Sent data back: " + message);
                clientSocket.close();
                listenSocket.close();
            } catch (EOFException e) {
                System.out.println(" EOF:" + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return message;
    }

    public String getJSON(String message, String object) {
        String result = "";
        JSONObject obj = new JSONObject(message);
        result = obj.getString(object);
        return result;
    }
}
