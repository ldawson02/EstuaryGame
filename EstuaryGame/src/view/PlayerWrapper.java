package view;

import java.awt.Rectangle;

import model.Player;

public class PlayerWrapper {

	private Player player;
	private Rectangle hitBox;
	
	private PlayerWrapper(){};
	
	public PlayerWrapper(Player p, Rectangle hit) {
		player = p;
		hitBox = hit;
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the hitBox
	 */
	public Rectangle getHitBox() {
		return hitBox;
	}

	/**
	 * @param hitBox the hitBox to set
	 */
	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}

	public void updateCoords() {
		hitBox.x = player.getPosX();
		hitBox.y = player.getPosY();
	}
	
}
