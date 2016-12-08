package model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Timer;

import controller.GameController;
import eNums.eBarrierState;
import eNums.eBarrierType;

/**
 * Barrier interface. Each barrier has build, decay, and crumble methods.
 * 
 * @author: Rachel Muzzelo
 * @version 1.0
 * @since 10/25/16
 */
/**barrier class has a constant decaytime, and has health which type of barrier erosion time, and constant barriery,leftedge and rightedge
 * 
 * 
 *
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
	 * @param x, y
	 */
	public Barriers(int x, int y){
		super(x,y);
		setWidth(defaultWidth);
		setHeight(defaultHeight);
		type = eBarrierType.EMPTY;
	};
	
	
	/**
	 * Constructor for Barrier, with a chosen type
	 * @param x, y
	 */
	public Barriers(int x, int y, eBarrierType t){
		super(x,y);
		setWidth(defaultWidth);
		setHeight(defaultHeight);
		type = t;
	}
	
	
	/**
	 * getter for the x coord of left edge of the right-most barrier
	 * @return leftEdge;
	 */
	public static int getLeftEdge() {
		return leftEdge;
	}
	
	/**
	 * the getter for right-most edge of all barriers
	 * @return rightEdge
	 */
	public static int getRightEdge() {
		return rightEdge;
	}
	
	/**
	 * the getter for the upper y coord spawn point
	 * @return spawnY
	 */
	public static int getSpawnY() {
		return spawnY;
	}
	
	/**
	 * the getter for the y value for all bottom barriers
	 * @return barrierY
	 */
	public static int getBarrierY(){
		return barrierY;
	}

	/**
	 * getter for the decayTime of the barrier, dependent on its type
	 * @return decayTime;
	 */
	public int getDecayTime() {
		return this.getType().getDecay();
	}
	
	/**
	 * getter for the barrier's type
	 * @return type
	 */
	public eBarrierType getType() {
		return type;
	}

	/**
	 * setter for the barrier's type, also sets the state to no hits
	 */
	public void setType(eBarrierType type) {
		this.type = type;
		this.setState(eBarrierState.NO_HIT);
	}

	/**
	 * getter for the barrier's state, either no hits or one hit
	 * @return state
	 */
	public eBarrierState getState(){
		return state;
	}
	
	/**
	 * setter for the barrier's state
	 */
	public void setState(eBarrierState s){
		state = s;
	}
	
	/**
	 * get the erosion timer of this barrier
	 * @return erosionTimer;
	 */
	public Timer getErosionTimer() {
		return erosionTimer;
	}

	/** 
	 * set the erosion timer of this barrier
	 */
	public void setErosionTimer(Timer t) {
		this.erosionTimer = t;
	}
	
	/**
	 * erodes the barrier and sets to EMPTY type, also set to no hits
	 */
	public void erode(){
		this.setType(eBarrierType.EMPTY);
		this.setState(eBarrierState.NO_HIT);
	}
	
	/**
	 * erodes the barrier, and sets to only one hit
	 */
	public void erodeHalf(){
		this.setState(eBarrierState.ONE_HIT);
	}

	/**
	 * set up the left coast (5 leftmost barriers)
	 * @return array list of set up default barriers (set to empty)
	 */
	public static ArrayList<Barriers> setUpLeftCoast() {
		ArrayList<Barriers> spaces = new ArrayList<Barriers>();
		
		for (int i = 0; i < 5; i++) {
			spaces.add(new Barriers(leftEdge+offset*i, barrierY));
		}
		
		return spaces;
	}
	
	/**
	 * set up the right coast (5 rightmost barriers)
	 * @return array list of set up default barriers (set to empty)
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