package com.exercise5.examples;

import java.net.*;
import java.io.*;

public class UDPServer{
  public static void main( String args[]){
    System.out.println("The Server is running");
    try{
      DatagramSocket aSocket = new DatagramSocket (6789);
      byte[] buffer = new byte[1000];
      while(true){
        DatagramPacket request = new DatagramPacket (buffer, buffer.length);
        aSocket.receive (request);
        System.out.println(" Request: " + new String(request.getData(), 0, request.getLength()));
                                                                          
        DatagramPacket reply = new DatagramPacket (request.getData(),
                request.getLength(), request.getAddress(), request.getPort());
        aSocket.send (reply);
      }
    }catch (SocketException e){ System.out.println(" Socket: " + e.getMessage());
    }catch (IOException e) {System.out.println(" IO: " + e.getMessage());}
  } // main
} //class UDPServer