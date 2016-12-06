package controller;

public class ScoreController {
	/**
	 * few constant variable listed
	 */
	private static int livedStorm = 5000;
	private static int rightBin = 100;
	private static int madeWall = 50;
	private static int madeGabion = 75;
	private static int gotPower = 25;
	
	private static int score = 0;
	
	/**
	 * score getter
	 * @return score
	 */
	public static int getScore() {
		return score;
	}
	
	/**
	 * setter for score
	 * @param s
	 */
	public static void setScore(int s) {
		score = s;
	}
	
	/**
	 * update the score with the storm
	 */
	public static void scoreStorm() {
		score = score + livedStorm;
	}
	
	/**
	 * if things go to the right bin,score plus
	 */
	public static void scoreBin() {
		score = score + rightBin;
	}
	/**
	 * if wall made, score plus.
	 */
	public static void scoreWall() {
		score = score + madeWall;
	}
	
	/**
	 * if successfully made the gabion,the score plus.
	 */
	public static void scoreGabion () {
		score = score + madeGabion;
	}
	
	/**
	 * if got the power, the score plus.
	 */
	public static void scorePower() {
		score = score + gotPower;
	}
	/**
	 * and score finally willl be added also 
	 * @param currHealth
	 */
	public static void scoreHealth(int currHealth){
		score = score + currHealth;
	}
	
	//add more complex scoring w/ difficulty, etc.

}
