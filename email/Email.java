/**
*  Email Client
*  Aquires appropriate information from the user.
*  Connects to a TCP Server
*  Negotiates with the server and transmits message.  
*  Closes the socket and exits
*
*  @author: Chase Mitchell
*  Email:  mitch213@mail.chapman.edu
*  Date:  10/1/2018
*  @version: 1.3
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

class Email {

  public static void main(String[] argv) throws Exception {

    Socket clientSocket = null;
    
    //Get User Input For Mail Variables (e.g. "From" address, "To" address, Subject, message body)
    System.out.println("Please Enter Sender Address: ");
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    String from = inFromUser.readLine();
    //System.out.println("Sender Address: " + from);
    System.out.println("Please Enter Recipient Address: ");
    String to = inFromUser.readLine();
    //System.out.println("Recipient Address: " + to);
    System.out.println("Please Enter Message Subject: ");
    String subject = inFromUser.readLine();
    //System.out.println("Subject: " + subject);
    System.out.println("Please Enter Message Body: ");
    String body = inFromUser.readLine();
    while (true) { //Loop Appending Message Body Till User Is Finished
      System.out.println("Please Enter 'n' To Finish Message Body; Enter To Continue: ");
      if (inFromUser.readLine().equals("n")) { //Break Appending On 'n' input
        break;
      }
      System.out.println("Continue Message: ");
      String append = "\n" + inFromUser.readLine();
      body = body + append;
      //System.out.println("Body: " + body);
    }

    //Establish Connection To SMTP Server
    try {
      clientSocket = new Socket("smtp.chapman.edu", 25);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }
    
    //Establish Communication Variables
    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
    BufferedReader inFromServer =  new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));

    //Initial Contact Response
    String serverMessage = inFromServer.readLine();
    System.out.println("\nFROM SERVER:" + serverMessage);

    //HELO Message/Response
    String clientSentence = "HELO llb16.chapman.edu";
    System.out.println(clientSentence);
    outToServer.println(clientSentence);
    serverMessage = inFromServer.readLine();
    System.out.println("FROM SERVER: " + serverMessage);
    
    //MAIL FROM Message/Response
    clientSentence = "MAIL FROM: " + from;
    System.out.println(clientSentence);
    outToServer.println(clientSentence);
    serverMessage = inFromServer.readLine();
    System.out.println("FROM SERVER: " + serverMessage);
    
    //RCPT TO Message/Response
    clientSentence = "RCPT TO: " + to;
    System.out.println(clientSentence);
    outToServer.println(clientSentence);
    serverMessage = inFromServer.readLine();
    System.out.println("FROM SERVER: " + serverMessage);
    
    //DATA Message/Response
    clientSentence = "DATA";
    System.out.println(clientSentence);
    outToServer.println(clientSentence);
    serverMessage = inFromServer.readLine();
    System.out.println("FROM SERVER: " + serverMessage);
    
    //Header+Body Message/Response
    clientSentence =
      "From: " + from 
      + "\nTo: " + to 
      + "\nSubject: " + subject
      + "\n\n" + body 
      + "\n.";
    System.out.println(clientSentence);
    outToServer.println(clientSentence);
    serverMessage = inFromServer.readLine();
    System.out.println("FROM SERVER: " + serverMessage);
    
    //QUIT Message/Response
    clientSentence = "QUIT";
    System.out.println(clientSentence);
    outToServer.println(clientSentence);
    serverMessage = inFromServer.readLine();
    System.out.println("FROM SERVER: " + serverMessage);
    
    clientSocket.close();
  }
}
