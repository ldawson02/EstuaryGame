package controller;

public class ScoreController {
	
	private static int livedStorm = 5000;
	private static int rightBin = 100;
	private static int madeWall = 50;
	private static int madeGabion = 75;
	private static int gotPower = 25;
	
	private static int score = 0;
	
	/**
	 * @return score
	 */
	public static int getScore() {
		return score;
	}
	
	/**
	 * sets the score
	 */
	public static void setScore(int s) {
		score = s;
	}
	
	/**
	 * adds points for making it through the storm
	 */
	public static void scoreStorm() {
		score = score + livedStorm;
	}
	
	/**
	 * adds points for throwing debris in the correct bin
	 */
	public static void scoreBin() {
		score = score + rightBin;
	}
	
	/**
	 * adds points for creating a wall
	 */
	public static void scoreWall() {
		score = score + madeWall;
	}
	
	/**
	 * adds points for creating a gabion
	 */
	public static void scoreGabion () {
		score = score + madeGabion;
	}
	
	/**
	 * adds points for getting a power up
	 */
	public static void scorePower() {
		score = score + gotPower;
	}
	
	/**
	 * adds points dependent on the current health of the estuary
	 */
	public static void scoreHealth(int currHealth){
		score = score + currHealth;
	}
	
	/**
	 * adds points for certain increase in difficulty of the game
	 */
	public static void scoreDifficulty(int diff){
		score = score + 2*diff;
	}
}
