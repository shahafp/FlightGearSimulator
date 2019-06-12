package model.server;

import java.util.*;



public class BFS<T> implements Searcher<T> {

	PriorityQueue<State<T>> openList;
	HashSet<State<T>> close;
	State<T> source;
	State<T> goal;
	
	public BFS()
	{
		openList=new PriorityQueue<State<T>>(new CostComparator());
	}

	public String search(Searchable<T> s)
	{
		this.source=s.getInitialState();
		this.goal=s.getGoalState();
		addToOpenList(source);
		HashSet<State<T>> closedSet = new HashSet<>();
		while(openList.size() > 0)
		{
			State<T> n = popOpenList();
			closedSet.add(n);
			if(n.equals(goal)) {
				return s.backTrace(n);
			}//return the "Cheapest" path

			ArrayList<State<T>> successors = s.getAllPossibleStates(n);
			for(State<T> state : successors)
			{
				if(!(closedSet.contains(state)) && !(openList.contains(state)))
				{
					state.setCameFrom(n);
					addToOpenList(state);
				}
				else if(openList.contains(state)){
						updatedToBetterPath(state);
						//openList.remove(state);
						//openList.add(state);
					}
				}
			}
		return null;
	}
	
	// add and pop Methods
	void addToOpenList(State s) { openList.add(s); }
	State<T> popOpenList() { return (openList.poll()); }
	public void updatedToBetterPath(State<T> state){ //pop and check the current openlist for changes, openlist now contain the best path
		PriorityQueue<State<T>> assist=new PriorityQueue<>(new CostComparator());
		State<T> temp;
		while(!openList.isEmpty()){
			temp=popOpenList();
			if (temp.getState().equals(state.getState())){
				if (temp.getCost() > state.getCost()){
					temp=state;
				}
			}
			assist.add(temp);
		}
		openList=assist;
	}
	
	
	// Our comparator, compare due to cost of states
	public class CostComparator implements Comparator<State<T>>
	{
	    @Override
	    public int compare(State<T> x, State<T> y)
	    {
	        if (x.getCost() > y.getCost())
	        {
	            return 1;
	        }
	        if (x.getCost() < y.getCost())
	        {
	            return -1;
	        }
	        return 0;
	    }
	}

}
