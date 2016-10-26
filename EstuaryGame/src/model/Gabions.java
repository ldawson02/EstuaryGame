package model;

public class Gabions extends Barriers {
	
	public Gabions(int x, int y){
		super(x,y);
	}
	
	@Override
	public void build(){
		this.setDecayTime(0);	
	}
	@Override
	public void decay(int time){
		
	}
	@Override
	public void crumble(){
		
	}

	@Override
	public boolean checkPlayerCollision(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
