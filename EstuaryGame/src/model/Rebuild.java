package model;

import java.util.ArrayList;

import controller.ActiveItems;
import eNums.eBarrierType;
import eNums.eFloaterState;

public class Rebuild extends Powers implements HealthChangers{

	private int vertex;
	private eFloaterState state;
	
	public Rebuild(int x, int y){
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
		for (Coast c : ActiveItems.coasts){
			c.rebuild();	
		}*/
		
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
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PlayerCollision(Item item) {
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
	
	public void power(ArrayList<Barriers> barriers){
		for(Barriers b : barriers){
			b.setType(eBarrierType.Gabion);
		}
	}

}
