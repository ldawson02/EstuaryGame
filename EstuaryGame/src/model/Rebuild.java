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
	public Rebuild(){
		super();
	}

	@Override
	public void catching() {
		this.setState(eFloaterState.LIFTED);
		
	}


	@Override
	public int getVertex() {
		return vertex;
	}
	@Override
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	
	@Override
	public void power(ActiveItems items){
		ArrayList<Barriers> barriers = items.getAllBarriers();
		int empty = 0;
		ArrayList<Barriers> bEmpty = new ArrayList<Barriers>();
		for(Item i : barriers){
			Barriers b = (Barriers)i; 
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
