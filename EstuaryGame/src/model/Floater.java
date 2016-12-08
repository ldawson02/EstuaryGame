package model;

import java.io.Serializable;

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
 * @author megan 
 *
 */
public abstract class Floater extends Item implements Serializable {
	private int speed = 1;
	private int vertex;
	private eFloaterState state = eFloaterState.MOVING;
	
	public static final int defaultWidth = 40;
	public static final int defaultHeight = 40;
	
	/**
	 * construct the floater, default set to MOVING state
	 */
	public Floater(){
		super();
		setWidth(defaultWidth);
		setHeight(defaultHeight);
		this.state = eFloaterState.MOVING;
	}
	
	/**
	 * construct the floater at (x,y), default set to MOVING state
	 * @param x, y
	 */
	public Floater(int x, int y){
		super(x,y);
		setWidth(defaultWidth);
		setHeight(defaultHeight);
		this.state = eFloaterState.MOVING;
	};

	/**
	 * abstract method for subclasses
	 */
	public abstract void catching();
	
	/**
	 * getter for the speed of the floater
	 * @return speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * getter for the state of the floater
	 * @return state;
	 */
	public eFloaterState getState(){
		return state;
	}
	
	/**
	 * set the state of the floater
	 */
	public void setState(eFloaterState e){
		this.state = e;
	}

	/**
	 * getter for the floater's vertex
	 */
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
