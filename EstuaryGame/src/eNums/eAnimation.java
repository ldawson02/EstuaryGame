package eNums;

/**
 * Mapping out all the different animations and images used.
 * Must be mapped here to be loaded into ImageLibrary.
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
	playerLift("player_lift"),
	error("error");
	
	private String folderName;
	
	eAnimation(String folder) {
		this.folderName = folder;
	}
	
	public String getPath() {
		return folderName;
	}
}
