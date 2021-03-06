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
	
	public static final int defaultWidth = 120;
	public static final int defaultHeight = 50;
	/**
	 * construct a tool
	 * @param barriers
	 */
	public Tool(ArrayList<Barriers> barriers) {		
		this.setHeight(defaultHeight);
		this.setWidth(defaultWidth);
		Collections.sort(barriers);
		barriersToBuild = barriers;
		currBarrier = barriers.get(0);
		this.updatePos();
	}
	
	public void stopErosion(ArrayList<Coast> coastline){
		for(Coast c : coastline){
			if(barriersToBuild.contains(c.getBarrier())){
				c.tempProtect(true);
			}
		}
	}
	
	public void addTime(int delta){
		timeOnBarrier+=delta;
		if(timeOnBarrier >= timePerBarrier){
			Rebuild.power(currBarrier);
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
	/**
	 * update the barrier's postion
	 */
	public void updatePos(){ 
		this.updatePos(currBarrier.getPosX(), currBarrier.getPosY());
	}
}
