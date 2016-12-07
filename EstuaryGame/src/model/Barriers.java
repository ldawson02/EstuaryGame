package model;

import java.util.ArrayList;

import javax.swing.Timer;

import controller.GameController;
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
public class Barriers extends Item{
	private boolean protector = false;
	//overall state of the barrier
	private eBarrierType type;
	private Timer erosionTimer;
	private static int barrierY = 500;
	private static int leftEdge = 20;
	private static int rightEdge = 740;
	
	/**
	 * Constructor for Barrier
	 */
	public Barriers(int x, int y){
		super(x,y);
		setWidth(40);
		setHeight(20);
		type = eBarrierType.EMPTY;
	};
	
	public Barriers(int x, int y, eBarrierType t){
		super(x,y);
		setWidth(40);
		setHeight(20);
		type = t;
	}
	
	
	/**the getter for leftedge
	 * 
	 * @return leftedge;
	 */
	public static int getLeftEdge() {
		return leftEdge;
	}
	
	/**
	 * the getter for right edge
	 * @return right edge
	 */
	public static int getRightEdge() {
		return rightEdge;
	}
	/**
	 * the getter the y value for all barriers
	 * @return barriery
	 */
	public static int getBarrierY(){
		return barrierY;
	}


	/**
	 * the barrier will erode and then the barrier type will be empty.
	 */
	public void erode(){
		this.setType(eBarrierType.EMPTY);
	}

	/**
	 * the decaytime getter and setter
	 * @return decaytime;
	 */
	public int getDecayTime() {
		return this.getType().getDecay();
	}


	/**
	 * the getter and setter for type
	 * @return type
	 */
	public eBarrierType getType() {
		return type;
	}

	public void setType(eBarrierType type) {
		this.type = type;
	}



	/**
	 * we set up the left coast, when the loop less than 5, the space will add barrier in left
	 * @return spaces.
	 */
	public static ArrayList<Barriers> setUpLeftCoast() {
		ArrayList<Barriers> spaces = new ArrayList<Barriers>();
		
		for (int i = 0; i < 5; i++) {
			spaces.add(new Barriers(leftEdge+50*i, barrierY));
		}
		
		return spaces;
	}
	/**
	 * we set up the right coast, when the loop less than 5, the space will add barrier in right
	 * @return spaces.
	 */
	public static ArrayList<Barriers> setUpRightCoast() {
		ArrayList<Barriers> spaces = new ArrayList<Barriers>();
		
		int leftestRightCoast = rightEdge - (50 * 4);
		for (int i = 0; i < 5; i++) {
			spaces.add(new Barriers(leftestRightCoast + 50*i, barrierY));
		}
		
		return spaces;
	}

	public boolean isProtector() {
		return protector;
	}

	/**
	 * set the protector
	 * @param protector
	 */
	public void setProtector(boolean protector) {
		this.protector = protector;
	}
/**
 * get the erosion timer
 * @return erosiontimer;
 */
	public Timer getErosionTimer() {
		return erosionTimer;
	}

	/** 
	 * set the erosion timer
	 * @param bTimer
	 */
	public void setErosionTimer(Timer bTimer) {
		this.erosionTimer = bTimer;
	}

}