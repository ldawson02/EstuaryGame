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
	public void PlayerCollision(Item item) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateHealthBar() {
		// TODO Auto-generated method stub
		
	}
	
	
}
