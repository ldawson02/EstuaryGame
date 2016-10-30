package model;

import controller.ActiveItems;

public class Rebuild extends Powers implements HealthChangers{

	public Rebuild(int x, int y){
		super(x,y);
	};
	
	@Override
	public void floating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catching() {
		// TODO Auto-generated method stub
		for (Coast c : ActiveItems.coasts){
			c.rebuild();	
		}
		
	}

	@Override
	public void appear() {
		// TODO Auto-generated method stub
		ActiveItems.powerups.add(this);
		
	}

	@Override
	public void disappear() {
		// TODO Auto-generated method stub
		ActiveItems.powerups.remove(this);
	}

	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PlayerCollision(Item item) {
		// TODO Auto-generated method stub
		
	}

}
