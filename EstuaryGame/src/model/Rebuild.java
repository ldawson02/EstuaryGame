package model;

import java.util.ArrayList;
import java.util.Collections;

import controller.ActiveItems;
import controller.GameController;
import eNums.eBarrierType;
import eNums.eFloaterState;

public class Rebuild extends Powers {

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
