package eNums;

public enum eHelperState {
	WALKING(0), PICKING_UP(1), HOLDING(2), WALKING_OFF(3), VOID(4);
	
	eHelperState(int i){}
	
	public int getTimeLimit(){
		switch(this){
		case PICKING_UP:
			return 1000;
		case HOLDING:
			return 1500;
		case WALKING:
		default:
			return 100000;
		}
	}
	
	public eHelperState nextState(){
		switch(this){
		case WALKING:
			return PICKING_UP;
		case PICKING_UP:
			return HOLDING;
		case HOLDING:
			return WALKING_OFF;
		case WALKING_OFF:
		default:
			return VOID;
		}
	}
}
