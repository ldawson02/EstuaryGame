package model;

import java.util.ArrayList;
import java.util.Collections;

import controller.ActiveItems;
import eNums.eBarrierType;
import eNums.eFloaterState;

public class Remove extends Powers {
	
	private int vertex;
	
	public Remove(int x, int y){
		super(x,y);
	};
	
	public Remove(){
		super();
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
	public void power(ActiveItems items){
		//ArrayList<Debris> debris = items.getAllDebris();
		items.removeAllRestingDebris();
		/**int empty = 0;
		ArrayList<Debris> dEmpty = new ArrayList<Debris>();
		for(Item i : debris){
			Debris d = (Debris)i; 
			if(d.getState()==eFloaterState.RESTING){
				dEmpty.add(d);
				empty++;
			}
		}
		int add =empty;
		Collections.shuffle(dEmpty);
		for(int i = 0; i < add; i++){
			dEmpty.remove(i);
		}**/
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
