package view;

import java.awt.geom.Ellipse2D;

import model.Debris;

public class DebrisWrapper {

	Ellipse2D.Double hitBox;
	Debris debrisItem;
	
	DebrisWrapper(Debris item, Ellipse2D.Double shape) {
		debrisItem = item;
		hitBox = shape;
	}
	
	public void updateCoords() {
		hitBox.x = debrisItem.getPosX();
		hitBox.y = debrisItem.getPosY();
	}
	
}
