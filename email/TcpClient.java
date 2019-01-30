/**
*  TCP Client Program
*  Connects to a TCP Server
*  Waits for a Welcome message from the server
*  Receives a line of input from the keyboard and sends it to the server
*  Receives a response from the server and displays it.
*  Receives a second line of input from the keyboard and sends it to the server
*  Receives a second response from the server and displays it.
*  Closes the socket and exits
*
*  @author: Michael Fahy
*  Email:  fahy@chapman.edu
*  Date:  2/4/2018
*  @version: 3.0
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

class TcpClient {

  public static void main(String[] argv) throws Exception {

    Socket clientSocket = null;

    try {
      clientSocket = new Socket("localhost", 6789);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }
    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
    BufferedReader inFromServer =  new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));

    String welcomeMessage = inFromServer.readLine();
    System.out.println("FROM SERVER:" + welcomeMessage);

    System.out.print("First Sentence: ");
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    String sentence = inFromUser.readLine();
    outToServer.println(sentence);
    String modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);

    System.out.print("Second Sentence: ");
    sentence = inFromUser.readLine();
    outToServer.println(sentence);
    modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);

    clientSocket.close();
  }
}
