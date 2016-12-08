package eNums;

public enum eDifficulty {
	/**
	 * this class is for the difficulty thing, there are five states that shows different difficulty level
	 * @megan
	 */
	VERYEASY(0), EASY(1), MEDIUM(2), HARD(3), IMPOSSIBLE(4);
	
	private int difficulty;
	
	/**
	 * construct a difficulty
	 * @param val
	 */
	eDifficulty(int val){
		difficulty = val;
	}
	/**
	 * get the difficulty
	 * @return
	 */
	public int getDifficulty(){
		return difficulty;
	}
	/**
	 * get the next difficulty, very easy then easy then medium then hard then impossible.
	 * @return
	 */
	public eDifficulty getNextDifficulty(){
		eDifficulty diff;
		switch(this){
		case VERYEASY:
			diff = EASY;
			break;
		case EASY:
			diff = MEDIUM;
			break;
		case MEDIUM:
			diff = HARD;
			break;
		case HARD:
			diff = IMPOSSIBLE;
			break;
		case IMPOSSIBLE:
			diff = IMPOSSIBLE;
			break;
		default:
			diff = MEDIUM;
		}
		return diff;
	}
	/**
	 * get the previous difficulty, go back to the last level
	 * @return difficulty
	 */
	public eDifficulty getPreviousDifficulty(){
		eDifficulty difficulty;
		switch(this){
		case VERYEASY:
			difficulty = VERYEASY;
			break;
		case EASY:
			difficulty = VERYEASY;
			break;
		case MEDIUM:
			difficulty = EASY;
			break;
		case HARD:
			difficulty = MEDIUM;
			break;
		case IMPOSSIBLE:
			difficulty = HARD;
			break;
		default:
			difficulty = EASY;
		}
		return difficulty;
	}


}
