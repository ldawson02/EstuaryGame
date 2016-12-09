package eNums;

/**
 * Documents the difficulty levels of the game, of which there are 5
 * increasingly difficult levels: Very Easy, Easy, Medium, Hard, and Impossible.
 * 
 * @author Rachel Muzzelo
 *
 */
public enum eDifficulty {

	VERYEASY(0), EASY(1), MEDIUM(2), HARD(3), IMPOSSIBLE(4);

	private int difficulty;

	/**
	 * Construct a difficulty
	 * 
	 * @param val
	 */
	eDifficulty(int val) {
		difficulty = val;
	}

	/**
	 * Get the difficulty
	 * 
	 * @return
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * Get the next difficulty level (Very Easy -> Easy -> Medium -> Hard ->
	 * Impossible). Used to move up a difficulty level when the game is too
	 * easy.
	 * 
	 * @return the next difficult level
	 */
	public eDifficulty getNextDifficulty() {
		eDifficulty diff;
		switch (this) {
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
	 * Get the previous difficulty (Impossible -> Hard -> Medium -> East -> Very
	 * Easy). Used to move down a difficulty level when the game is too hard.
	 * 
	 * @return the previous difficulty
	 */
	public eDifficulty getPreviousDifficulty() {
		eDifficulty difficulty;
		switch (this) {
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
