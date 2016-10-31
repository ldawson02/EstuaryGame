package controller;

import java.awt.geom.QuadCurve2D;

public class MovementController {
	public static int getStart(int shift){
		int xstart = 0;
		if(shift <= 400){
			xstart = 150+(-2/225)*(150)^2+shift;	
		}
		else if(shift > 400){
			xstart = 400+(2/225)*(150)^2+shift;
		}
		return xstart;	
	}
	public static void move(model.Floater floater){
		int newy = floater.getPosY() + floater.getSpeed();
		int newx;
		if(floater.getVertex() <= 400){
			newx = 150+(-2/225)*(newy-150)^2+floater.getVertex();
			floater.updatePos(newx, newy);
		}
		else if(floater.getVertex() > 400){
			newx = 400+(2/225)*(newy-150)^2+floater.getVertex();
			floater.updatePos(newx, newy);
		}
		
		
	}
}
