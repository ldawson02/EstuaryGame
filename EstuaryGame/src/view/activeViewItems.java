package view;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import eNums.eDebrisType;
import model.Debris;
import model.Player;

public class activeViewItems {

	ArrayList<DebrisWrapper> debrisWrappers;
	PlayerWrapper player;
	
	/**
	 * construct an active view items
	 */
	public activeViewItems() {
		debrisWrappers = new ArrayList<DebrisWrapper>();
	}

	/**
	 * @return the debrisWrappers
	 */
	public ArrayList<DebrisWrapper> getDebrisWrappers() {
		return debrisWrappers;
	}

	/**
	 * @return the player
	 */
	public PlayerWrapper getPlayer() {
		return player;
	}

	/**
	 * @param player 
	 * the player to set
	 */
	public void setPlayer(Player p) {
		this.player = new PlayerWrapper(p, new Rectangle());
		//this.player.getHitBox().setBounds(p.getPosX(), p.getPosY(), p.getWidth(), p.getHeight());
	}
	
	/**
	 * add the debris 
	 * @param d
	 */
	public void addDebris(Debris d) {
		Ellipse2D.Double dell = new Ellipse2D.Double(d.getPosX(), d.getPosY(), d.getWidth(), d.getHeight());
		debrisWrappers.add(new DebrisWrapper(d, dell));
	}
	/**
	 * remove debris,if it equals the debris item, remove the debris
	 * @param d
	 */
	public void removeDebris(Debris d) {
		DebrisWrapper toRemove = null;
		for (DebrisWrapper dw: debrisWrappers) {
			if (dw.getDebrisItem().equals(d)) {
				toRemove = dw;
			}
		}
		if (toRemove != null) {
			debrisWrappers.remove(toRemove);
		}
	}
	/**
	 * clear debris wrappers
	 */
	public void clearDebris() {
		debrisWrappers.clear();
	}
	
}
