package model;

import controller.ActiveItems;
import eNums.eBarrierType;

/***
 * the Gabion class has a constant gDecaytime
 * 
 *
 */
public class Gabions extends Barriers {
	private int gDecayTime = 12000;

	/**
	 * the constructor of the gabions
	 * @param x
	 * @param y
	 */
	public Gabions(int x, int y){
		super(x,y);
		this.setType(eBarrierType.Gabion);
		this.setDecayTime(gDecayTime);
	}
	
	/**
	 * build the gabions
	 */
	@Override
	public void build(){
		this.setDecayTime(0);
		this.setHealth(100);
		//ActiveItems.barriers.add(this);
	}
	@Override
	/**
	 * the gabions decay, so the health decays as time goes. 
	 */
	public void decay(int time){
		int gabionHealth = (int) (this.getHealth() - 2.5*time);
		this.setHealth(gabionHealth);
	}
	@Override
	public void crumble(){
		//ActiveItems.barriers.remove(this);
		
	}

	@Override
	public void PlayerCollision(Item item) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		int adjustedHealth = -(100-this.getHealth())/10;
		//ActiveItems.hBar.update(adjustedHealth);	
	}
	
	
}
