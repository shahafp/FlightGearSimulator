package model.server;

public interface Server {
	
	void start(ClientHandler ch); //opens the server and waits to clients
	void stop();//closes the server
	void runServer(ClientHandler ch) throws Exception;

}
