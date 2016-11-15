package model;

import controller.ActiveItems;
import eNums.eBarrierType;

public class Gabions extends Barriers {
	
	public Gabions(int x, int y){
		super(x, y);
		this.setType(eBarrierType.Gabion);
	}
	
	@Override
	public void build(){
		this.setDecayTime(0);
		this.setHealth(100);
		//ActiveItems.barriers.add(this);
	}
	@Override
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
