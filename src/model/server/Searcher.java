package model.server;

public interface Searcher<T> {
	
	String search(Searchable<T> s);

}
