package model.server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	
	void handleClient(InputStream in,OutputStream out) throws IOException;

}
