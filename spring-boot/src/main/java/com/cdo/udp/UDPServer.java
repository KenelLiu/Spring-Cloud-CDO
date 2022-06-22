package com.cdo.udp;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This program demonstrates how to implement a UDP server program.
 *
 *
 * @author www.codejava.net
 */
public class UDPServer {
	private DatagramSocket socket;
	private List<String> listQuotes = new ArrayList<String>();
	private Random random;

	public UDPServer(int port) throws SocketException, UnknownHostException{
		socket = new DatagramSocket(port,InetAddress.getByName("127.0.0.1"));
		random = new Random();
	}

	public static void main(String[] args){
		String quoteFile ="E:/Quotes.txt";
		int port =8181;
		try{
			UDPServer server = new UDPServer(port);
			server.loadQuotesFromFile(quoteFile);
			server.service();
		}catch(SocketException ex) {
			System.out.println("Socket error: " + ex.getMessage());
		}catch (IOException ex){
			System.out.println("I/O error: " + ex.getMessage());
		}		
	}

	private void service() throws IOException {
		while (true) {
			System.out.println("UDP Server Receive.....");
			byte[] buffer = new byte[512];
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			socket.receive(request);
			String times = new String(buffer, 0, request.getLength()); 			
			System.out.println("UDP Server Receive times="+times+" Finished!");
			String quote = getRandomQuote();
			buffer = quote.getBytes();
			InetAddress clientAddress = request.getAddress();
			int clientPort = request.getPort();
			System.out.println("UDP Server Send.....");
			DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
			socket.send(response);
			System.out.println("UDP Server Send Finished!");
		}
	}
	
	private void loadQuotesFromFile(String quoteFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(quoteFile));
		String aQuote;

		while ((aQuote = reader.readLine()) != null) {
			listQuotes.add(aQuote);
		}
		reader.close();
	}
	
	private String getRandomQuote() {
		int randomIndex = random.nextInt(listQuotes.size());
		String randomQuote = listQuotes.get(randomIndex);
		return randomQuote;
	}
}