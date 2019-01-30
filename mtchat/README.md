# mtchat
This repo contains programs to implement a multi-threaded TCP chat server and client 

* MtClient.java handles keyborad input from the user.
* ClientListener.java recieves responses from the server and displays them
* MtServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java recieves messages from a client and relays it to the other clients.

To package the client and server in Docker containers:

* Copy the MtClient.class and ClientListener.class files to the client subdirectory
* Copy the MtServer.class and ClientHandler.class files to the server subdirectory

* In the client subdirectory, to create the client Docker image use:
	docker image build -t client . 

* In the server subdirectory, to create the server Docker image use:
	docker image build -t server .

