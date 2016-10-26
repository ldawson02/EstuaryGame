package model;

import eNums.eDebrisType;

public class Bin extends Item{

	public eDebrisType trashType;
	
	private Bin(){};
	
	public Bin(eDebrisType t){
		this.trashType = t;
	}
	
	public eDebrisType getDebrisType(){
		return trashType;
	}
	
	public void correctBinAction(){};
	
	public void wrongBinAction(){};
}
