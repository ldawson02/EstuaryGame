package model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Timer;

import eNums.eBarrierType;
import eNums.eCoastState;

/**
* The Coast class is the Model class for the coast spaces in the game. Each 
* has an erosion rate and a barrier that in the space, as well has a height and width.
* It also contains information about its state.
* 
* @author Ian Heffner
* @version 1.0
* @since 10/25/16
 */

public class Coast extends Item implements Serializable{

	private int coastID;
	/**
	 * The maximum size of the coast
	 */
	private int height = 150;
	private int width = 50;
	/**
	 * The rate at which it erodes
	 */
	private int erosionRate = 10000;
	
	private Barriers barrier;
	private Timer erosionTimer;
	
	private ArrayList<Barriers> barriers;
	private eCoastState state = eCoastState.NO_HIT;
	
	private boolean tempProtected = false;
	
	/**
	 * Constructor for coast spaces, with the default height and width 
	 */
	public Coast() {
		super();
		this.setHeight(height);
		this.setWidth(width);
	}
	
	/**
	 * Constructs a Coast piece with position (x, y) and default height and width
	 * @param x x-coord of coast piece
	 * @param y y-coord of coast piece
	 */
	public Coast(int x, int y) {
		super(x, y);
		this.setHeight(height);
		this.setWidth(width);
	}
	
	/**
	 * Constructs a Coast with position (x, y) and default height and width and 
	 * sets the barrier in the space
	 * @param x x-coord of coast piece
	 * @param y y-coord of coast piece
	 * @param b barrier at that coast piece
	 */
	public Coast(int x, int y, Barriers b){
		super(x, y);
		this.setHeight(height);
		this.setWidth(width);
		this.setBarrier(b);
	}
	
	/**
	 * Getter for the barrier at the coast space
	 * @return barrier
	 */
	public Barriers getBarrier(){
		return barrier;
	}
	
	/**
	 * Sets barrier at the coast space to barrier b
	 */
	public void setBarrier(Barriers b){
		this.barrier = b;
	}
	
	/** 
	 * Getter for the state of the coast space
	 * @return state;
	 */
	public eCoastState getState() {
		return state;
	}

	/**
	 * Sets the state of the coast
	 */
	public void setState(eCoastState state) {
		this.state = state;
	}
	
	/**
	 * Getter for the erosion timer of the coast space
	 * @return erosionTimer
	 */
	public Timer getErosionTimer() {
		return erosionTimer;
	}

	/**
	 * Gets the erosion timer of the coast space
	 */
	public void setErosionTimer(Timer erosionTimer) {
		this.erosionTimer = erosionTimer;
	}

	/**
	 * @return the coastID
	 */
	public int getCoastID() {
		return coastID;
	}

	/**
	 * Sets the coastID 
	 */
	public void setCoastID(int coastID) {
		this.coastID = coastID;
	}
	
	/**
	 * Getter for the erosion rate of the coast space
	 * @return erosionRate
	 */
	public double getErosionRate() {
		return erosionRate;
	}

	/**
	 * Sets the erosion rate of the coast space
	 */
	public void setErosionRate(int erosionRate) {
		this.erosionRate = erosionRate;
	}

	/**
	 * Decreases the size of the coast by one (goes to next hit state), if possible 
	 */
	public void erode() {
		this.setState(this.getState().getNextState());
	}
	
	/**
	 * Changes the erosion rate by the specified amount
	 * @param amount
	 */
	public void changeErosionRate(int amount) {
		this.erosionRate = this.erosionRate + amount;
	}
	
	/**
	 * See if the barrier is protecting the coast (has gabion or wall
	 * of is being rebuilt by powerup)
	 * @return true	if coast is protected
	 * @return false if coast is not protected
	 *
	 */
	public boolean isProtected(){
		if(barrier.getType() != eBarrierType.EMPTY){
			return true;
		}
		else if(tempProtected){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Set up the left coast spaces and adds barriers to coast spaces
	 * @param barriers
	 * @return spaces
	 */
	public static ArrayList<Coast> setUpLeftCoast(ArrayList<Barriers> barriers) {
		ArrayList<Coast> spaces = new ArrayList<Coast>();
		
		int ID = 1;
		for (Barriers b : barriers) {
			Coast c  =new Coast(b.getPosX(), Barriers.getBarrierY()-75);
			c.setBarrier(b);
			c.setCoastID(ID);
			spaces.add(c);
			ID++;
		}
		
		return spaces;
	}
	
	public void tempProtect(boolean t){
		tempProtected = t;
	}
	

	/**
	 * set up the right coast, add the coast to the space
	 * @param barriers
	 * @return spaces
	 */
	public static ArrayList<Coast> setUpRightCoast(ArrayList<Barriers> barriers) {
		ArrayList<Coast> spaces = new ArrayList<Coast>();
		
		int ID = 6;
		for (Barriers b : barriers) {
			Coast c = new Coast(b.getPosX(), Barriers.getBarrierY()-75);
			c.setBarrier(b);
			c.setCoastID(ID);
			spaces.add(c);
			ID++;
		}
		
		return spaces;
	}
	
	
}