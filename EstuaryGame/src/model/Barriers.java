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
public class Barriers extends Item implements Interactable, HealthChangers{
	private boolean protector = false;
	private int decayTime = 7000;
	//overall state of the barrier
	private int health;
	private eBarrierType type;
	private Timer erosionTimer;
	
	private static int barrierY = 500;
	
	private static int leftEdge = 20;
	private static int rightEdge = 740;
	
	private static int pointsMade = 5;
	
	public static int getPointsMade() {
		return pointsMade;
	}
	
	public static int getLeftEdge() {
		return leftEdge;
	}
	
	public static int getRightEdge() {
		return rightEdge;
	}
	
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
	
	public void build() {
	}
	public void decay(int time) {
	}
	public void crumble() {
	}
	public void erode(){
		this.setType(eBarrierType.EMPTY);
	}

	public int getDecayTime() {
		return decayTime;
	}

	public void setDecayTime(int decayTime) {
		this.decayTime = decayTime;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

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
	
	public static ArrayList<Barriers> setUpLeftCoast() {
		ArrayList<Barriers> spaces = new ArrayList<Barriers>();
		
		for (int i = 0; i < 5; i++) {
			spaces.add(new Barriers(leftEdge+50*i, barrierY));
		}
		
		return spaces;
	}
	
	public static ArrayList<Barriers> setUpRightCoast() {
		ArrayList<Barriers> spaces = new ArrayList<Barriers>();
		
		for (int i = 0; i < 5; i++) {
			spaces.add(new Barriers(rightEdge-50*i, barrierY));
		}
		
		return spaces;
	}
	
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

	public void setProtector(boolean protector) {
		this.protector = protector;
	}

	public Timer geterosionTimer() {
		return erosionTimer;
	}

	public void setbTimer(Timer bTimer) {
		this.erosionTimer = bTimer;
	}

}
