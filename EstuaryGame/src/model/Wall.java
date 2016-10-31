package model;

import controller.ActiveItems;
import eNums.eBarrierType;

public class Wall extends Barriers {


	public Wall(int x, int y) {
		super(x, y);
		this.setType(eBarrierType.Wall);
	}

	@Override
	public void build() {
		// TODO Auto-generated method stub
		this.setDecayTime(0);
		this.setHealth(100);
		//ActiveItems.barriers.add(this);
		

	}

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

	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		int adjustedHealth = -(100-this.getHealth())/10;
		//ActiveItems.hBar.update(adjustedHealth);
		
	}

}
