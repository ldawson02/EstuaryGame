package model;

import controller.ActiveItems;
import eNums.eFloaterState;

public class Remove extends Powers {
	
	private int vertex;
	
	/**
	 * construct a remove function
	 * @param x
	 * @param y
	 */
	public Remove(int x, int y){
		super(x,y);
	};
	

	@Override
	public void floating() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * set the state to lifted
	 */
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

/**
 * get and set the vertex
 */
	@Override
	public int getVertex() {
		return vertex;
	}
	
	@Override
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

}
