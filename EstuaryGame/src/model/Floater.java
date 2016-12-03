package model;

import eNums.eFloaterState;

/**
 * 
 * 
 * @author Rachel Muzzelo
 * @version 1.0
 * @since 10/26/16
 */
/**
 * the floater class has a constant speed, and a state, vertex.
 * @author Pu
 *
 */
public abstract class Floater extends Item {
	private int speed = 3;
	private int vertex;
	private eFloaterState state = eFloaterState.MOVING;
	
	public Floater(){
		super();
		setWidth(40);
		setHeight(40);
		this.state = eFloaterState.MOVING;
	}
	/**
	 * construct the floater int(x,y)
	 * @param x
	 * @param y
	 */
	public Floater(int x, int y){
		super(x,y);
		setWidth(40);
		setHeight(40);
		this.state = eFloaterState.MOVING;
	};
	public abstract void floating();
	public abstract void catching();
	
	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}
	/**
	 * get the state
	 * @return state;
	 */
	
	public eFloaterState getState(){
		return state;
	}
	/**
	 * set the state
	 * @param e
	 */
	public void setState(eFloaterState e){
		//TODO: make sure you cant set illegal instances
		/*
		if (this instanceof Powers && e.getState()>2){
			throw IllegalStateException;
		}
		*/
		this.state = e;
	}
	
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getVertex() {
		return vertex;
	}
	/**
	 * @param vertex the vertex to set
	 */
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
}
