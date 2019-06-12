package model.server;

public interface Solver<Problem,Solution> {
	
	Solution solve(Problem receiveProb);

}
