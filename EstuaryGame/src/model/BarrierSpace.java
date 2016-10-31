package model;

import java.util.ArrayList;

public class BarrierSpace extends Item {

	private Barriers barrier;
	
	public void setBarrier(Barriers b){
		barrier = b;
	}
	
	public Barriers getBarrier(){
		return barrier;
	}
	
	private BarrierSpace(int x, int y) {
		super(x, y);
		setWidth(40);
		setHeight(40);
	}
	
	public static ArrayList<BarrierSpace> setUpLeftCoast() {
		ArrayList<BarrierSpace> spaces = new ArrayList<BarrierSpace>();
		//These are hard-coded!!!!!
		BarrierSpace space1 = new BarrierSpace(20, 450);
		BarrierSpace space2 = new BarrierSpace(70, 450);
		BarrierSpace space3 = new BarrierSpace(120, 450);
		BarrierSpace space4 = new BarrierSpace(170, 450);
		BarrierSpace space5 = new BarrierSpace(220, 450);
		spaces.add(space1);
		spaces.add(space2);
		spaces.add(space3);
		spaces.add(space4);
		spaces.add(space5);
		
		return spaces;
	}
	
	public static ArrayList<BarrierSpace> setUpRightCoast() {
		ArrayList<BarrierSpace> spaces = new ArrayList<BarrierSpace>();
		//These are hard-coded!!!!!
		BarrierSpace space1 = new BarrierSpace(740, 450);
		BarrierSpace space2 = new BarrierSpace(690, 450);
		BarrierSpace space3 = new BarrierSpace(640, 450);
		BarrierSpace space4 = new BarrierSpace(590, 450);
		BarrierSpace space5 = new BarrierSpace(540, 450);
		spaces.add(space1);
		spaces.add(space2);
		spaces.add(space3);
		spaces.add(space4);
		spaces.add(space5);
		
		return spaces;
	}
	
}
