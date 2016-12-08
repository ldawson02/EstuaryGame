package eNums;

public enum eDifficulty {
	
	VERYEASY(0), EASY(1), MEDIUM(2), HARD(3), IMPOSSIBLE(4);
	
	private int difficulty;
	
	eDifficulty(int val){
		difficulty = val;
	}
	
	public int getDifficulty(){
		return difficulty;
	}
	
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
