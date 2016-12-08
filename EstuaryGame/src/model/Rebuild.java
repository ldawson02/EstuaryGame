package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import controller.ActiveItems;
import controller.GameController;
import eNums.eBarrierType;
import eNums.eFloaterState;

public class Rebuild extends Powers implements Serializable{

	private int vertex;
	private eFloaterState state;
	private ArrayList<Barriers> toRebuild = new ArrayList<Barriers>();
		
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

	public ArrayList<Barriers> getBarriersToRebuild(){
		return toRebuild;
	}
	

	public static ArrayList<Barriers> getRebuildBarriers(ActiveItems items){
		ArrayList<Barriers> toRebuild = new ArrayList<Barriers>();
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
			toRebuild.add(bEmpty.get(i));
			//bEmpty.get(i).setType(eBarrierType.Gabion);
		}
		
		return toRebuild;
	}
	
	public static void power(Barriers b){
		b.setType(eBarrierType.Gabion);
	}

}