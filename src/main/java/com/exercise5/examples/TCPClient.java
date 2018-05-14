package com.exercise5.examples;

import java.net.*;
import java.io.*;

public class TCPClient {
  public static void main (String args[]) {
  // args[0]: Message
  // args[1]: Server
  
    try{
	  int serverPort = 7896;
	  Socket s = new Socket (args[1], serverPort);
	  DataOutputStream out = new DataOutputStream ( s.getOutputStream());
	  DataInputStream in = new DataInputStream ( s.getInputStream());
	  out.writeUTF (args[0]); 
	  String data = in.readUTF ();
	  System. out.println("Received: "+ data) ;
	  s.close();
    }catch (UnknownHostException e){
	  System.out.println(" Sock:"+ e.getMessage());
    }catch (EOFException e){ System.out.println(" EOF:"+ e.getMessage());
    }catch (IOException e){ System.out.println(" IO:"+ e.getMessage());}
  }// main
}// class