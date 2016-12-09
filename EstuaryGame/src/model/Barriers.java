package model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Timer;

import controller.GameController;
import eNums.eBarrierState;
import eNums.eBarrierType;

/**
 * the barrier class represents the barriers on the coast with a type, state, erosion countdown, 
 * x coord, y coord, height, width
 */
public class Barriers extends Item implements Serializable, Comparable<Barriers>{

	private eBarrierType type;
	private eBarrierState state = eBarrierState.NO_HIT;
	private Timer erosionTimer;
	private static int barrierY = 525;
	private static int leftEdge = 0;
	private static int rightEdge = 750;
	private static int spawnY = 390;
	public static final int defaultHeight = 37;
	public static final int defaultWidth = 45;
	private static int offset = 50;

	
	/**
	 * Constructor for Barrier, with a default of EMPTY type
	 * @param x x-coord of barrier
	 * @param y y-coord of barrier
	 */
	public Barriers(int x, int y){
		super(x,y);
		setWidth(defaultWidth);
		setHeight(defaultHeight);
		type = eBarrierType.EMPTY;
	};
	
	
	/**
	 * Constructor for Barrier, with a chosen type
	 * @param x x-coord of barrier
	 * @param y y-coord of barrier
	 * @param t barrier type
	 */
	public Barriers(int x, int y, eBarrierType t){
		super(x,y);
		setWidth(defaultWidth);
		setHeight(defaultHeight);
		type = t;
	}
	
	
	/**
	 * Getter for the x coordinate of left-most edge of all the barriers
	 * @return leftEdge
	 */
	public static int getLeftEdge() {
		return leftEdge;
	}
	
	/**
	 * Getter for the x coordinate of right-most edge of all barriers
	 * @return rightEdge
	 */
	public static int getRightEdge() {
		return rightEdge;
	}
	
	/**
	 * The getter for the upper y coordinate spawn point
	 * @return spawnY
	 */
	public static int getSpawnY() {
		return spawnY;
	}
	
	/**
	 * The getter for the y value for all barriers
	 * @return barrierY
	 */
	public static int getBarrierY(){
		return barrierY;
	}

	/**
	 * Getter for the decayTime of the barrier, dependent on its type
	 * @return decayTime;
	 */
	public int getDecayTime() {
		return this.getType().getDecay();
	}
	
	/**
	 * Getter for the barrier's type
	 * @return type
	 */
	public eBarrierType getType() {
		return type;
	}

	/**
	 * Setter for the barrier's type and also sets the state to no hits
	 */
	public void setType(eBarrierType type) {
		this.type = type;
		this.setState(eBarrierState.NO_HIT);
	}

	/**
	 * Getter for the barrier's state, either no hits or one hit
	 * @return state
	 */
	public eBarrierState getState(){
		return state;
	}
	
	/**
	 * Setter for the barrier's state
	 */
	public void setState(eBarrierState s){
		state = s;
	}
	
	/**
	 * Gets the erosion timer of this barrier
	 * @return erosionTimer;
	 */
	public Timer getErosionTimer() {
		return erosionTimer;
	}

	/** 
	 * Set the erosion timer of this barrier
	 */
	public void setErosionTimer(Timer t) {
		this.erosionTimer = t;
	}
	
	/**
	 * Erodes the barrier by settin to its type to EMPTY and its state to no hits
	 */
	public void erode(){
		this.setType(eBarrierType.EMPTY);
		this.setState(eBarrierState.NO_HIT);
	}
	
	/**
	 * Erodes half the barrier, and sets its state to only one hit
	 */
	public void erodeHalf(){
		this.setState(eBarrierState.ONE_HIT);
	}

	/**
	 * Set up the left coast (5 leftmost barriers)
	 * @return spaces arraylist of default spaces for barriers(set to empty)
	 */
	public static ArrayList<Barriers> setUpLeftCoast() {
		ArrayList<Barriers> spaces = new ArrayList<Barriers>();
		
		for (int i = 0; i < 5; i++) {
			spaces.add(new Barriers(leftEdge+offset*i, barrierY));
		}
		
		return spaces;
	}
	
	/**
	 * Set up the right coast (5 rightmost barriers)
	 * @return spaces arraylist of default spaces for barriers (set to empty)
	 */
	public static ArrayList<Barriers> setUpRightCoast() {
		ArrayList<Barriers> spaces = new ArrayList<Barriers>();
		
		int leftestRightCoast = rightEdge - (offset * 4);
		for (int i = 0; i < 5; i++) {
			spaces.add(new Barriers(leftestRightCoast + offset*i, barrierY));
		}
		
		return spaces;
	}
	
	/**
	 * Custom comparator to sort Barriers from left to right
	 */
	@Override
	public int compareTo(Barriers b) {
		return this.getPosX() - b.getPosX();
	}
}