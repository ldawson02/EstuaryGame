package controller;


public class MovementController {
	public static int getStart(int shift){
		int xstart = 0;
		if(shift <= 400){
			xstart = 150+(-2/225)*(150)^2+shift;	
		}
		else if(shift > 400){
			xstart = (2/225)*(150)^2+shift;
		}
		return xstart;	
	}
	public static void move(model.Floater floater){
		if(floater.getPosY()<300){
			int newy = floater.getPosY() + floater.getSpeed();
			int newx;
			if(floater.getVertex() <= 400){
				newx = 150+(-2/225)*(newy-150)^2+floater.getVertex();
				floater.updatePos(newx, newy);
			}
			else if(floater.getVertex() > 400){
				newx = (2/225)*(newy-150)^2+floater.getVertex();
				floater.updatePos(newx, newy);
			}
		}
		else if(floater.getPosY()>=300){
			
		}
		
		
	}
}
