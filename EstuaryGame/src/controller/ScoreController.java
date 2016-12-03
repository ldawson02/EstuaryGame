package controller;

public class ScoreController {
	
	private static int livedStorm = 5000;
	private static int rightBin = 100;
	private static int madeWall = 50;
	private static int madeGabion = 75;
	private static int gotPower = 25;
	
	private static int score = 0;
	
	public static int getScore() {
		return score;
	}
	
	public static void scoreStorm() {
		score = score + livedStorm;
	}
	
	public static void scoreBin() {
		score = score + rightBin;
	}
	
	public static void scoreWall() {
		score = score + madeWall;
	}
	
	public static void scoreGabion () {
		score = score + madeGabion;
	}
	
	public static void scorePower() {
		score = score + gotPower;
	}
	
	
	
	//add more complex scoring w/ difficulty, etc.

}
