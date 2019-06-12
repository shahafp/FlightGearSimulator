package model.server;

//import java.io.IOException;

public interface CacheManager<Problem,Solution> {
	
    boolean DoesSolutionExist(Problem prob);
	Solution retrieveSol(Problem prob);
	void SolutionSaver(Solution sol,Problem prob);
}
