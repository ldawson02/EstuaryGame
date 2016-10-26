package model;

public class Wall extends Barriers {


	public Wall(int x, int y) {
		super(x, y);
	}

	@Override
	public void build() {
		// TODO Auto-generated method stub
		this.setDecayTime(0);

	}

	@Override
	public void decay(int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void crumble() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkPlayerCollision(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
