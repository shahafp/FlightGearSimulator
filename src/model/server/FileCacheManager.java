package model.server;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.util.HashMap;

public class FileCacheManager<Problem, Solution> implements CacheManager<Problem, Solution> {

	HashMap<Problem,Solution> ourHashMap;
	//ObjectOutputStream out;
	
	public FileCacheManager()  {
		this.ourHashMap=new HashMap<>();
//		try{
//		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Solutions.dat"));
//		//setHashMap();
//		out.close();
//		} catch (IOException e) {e.getStackTrace();}
	} 
	
//	@SuppressWarnings("unchecked")
//	public void setHashMap() {
//		try {
//			ObjectInputStream in = new ObjectInputStream(new FileInputStream("Solutions.dat"));
//			this.ourHashMap = (HashMap<Problem,Solution>)in.readObject();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Override
	public boolean DoesSolutionExist(Problem prob) {

		return this.ourHashMap.containsKey(prob);
	}

	@Override
	public Solution retrieveSol(Problem prob) {
		
		return this.ourHashMap.get(prob);
	}

	@Override
	public void SolutionSaver(Solution sol,Problem prob) {
		
				this.ourHashMap.put(prob, sol);
		}
}
