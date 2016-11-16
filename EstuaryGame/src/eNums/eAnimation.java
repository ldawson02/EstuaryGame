package eNums;

/**
 * Mapping out all the different animations and images used
 * 
 * @author Ian
 * @version 1.0
 * @since 11/16/2016
 *
 */
public enum eAnimation {
	background("background"),
	clockback("clockback"),
	playerIdle("player_idle"),
	playerLift("player_lift");
	
	private String folderName;
	
	eAnimation(String folder) {
		this.folderName = folder;
	}
	
	public String getPath() {
		return folderName;
	}
}
