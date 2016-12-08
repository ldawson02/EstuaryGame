package view;

import java.awt.geom.Ellipse2D;

import model.Debris;

public class DebrisWrapper {

	private Ellipse2D.Double hitBox;
	private Debris debrisItem;
	
	private DebrisWrapper(){};
	
	/**
	 * construct the debris wrapper
	 * @param item
	 * @param shape
	 */
	DebrisWrapper(Debris item, Ellipse2D.Double shape) {
		debrisItem = item;
		hitBox = shape;
	}
	
	/**
	 * get the hit box
	 * @return the hitBox
	 */
	public Ellipse2D.Double getHitBox() {
		return hitBox;
	}

	/**
	 * @param hitBox the hitBox to set
	 */
	public void setHitBox(Ellipse2D.Double hitBox) {
		this.hitBox = hitBox;
	}

	/**
	 * @return the debrisItem
	 */
	public Debris getDebrisItem() {
		return debrisItem;
	}

	/**
	 * @param debrisItem the debrisItem to set
	 */
	public void setDebrisItem(Debris debrisItem) {
		this.debrisItem = debrisItem;
	}

	/**
	 * update the hitbox's coordinates
	 */
	public void updateCoords() {
		hitBox.x = debrisItem.getPosX();
		hitBox.y = debrisItem.getPosY();
	}
	
}
