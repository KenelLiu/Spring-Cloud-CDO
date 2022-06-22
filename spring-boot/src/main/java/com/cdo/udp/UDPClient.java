package com.cdo.udp;

import java.io.*;
import java.net.*;
 
/**
 * This program demonstrates how to implement a UDP client program.
 *
 *
 * @author www.codejava.net
 */
public class UDPClient {
 
    public static void main(String[] args) throws IOException {
    	UDPClient client=new UDPClient(); 
    	DatagramSocket socket = new DatagramSocket();
    	new Thread(client.new Receiver(socket)).start();
    	new Thread(client.new Send(socket)).start();
    	System.in.read();
//        String hostname ="127.0.0.1";
//        int port =8181;
 
//        try {
//            InetAddress address = InetAddress.getByName(hostname);
//            DatagramSocket socket = new DatagramSocket();
// 
//            while (true) {
//            	System.out.println("UDP Client Send.....");
//                DatagramPacket request = new DatagramPacket(new byte[1], 1, address, port);
//                socket.send(request);
//                System.out.println("UDP Client Send Finished!");
//                byte[] buffer = new byte[512];
//                System.out.println("UDP Client Receive.....");
//                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
//                socket.receive(response);
//                System.out.println("UDP Client Receive Finished!");
//                String quote = new String(buffer, 0, response.getLength());
// 
//                System.out.println(quote);
//                System.out.println();
// 
//                Thread.sleep(5000);
//            }
// 
//        } catch (SocketTimeoutException ex) {
//            System.out.println("Timeout error: " + ex.getMessage());
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            System.out.println("Client error: " + ex.getMessage());
//            ex.printStackTrace();
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
    }
    
    int port=8181;
    String hostname ="127.0.0.1";    
   
    class Send implements Runnable{
    	 
    	DatagramSocket socket =null;
    	
    	public Send(DatagramSocket socket) {
    			this.socket=socket;
    	}
    	
		@Override
		public void run() {
			   try {
		            InetAddress address = InetAddress.getByName(hostname);		          
		            int times=1;
		            while(true){
		            	byte[] buffer=(""+times).getBytes();
		            	System.out.println("UDP Client Send.....times="+times);
		                DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);
		                socket.send(request);
		                System.out.println("UDP Client Send Finished!");
		                Thread.sleep(20000);
		                times++;
		            }		 
		        } catch (SocketTimeoutException ex) {
		            System.out.println("Timeout error: " + ex.getMessage());
		            ex.printStackTrace();
		        } catch (IOException ex) {
		            System.out.println("Client error: " + ex.getMessage());
		            ex.printStackTrace();
		        } catch (InterruptedException ex) {
		            ex.printStackTrace();
		        }			
		}
	}
    
     class Receiver implements Runnable{
    	 DatagramSocket socket =null;
    	 public Receiver(DatagramSocket socket) {
			this.socket=socket;
		}
		@Override
		public void run() {
		try{ 
           
            while(true){            	
                byte[] buffer = new byte[512];
                System.out.println("UDP Client Receive.....");
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);
                System.out.println("UDP Client Receive Finished!");
                String quote = new String(buffer, 0, response.getLength()); 
                System.out.println(quote);
                System.out.println();
                Thread.sleep(5000);                
            }
        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }	
	 }
    	
    }
}