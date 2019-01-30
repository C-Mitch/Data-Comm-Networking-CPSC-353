# email
This repository contans a TcpServer.java program and a TcpClient.java program.
These programs are similar to the UDP client and server programs except that the client will send two sentences to the server instead of just one.
This is to demonstrate how you can re-use a TCP socket and send multiple messages over the same socket connection instead of creating a new socket coneection for each message as you do with UDP.

Use the TcpClient.java program as a starting point for the Email.java program that you will create for the email assignment.

In the Email.java pogram, you will collect information from the user to send an email message, then (and only then!) send the message by  connecting to the smtp.chapman.edu server and transmit the message.  Your program shuld display all the SMTP commands that you send to the server and all the responses that you receive from the server.

For full credit, your program Must include appropriate documentation, must be capable of sending messages with multiple lines and must pass checkstyle review/  
