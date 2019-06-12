package model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyTestClientHandler<Problem,Solution> implements ClientHandler {
	
	public SolverSearcher<Problem,Solution> solver;
	public CacheManager<Problem,Solution> cm;
	

	public MyTestClientHandler(SolverSearcher<Problem,Solution> solver, CacheManager<Problem,Solution> cm) {
		this.solver = solver;
		this.cm = cm;
	}


	@Override
	public void handleClient(InputStream in, OutputStream out) throws IOException {
		
		BufferedReader userInput = new BufferedReader(new InputStreamReader(in));//get the input from the client
		PrintWriter outToScreen = new PrintWriter(out);//send an output to the client
		
		Problem receiveProb = (Problem) userInput.readLine();
		
		while(!receiveProb.equals("end")) {
			
			if (this.cm.DoesSolutionExist(receiveProb)) {
				outToScreen.println(cm.retrieveSol(receiveProb));
				outToScreen.flush();
			}
			else {
				this.cm.SolutionSaver(this.solver.solve(receiveProb),receiveProb);
				outToScreen.println(cm.retrieveSol(receiveProb));
				outToScreen.flush();
			}
			outToScreen.flush();
			receiveProb = (Problem) userInput.readLine();
		} 
		
		userInput.close();
		outToScreen.close();
		
	}

}
