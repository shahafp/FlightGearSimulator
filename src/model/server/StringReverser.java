package model.server;

public class StringReverser<Problem, Solution> implements Solver<Problem, Solution> {

	@SuppressWarnings("unchecked")
	@Override
	public Solution solve(Problem receiveProb) {

		String solution = new StringBuilder(receiveProb.toString()).reverse().toString();
		return (Solution) solution;
		
	}

}
