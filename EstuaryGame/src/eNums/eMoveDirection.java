package eNums;

/**
 * we have four directions in the game, left right, up, and down.
 *
 */
public enum eMoveDirection {
	LEFT(-1), RIGHT(1), UP(1), DOWN(-1);
	
	private int direction;
	
	eMoveDirection(int e){
		direction = e;
	}
	
	
}
