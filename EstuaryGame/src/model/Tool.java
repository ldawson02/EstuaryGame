package model;

import java.util.ArrayList;
import java.util.Collections;

public class Tool extends Item{
	private ArrayList<Barriers> barriersToBuild;
	private Barriers currBarrier;
	private int height = 30;
	private int width = 30;
	private final int timePerBarrier = 1000;
	private int timeOnBarrier = 0;
	private boolean doneBuilding = false;
	
	public Tool(ArrayList<Barriers> barriers){		
		this.setHeight(height);
		this.setWidth(width);
		Collections.sort(barriers);
		barriersToBuild = barriers;
		currBarrier = barriers.get(0);
		this.updatePos();
	}
	
	public void addTime(int delta){
		timeOnBarrier+=delta;
		if(timeOnBarrier >= timePerBarrier){
			nextBarrier();
		}
	}
	
	public boolean doneBuilding(){
		return doneBuilding;
	}
	public void nextBarrier(){
		barriersToBuild.remove(0);
		if(!barriersToBuild.isEmpty()){
			currBarrier = barriersToBuild.get(0);
			timeOnBarrier = 0;
			this.updatePos();
		}
		else{
			doneBuilding = true;
		}
	}
	
	public void updatePos(){ 
		this.updatePos(currBarrier.getPosX(), currBarrier.getPosY());
	}
}
