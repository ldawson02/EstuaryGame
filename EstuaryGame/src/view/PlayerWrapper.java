package view;

import java.awt.Rectangle;

import model.Player;

public class PlayerWrapper {

	private Player player;
	private Rectangle hitBox;
	
	public PlayerWrapper(Player p, Rectangle hit) {
		player = p;
		hitBox = hit;
	}
	
	public void updateCoords() {
		hitBox.x = player.getPosX();
		hitBox.y = player.getPosY();
	}
	
}
