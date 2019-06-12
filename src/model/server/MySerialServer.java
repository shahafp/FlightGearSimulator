package model.server;

import java.net.ServerSocket;
import java.net.Socket;

public class MySerialServer implements Server {

	private int port;
	private volatile boolean stop;
	
	public MySerialServer(int port) {
		this.port=port;
		this.stop=false;
	}
	
	@Override
	public void start(ClientHandler ch) {
		new Thread(()->{
			try {
				runServer(ch);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	@Override
	public void runServer(ClientHandler ch) throws Exception {
		ServerSocket server = new ServerSocket(this.port);
		server.setSoTimeout(1000);
		while(!stop) {
			try {
				Socket aClient=server.accept();//connection with a client
					try {
						
						ch.handleClient(aClient.getInputStream(),aClient.getOutputStream());//send to handle client methods to handle every type of client
						
						aClient.getInputStream().close();
						aClient.getOutputStream().close();
						aClient.close();
						
					} catch (Exception e) {e.getMessage();}
						
					} catch (Exception e) {e.getMessage();}
			}
		server.close();
	}

	@Override
	public void stop() {
		this.stop = true;

	}

}
