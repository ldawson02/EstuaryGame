package eNums;

/**
 * Documents the different animations and images used, and holds their file
 * path. Must be mapped here to be loaded into ImageLibrary.
 * 
 * @author Ian Heffner
 * @version 1.0
 * @since 11/16/2016
 *
 */

public enum eAnimation {
	background("background"),
	clockback("clockback"),
	playerIdle("player_idle"),
	playerLift("player_lift"),
	trashBin("trashbin"),
	recycleBin("recyclingbin"),
	recyclingFloat("recyclingFloat"),
	recyclingCoast("recyclingCoast"),
	recyclingLifted("recyclinglift"),
	trashFloat("trashfloat"),
	trashCoast("trashcoast"),
	trashLifted("trashlift"),
	remove("remove"),
	removeLift("removelift"),
	rebuild("rebuild"),
	fullGabion("fullgab"),
	halfGabion("halfgab"),
	fullWall("fullwall"),
	halfWall("halfwall"),
	helperWalk("helperwalk"),
	helperPickUp("helperpickup"),
	helperLift("helperlift"),
	helperWalkRight("helperwalkright"),
	storm("storm"),
	gameOver("gameOver"),
	clockArrow("clockarrow"),
	healthArrow("healtharrow"),
	spotlight("spotlight"),
	hammer("hammer"),
	mouseArrow("mousearrow"),
	error("error");
	
	private String folderName;
	
	eAnimation(String folder) {
		this.folderName = folder;
	}
	
	/**
	 * get the path
	 * @return foldername;
	 */
	public String getPath() {
		return folderName;
	}
}
