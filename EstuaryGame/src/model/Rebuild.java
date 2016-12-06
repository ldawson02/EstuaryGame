package model;

import java.util.ArrayList;
import java.util.Collections;

import controller.ActiveItems;
import controller.GameController;
import eNums.eBarrierType;
import eNums.eFloaterState;

/**
 * the rubuild class has a vertex and a floater state
 */
public class Rebuild extends Powers {

	private int vertex;
	private eFloaterState state;
		
	/**
	 * the constructor of rebuild
	 * @param x
	 * @param y
	 */
	public Rebuild(int x, int y){
		super(x,y);
	};
	
	@Override
	public void floating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * set the state to floater lifted
	 */
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
	public void PlayerCollision(Item item) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * get the vertex
	 */
	@Override
	public int getVertex() {
		return vertex;
	}
	/**
	 * set the vertex
	 */
	@Override
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	/**
	 * when the barrier's type equals to empty, empty barrier will add barriers
	 * @param barriers
	 */
	public void power(ArrayList<Barriers> barriers){
		int empty = 0;
		ArrayList<Barriers> bEmpty = new ArrayList<Barriers>();
		for(Barriers b : barriers){
			if(b.getType()==eBarrierType.EMPTY){
				bEmpty.add(b);
				empty++;
			}
		}
		int add = empty/2;
		Collections.shuffle(bEmpty);
		for(int i = 0; i < add; i++){
			bEmpty.get(i).setType(eBarrierType.Gabion);
		}
	}

}