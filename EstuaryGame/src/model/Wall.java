package model;

import controller.ActiveItems;
import eNums.eBarrierType;

/**
 * This class entends a barrier, it will be builded, and it also can be decayed 
 * @author megan
 *
 */
public class Wall extends Barriers {
	private int wallDecayTime = 8000;

	/**
	 * constructor for the wall
	 * @param x
	 * @param y
	 */
	public Wall(int x, int y) {
		super(x, y);
		this.setType(eBarrierType.Wall);
		this.setDecayTime(wallDecayTime);
	}

	@Override
	/**
	 * when we build the wall, the decaytime and health is constant
	 */
	public void build() {
		// TODO Auto-generated method stub
		this.setDecayTime(0);
		this.setHealth(100);
		//ActiveItems.barriers.add(this);
		

	}

	/**
	 * when the wall decays, we decide to let the health of the wall goes down as four times of time goes 
	 */
	@Override
	public void decay(int time) {
		// TODO Auto-generated method stub
		int wallHealth = this.getHealth() - 4*time;
		this.setHealth(wallHealth);
		
	}

	@Override
	public void crumble() {
		// TODO Auto-generated method stub
		//ActiveItems.barriers.remove(this);

	}

	@Override
	public void PlayerCollision(Item item) {
		// TODO Auto-generated method stub
	}

	/**
	 * when the time goes, the health of the health bar updated as well
	 */
	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		int adjustedHealth = -(100-this.getHealth())/10;
		//ActiveItems.hBar.update(adjustedHealth);
		
	}

}
