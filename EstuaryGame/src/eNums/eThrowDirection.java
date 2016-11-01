package eNums;

public enum eThrowDirection {
	LEFT(-1), RIGHT(1);
	
	private int direction;
	
	eThrowDirection(int edir) {
		direction = edir;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public eThrowDirection opposite(){
		if(this.direction==-1){
			return RIGHT;
		}
		else{
			return LEFT;
		}
	}
}
