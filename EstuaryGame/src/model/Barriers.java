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
public class Barriers extends Item implements Interactable, HealthChangers{
	private boolean protector = false;
	//overall state of the barrier
	private int health;
	private eBarrierType type;
	private Timer erosionTimer;
	
	private static int barrierY = 500;
	
	private static int leftEdge = 20;
	private static int rightEdge = 740;
	
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
	 * the getter for barrier
	 * @return barriery
	 */
	public static int getBarrierY(){
		return barrierY;
	}
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
	
	public void build() {
	}
	public void decay(int time) {
	}
	public void crumble() {
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
	 * the getter and setter for health
	 * @return health
	 */
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
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

	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PlayerCollision(Item item) {
		// TODO Auto-generated method stub
		
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
		
		for (int i = 0; i < 5; i++) {
			spaces.add(new Barriers(rightEdge-50*i, barrierY));
		}
		
		return spaces;
	}
	/**
	 * set up the barrier type, we goes through list of barriers and changes the one with the matching coords to type t, if matches, set the type to t
	 * @param barr
	 * @param t
	 */
	public static void setBarrierType(Barriers barr, eBarrierType t) {
		//goes through list of barriers and changes the one with the matching coords to type t
		for (Barriers b : GameController.getItems().getAllBarriers()) {
			if (barr.getPosX() == b.getPosX()) //"match"
				b.setType(t);
		}
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
	public Timer geterosionTimer() {
		return erosionTimer;
	}

	/** 
	 * set the btimer
	 * @param bTimer
	 */
	public void setbTimer(Timer bTimer) {
		this.erosionTimer = bTimer;
	}

}
