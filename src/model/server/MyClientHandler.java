package model.server;

import java.awt.*;
import java.io.*;

public class MyClientHandler<Problem,Solution> implements ClientHandler{

	public Solver<Searchable<Point>,String> solver;
	public CacheManager<Searchable<Point>,String> cm;
	public Searchable ms;

	public MyClientHandler(){
		this.solver=new SolverSearcher<>(new BFS<>());
		this.cm=new FileCacheManager<>();
	}
	
	@Override
		public void handleClient(InputStream in, OutputStream out) throws IOException {
			
			BufferedReader userInput = new BufferedReader(new InputStreamReader(in));
			PrintWriter outToScreen=new PrintWriter(out);
			int i=0;
			String outputToScreen = "SP";
			String recieveProb = userInput.readLine();
			String[] str= recieveProb.split(",");
			int column=str.length;
			int row=column;
			double[][] matrix=new double[row][column];

			while(!recieveProb.equals("end")) {
				for(int j=0; j<str.length; j++)
				{
					matrix[i][j]=Double.parseDouble(str[j]);
				}
				i++;
				recieveProb = userInput.readLine();
				str=recieveProb.split(",");
			}


			String[] receiveSource = userInput.readLine().split(",");
			Point sourcePoint= new Point(Integer.parseInt(receiveSource[0]),Integer.parseInt(receiveSource[1]));

			String[] receiveGoal = userInput.readLine().split(",");
			Point goalPoint = new Point(Integer.parseInt(receiveGoal[0]),Integer.parseInt(receiveGoal[1]));

			ms = new MatrixSearchable(matrix,sourcePoint,goalPoint,row,column);

			if (this.cm.DoesSolutionExist(ms)) {
				outToScreen.println(cm.retrieveSol(ms));
				outToScreen.flush();
			}
			else {
				outputToScreen=this.solver.solve(ms);
				this.cm.SolutionSaver(outputToScreen,ms);
				outToScreen.println(outputToScreen);
				outToScreen.flush();
			}


			userInput.close();
			outToScreen.close();
		}

	}

