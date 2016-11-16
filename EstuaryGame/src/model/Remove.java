package model;

import controller.ActiveItems;
import eNums.eFloaterState;

public class Remove extends Powers implements HealthChangers {
	
	private int vertex;
	
	public Remove(int x, int y){
		super(x,y);
	};
	

	@Override
	public void floating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catching() {
		this.setState(eFloaterState.LIFTED);
		/*
		// TODO Auto-generated method stub
		for (Debris d : ActiveItems.debris){
			ActiveItems.debris.remove(d);	
		}
		*/
	}

	@Override
	public void appear() {
		// TODO Auto-generated method stub
		//ActiveItems.powerups.add(this);
		
	}

	@Override
	public void disappear() {
		// TODO Auto-generated method stub
		//ActiveItems.powerups.remove(this);
	}


	@Override
	public void PlayerCollision(Item item) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getVertex() {
		return vertex;
	}
	
	@Override
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

}
