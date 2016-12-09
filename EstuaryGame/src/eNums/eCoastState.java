package eNums;
/**
 * This documents the erosion state of the coast
 *
 */
public enum eCoastState {
	NO_HIT(0), ONE_HIT(1), TWO_HIT(2), ERODED(3);
	
	private int hits;

	eCoastState(int i){
		hits = i;
	}
	
	/**
	 * Get the number of hits
	 * @return hits
	 */
	public int getHits(){
		return hits;
	}
	
	/**
	 * Get the next state. If no hits the state goes to one hit, one hit will goes to the two hit,so on, and return the state in the end
	 * @return state
	 */
	public eCoastState getNextState(){
		eCoastState state;
		switch(this){
		case NO_HIT:
			state = ONE_HIT;
			break;
		case ONE_HIT:
			state = TWO_HIT;
			break;
		case TWO_HIT:
			state = ERODED;
		default:
			state = ERODED;
		}
		return state;
	}
}