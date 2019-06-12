package model.server;

import java.awt.*;
import java.util.ArrayList;

public class MatrixSearchable implements Searchable<Point> {

	public double[][] matrix;
	public Point sourceLocation;
	public Point goalLocation;
	public int row,columns;

	
	// constructor
	public MatrixSearchable(double[][] matrix,Point source,Point goal,int rows,int col){
		this.matrix = matrix;
		this.sourceLocation=source;
		this.goalLocation=goal;
		this.row=rows;
		this.columns=col;
	}
	

	// setters
	public void setSourceLocation(Point sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public void setGoalLocation(Point goalLocation) {
		this.goalLocation = goalLocation;
	}


	@Override
	public State<Point> getInitialState() {
        return getPointState(sourceLocation);
    }


	@Override
	public State<Point> getGoalState() {
        return getPointState(goalLocation);
    }

    private State<Point> getPointState(Point goalLocation) {
        State<Point> goal = new State<>(goalLocation);
        goal.setCost(matrix[(int) goalLocation.getX()][(int) goalLocation.getY()]);
        goal.setCameFrom(null);
        return goal;
    }

    @Override
	public ArrayList<State<Point>> getAllPossibleStates(State<Point> s) {
		int x=(int)s.getState().getX();
		int y=(int)s.getState().getY();
		ArrayList<State<Point>> successors=new ArrayList<>();

		for (int i=x-1;i<=x+1;i++){
			for (int j=y-1;j<=y+1;j++){
				if (isBound(i,j) && ((i == x+1 && j == y) || (i==x && j==y+1) || (i==x && j==y-1) || (i==x-1 && j==y) )){
					State<Point> state= new State<>(new Point(i,j));
					state.setCost(s.getCost()+matrix[i][j]);
					state.setCameFrom(s);
					successors.add(state);
				}
			}
		}
		return successors;
	}

	@Override
	public String backTrace(State<Point> goal) {

		StringBuilder trace=new StringBuilder();
		while (goal.getState()!=this.sourceLocation){

			if (goal.getState().getX() > goal.getCameFrom().getState().getX()){
				trace.insert(0,"Down,");
			}
			else if(goal.getState().getY()> (goal.getCameFrom().getState().getY())){
				trace.insert(0,"Right,");
			}
			else if(goal.getState().getY() < (goal.getCameFrom().getState().getY())){
				trace.insert(0,"Left,");
			}
			else if(goal.getState().getX() < goal.getCameFrom().getState().getX()){
				trace.insert(0,"Up,");
			}
			goal=goal.getCameFrom();
		}
		trace.deleteCharAt(trace.length()-1);
		return trace.toString();
	}

	public boolean isBound(int x,int y){
		if (x>=0 && x<row && y>=0 && y<columns){
			return true;
		}
		return false;
	}


}
