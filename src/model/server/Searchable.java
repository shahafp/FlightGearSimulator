package model.server;

import java.util.ArrayList;

public interface Searchable<T> {
	
	State<T> getInitialState(); // returns where state is on the field
	State<T> getGoalState(); // returns if the state we are at is the goal
	ArrayList<State<T>> getAllPossibleStates(State<T> s); // Array that will hold all possible moves
	String backTrace(State<T> goal);

}
