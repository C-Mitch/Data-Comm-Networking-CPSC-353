/**
*  UDP Chat Server Program
*  Listens on a UDP port
*  Receives a line of input from a UDP client A && UDP client B
*  Passes input from one client to another
*
*  @author: Chase Mitchell
*  email: Mitch213@mail.chapman.edu
*  date: 9/19/2018
*  @version: 1.4.1
*/

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class ChatServer { 

  public static void main(String[] args) throws Exception { 
	  
	DatagramSocket serverSocket = null;
	int port = 0;
	int port1 = 0;
	int port2 = 0;
	String name1 = "";
	String name2 = "";
	InetAddress ipAddress = null;
	InetAddress ipAddress1 = null;
	InetAddress ipAddress2 = null;
	String message = "";
	String response = "";
	DatagramPacket receivePacket;
	DatagramPacket sendPacket;
	int state = 0;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	byte[] messageBytes = new byte[1024];
	

    try
	{
      serverSocket = new DatagramSocket(9876);
    }
	catch (Exception e)
	{
      System.out.println("Failed to open UDP socket");
      System.exit(0);
    }
	
	while (state < 3)
	{
		receiveData = new byte[1024];
		sendData = new byte[1024];
		
		switch(state)
		{
			case 0: // state 0: Wait for first connection
			
				System.out.println("IN CASE 0");
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				message = new String(receivePacket.getData());

				name1 = message.substring(6,10);
				System.out.println("Name: " + name1);
				
				ipAddress1 = receivePacket.getAddress();
				System.out.println("ipAddress: " + ipAddress1);
				
				port1 = receivePacket.getPort();
				System.out.println("port number: " + port1);
				
				message = "100";
				
				sendData = message.getBytes();

				sendPacket =
					new DatagramPacket(sendData, sendData.length, ipAddress1, port1);

				serverSocket.send(sendPacket);
				
				state = 1;
				break;
			
			case 1: //State 1: Wait for second client to connect
				
				System.out.println("IN CASE 1");
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				message = new String(receivePacket.getData());
				
				name2 = message.substring(6,10);
				System.out.println("Name: " + name2);
				
				ipAddress2 = receivePacket.getAddress();
				System.out.println("ipAddress: " + ipAddress2);
				
				port2 = receivePacket.getPort();
				System.out.println("port number: " + port2);
				
				message = "200";
				
				sendData = message.getBytes();

				sendPacket =
					new DatagramPacket(sendData, sendData.length, ipAddress1, port1);
				serverSocket.send(sendPacket);

				sendPacket =
					new DatagramPacket(sendData, sendData.length, ipAddress2, port2);
				serverSocket.send(sendPacket);				
				
				state = 2;
				break;
				
			case 2: //State 2: Chat Mode
				
				System.out.println("IN CASE 2");
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				message = new String(receivePacket.getData());
				
				if (message.length()>=7 && message.substring(0,7).equals("Goodbye"))
				{
					state = 3;
					break;	
				}
				
				ipAddress = receivePacket.getAddress();
				port = receivePacket.getPort();
				
				if ((port == port1) && (ipAddress.equals(ipAddress1)))
				{
					ipAddress = ipAddress2;
					port = port2;
				}
				else
				{
					ipAddress = ipAddress1;
					port = port1;
				}
				
				sendData = message.getBytes();
				
				sendPacket =
					new DatagramPacket(sendData, sendData.length, ipAddress, port);
				serverSocket.send(sendPacket);
				
				break;
				
		}
	}

	message = "Goodbye";
	
	sendData = message.getBytes();

	sendPacket =
		new DatagramPacket(sendData, sendData.length, ipAddress1, port1);
	serverSocket.send(sendPacket);

	sendPacket =
		new DatagramPacket(sendData, sendData.length, ipAddress2, port2);
	serverSocket.send(sendPacket);	
	
	serverSocket.disconnect();
	
	System.out.println("DONE");
	
	
  }
}