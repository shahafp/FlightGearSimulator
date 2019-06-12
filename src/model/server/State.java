package model.server;

import java.util.Objects;

public class State<T> {
	
	private T state;
	private double cost;
	private State<T> cameFrom;
	
	//Constructor
	public State(T state) {
		this.state=state;
	}
	
	//Getter and Setter
	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public boolean equals(Object s) {
		return state.equals(((State<T>)s).state);
	}


	@Override
	public int hashCode() {

		return Objects.hash(state.toString());
	}

}

