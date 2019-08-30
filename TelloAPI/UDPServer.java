package TelloAPI;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

//import java.io.*;
//import java.net.*;

public class UDPServer {
	int port = 8889;
	String addr = "192.168.10.1";
	
	DatagramSocket serverSocket;
	InetAddress IPAddress;
	
	byte[] receiveData = new byte[1024];
//	byte[] sendData = new byte[1024];
	
	/*
	 * Constructor for the UDP server. Creates a UDPServer object with the fields of the port and IP of the drone. 
	 */
	public UDPServer() {
		try {
			serverSocket = new DatagramSocket(port);
			IPAddress = InetAddress.getByName(addr);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Sends the query to the drone to give it instructions
	 */
	public void SendData(String data) {
		byte[] sendData = data.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Takes the reply from the drone, such as "ok" if it can complete the query or an error if it cannot 
	 */
	public String ReceiveData() {
	    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    
        try {
			serverSocket.receive(receivePacket);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        
        String sentence = new String(receivePacket.getData());  
        System.out.println("RECEIVED: " + sentence);
        	
		return sentence.toLowerCase();
	}
}
