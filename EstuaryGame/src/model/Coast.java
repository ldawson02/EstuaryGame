package model;

import java.util.ArrayList;

import javax.swing.Timer;

import eNums.eBarrierType;
import eNums.eCoastState;

/**
* The Coast class is the Model class for the coast item that is in the game.
* 
* @author Ian Heffner
* @version 1.0
* @since 10/25/16
*/
/**
 * The coast has a current size for the coast, minumum and maxmum size of the coast, and a constant erosion rate, and coast has a constant
 * height and width
 * @author megan
 *
 */
public class Coast extends Item implements HealthChangers{

	

	
	/**
	 * The maximum size of the coast
	 */
	private int height = 30;
	private int width = 50;
	/**
	 * The rate at which it erodes
	 */
	private int erosionRate = 10000;
	
	private Barriers barrier;
	private Timer erosionTimer;
	/**
	 * The Debris items on the coast
	 */
	private ArrayList<Barriers> barriers;
	private eCoastState state = eCoastState.NO_HIT;
	
/**
 * 
 */
	public Coast() {
		super();
		this.setHeight(height);
		this.setWidth(width);
	}
	
	/**
	 * Constructs a Coast with position (x, y)
	 * @param x
	 * @param y
	 */
	public Coast(int x, int y) {
		super(x, y);
		this.setHeight(height);
		this.setWidth(width);
	}
	
	/**
	 * Constructs a Coast with position (x, y) and barrier b
	 * @param x
	 * @param y
	 * @param b
	 */
	public Coast(int x, int y, Barriers b){
		super(x, y);
		this.setHeight(height);
		this.setWidth(width);
		this.setBarrier(b);
	}
	/**
	 * barrier getter and setter
	 * @return barrier
	 */
	public Barriers getBarrier(){
		return barrier;
	}
	
	public void setBarrier(Barriers b){
		this.barrier = b;
	}
	



	/**
	 * @return the erosionRate
	 */
	public double getErosionRate() {
		return erosionRate;
	}

	/**
	 * @param erosionRate the erosionRate to set
	 */
	public void setErosionRate(int erosionRate) {
		this.erosionRate = erosionRate;
	}

	/**
	 * @return the buildUp
	 */
	
	/**
	 * @return the barrierSpaces
	 */
	public ArrayList<Barriers> getBarriers() {
		return barriers;
	}

	/**
	 * @param barrierSpaces the barrierSpaces to set
	 */
	public void setBarrierSpaces(ArrayList<Barriers> barriers) {
		this.barriers = barriers;
	}
	
	/*
	 * for CoastL and CoastR, get all the debris items
	public ArrayList<Debris> getBuildUp() {
		return ArrayList<Debris>();
	}
	*/

	/**
	 * Decreases the size of the coast by one, if possible
	 */
	public void erode() {
		this.setState(this.getState().getNextState());
	}
	
	/**
	 * Increases the size of the coast by one, if possible
	 */
	public void rebuild() {
		
	}
	
	/**
	 * Changes the erosion rate by the specified amount
	 * @param amount
	 */
	public void changeErosionRate(int amount) {
		this.erosionRate = this.erosionRate + amount;
		
	}
	/**
	 * see if the barrier's type matches the ebarrier's type.
	 * @return true or false.
	 */
	public boolean isProtected(){
		if(barrier.getType() != eBarrierType.EMPTY){
			return true;
		}
		else{
			return false;
		}
	}
	
	//public abstract void updateCoords();
	/**
	 *  set up the left coast, add the coast to the space.
	 * @param barriers
	 * @return spaces
	 */
	public static ArrayList<Coast> setUpLeftCoast(ArrayList<Barriers> barriers) {
		ArrayList<Coast> spaces = new ArrayList<Coast>();
		
		for (Barriers b : barriers) {
			Coast c  =new Coast(b.getPosX(), Barriers.getBarrierY()-30);
			c.setBarrier(b);
			spaces.add(c);
		}
		
		return spaces;
	}
	/**
	 * set up the right coast, add the coast to the space
	 * @param barriers
	 * @return spaces
	 */
	public static ArrayList<Coast> setUpRightCoast(ArrayList<Barriers> barriers) {
		ArrayList<Coast> spaces = new ArrayList<Coast>();
		
		for (Barriers b : barriers) {
			Coast c = new Coast(b.getPosX(), Barriers.getBarrierY()-30);
			c.setBarrier(b);
			spaces.add(c);
		}
		
		return spaces;
	}

	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		
	}
/** 
 * get and set the state
 * @return state;
 */
	public eCoastState getState() {
		return state;
	}

	public void setState(eCoastState state) {
		this.state = state;
	}
/**
 * get and set the erosion timer
 * @return erosiontimer;
 */
	public Timer getErosionTimer() {
		return erosionTimer;
	}

	public void setErosionTimer(Timer erosionTimer) {
		this.erosionTimer = erosionTimer;
	}
}
