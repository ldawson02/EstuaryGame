package model;

import java.util.ArrayList;

import javax.swing.Timer;

/**
* The Coast class is the Model class for the coast item that is in the game.
* 
* @author Ian Heffner
* @version 1.0
* @since 10/25/16
*/

public class Coast extends Item implements HealthChangers{

	/**
	 * The current size of the coast
	 */
	private int size;
	
	/**
	 * The minimum size of the coast
	 */
	private int minsize;
	
	/**
	 * The maximum size of the coast
	 */
	private int maxsize;
	private int height = 30;
	private int width = 50;
	/**
	 * The rate at which it erodes
	 */
	private double erosionRate;
	
	private Barriers barrier;
	private Timer erosionTimer;
	/**
	 * The Debris items on the coast
	 */
	private ArrayList<Barriers> barriers;
	

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
	
	public Barriers getBarrier(){
		return barrier;
	}
	
	public void setBarrier(Barriers b){
		this.barrier = b;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the minsize
	 */
	public int getMinsize() {
		return minsize;
	}

	/**
	 * @param minsize the minsize to set
	 */
	public void setMinsize(int minsize) {
		this.minsize = minsize;
	}

	/**
	 * @return the maxsize
	 */
	public int getMaxsize() {
		return maxsize;
	}

	/**
	 * @param maxsize the maxsize to set
	 */
	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
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
	public void setErosionRate(double erosionRate) {
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
	public void changeErosionRate(double amount) {
		
	}
	
	//public abstract void updateCoords();
	
	public static ArrayList<Coast> setUpLeftCoast(ArrayList<Barriers> barriers) {
		ArrayList<Coast> spaces = new ArrayList<Coast>();
		
		for (Barriers b : barriers) {
			Coast c  =new Coast(b.getPosX(), Barriers.getBarrierY()-50);
			c.setBarrier(b);
			spaces.add(c);
		}
		
		return spaces;
	}
	
	public static ArrayList<Coast> setUpRightCoast(ArrayList<Barriers> barriers) {
		ArrayList<Coast> spaces = new ArrayList<Coast>();
		
		for (Barriers b : barriers) {
			Coast c = new Coast(b.getPosX(), Barriers.getBarrierY()-50);
			c.setBarrier(b);
			spaces.add(c);
		}
		
		return spaces;
	}

	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		
	}
}
