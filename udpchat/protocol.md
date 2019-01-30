**Client**

1.  Send "Hello name" message to the server where "name" is an identifier for the client (red or blue).
2.  Wait for a response (100 or 200) from the Server
3.  If the client receives a 100 message from the server as its first message, the client knows it was the first to connect and then
    *  It waits for a second response from the server
    *  When the client receives a second response from the server, it knows tha the second client has connected.
        * the user of the first client is then prompted to type a message which is sent to the second client
        * The first client waits for a response message from the second client
        * When the first client receives the response message, it displays it to the user and prompts the user to type another message.
4.  If the client receives a 200 message from the server as its first message, the client knows it was the second to connect and then
    *  It waits for a message from the first client.
    * When the second client receives the response message, it displays it to the user and prompts the user to type a message.
5.  When either client receives a message from the other client, it sends one response message and waits for a reply
6.  When a client receives a "Goodbye" message, it closes the socket and exits.


**Server**

1.  Wait for "Hello name" message from first client.
    *  When a message is received, record the name, IP address and port number of the first client
    *  Send 100 message to the first client
2.  Wait for "Hello name" message from second client.
    *  When a message is received from the second client, record the name, IP address and port number of the second client
    *  Send 200 message to both clients.
3.  Wait for a message from a client.
    *  if the message is not "Goodbye" send it to the other client
    *  If the message is "Goodbye" send it to both clients.
    *  Close the socket and exit.
