
/**
*  TCP Server Program
*  Listens on a TCP port
*  Receives a line of input from a TCP client
*  Returns an upper case version of the line to the client
*
*  @author: Michael Fahy
*  Email:  fahy@chapman.edu
*  Date:  2/4/2018
*  @  version: 3.0
*/
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

class TcpServer {

  public static void main(String[] argv) throws Exception {
    //String welcomeMessage = "Welcome\n";
    String clientSentence;
    String capitalizedSentence;

    ServerSocket welcomeSocket = null;

    try {
      welcomeSocket = new ServerSocket(6789);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }

    while (true) {
      Socket connectionSocket = welcomeSocket.accept();
      BufferedReader inFromClient = new BufferedReader(
          new InputStreamReader(connectionSocket.getInputStream()));
      DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream());

      clientSentence = inFromClient.readLine();
      capitalizedSentence = clientSentence.toUpperCase() + '\n';
      outToClient.writeBytes(capitalizedSentence);
      connectionSocket.close();
    }
  }
}
