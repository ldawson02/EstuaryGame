package model;

import java.io.Serializable;
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
	
	/**
	 * Constructor for coast spaces, with the default height and width 
	 */
	public Coast() {
		super();
		this.setHeight(height);
		this.setWidth(width);
	}
	
	/**
	 * Constructs a Coast with position (x, y) and default height and width
	 * @param x, y
	 */
	public Coast(int x, int y) {
		super(x, y);
		this.setHeight(height);
		this.setWidth(width);
	}
	
	/**
	 * Constructs a Coast with position (x, y) and default height and width
	 * also sets the barrier in the space to Barriers b
	 * @param x, y, barrier
	 */
	public Coast(int x, int y, Barriers b){
		super(x, y);
		this.setHeight(height);
		this.setWidth(width);
		this.setBarrier(b);
	}
	
	/**
	 * getter for the barrier at the coast space
	 * @return barrier
	 */
	public Barriers getBarrier(){
		return barrier;
	}
	
	/**
	 * sets barrier at the coast space to Barriers b
	 */
	public void setBarrier(Barriers b){
		this.barrier = b;
	}
	
	/** 
	 * getter for the state of the coast space
	 * @return state;
	 */
	public eCoastState getState() {
		return state;
	}

	/**
	 * set coast state to eCoastState
	 */
	public void setState(eCoastState state) {
		this.state = state;
	}
	
	/**
	 * getter for the erosion timer of the coast space
	 * @return erosionTimer;
	 */
	public Timer getErosionTimer() {
		return erosionTimer;
	}

	/**
	 * sets the erosion timer of the coast to Timer
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
	 * sets the coast ID to int coastID
	 */
	public void setCoastID(int coastID) {
		this.coastID = coastID;
	}
	
	/**
	 * getter for the erosion rate of the coast space
	 * @return erosionRate
	 */
	public double getErosionRate() {
		return erosionRate;
	}

	/**
	 * sets the erosion rate of the coast space to int erosionRate
	 */
	public void setErosionRate(int erosionRate) {
		this.erosionRate = erosionRate;
	}

	/**
	 * Decreases the size of the coast by one, if possible (goes to next hit state)
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
	
	/**
	 * set up the left coast, add the coast to the space.
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