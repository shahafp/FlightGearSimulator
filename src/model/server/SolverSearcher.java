package model.server;

public class SolverSearcher<Problem,Solution> implements Solver<Problem,Solution> {

    Searcher<Problem> searcher;

    SolverSearcher(Searcher s){
        this.searcher=s;
    }

    @Override
    public Solution solve(Problem receiveProb) {

        return (Solution)searcher.search((Searchable<Problem>) receiveProb);
    }
}
