package controller;

import eNums.eFloaterState;
import model.Floater;

public class MovementController {
	public static int getStart(int shift){
		int xstart = 0;
		if(shift <= 400){
			xstart = 150+(-2/225)*(150)^2+shift;	
		}
		else if(shift > 400){
			xstart = (4/225)*(150)^2+shift;
		}
		return xstart;	
	}
	
	public static void move(Floater floater){
		if(floater.getState()==eFloaterState.RESTING){
			return;
		}
		
		if(floater.getPosY()<300){
			double newy = floater.getPosY() + floater.getSpeed();
			
			double newx;
			
			if(floater.getVertex() <= 400){
				newx = 150+(-0.008889)*(int)Math.pow(newy-125, 2.0)+ (int)floater.getVertex();
				floater.updatePos((int)newx, (int)newy);
			}
			else if(floater.getVertex() > 400){
				newx = (0.00889)*Math.pow(newy-125, 2.0)+floater.getVertex();
				floater.updatePos((int) newx, (int) newy);
			}
			
			
		}
		
		else if(floater.getPosY()>=300){
			if((floater.getPosX()>10 && floater.getPosX()<750)){
				if(floater.getVertex() <= 400){
					floater.updatePos(floater.getPosX()-floater.getSpeed(), floater.getPosY());
				}
				else if(floater.getVertex() > 400){
					floater.updatePos(floater.getPosX()+floater.getSpeed(), floater.getPosY());
				}
			}
			else{
				floater.setState(eFloaterState.RESTING);
			}
			
		}
		
		else{
			floater.setState(eFloaterState.RESTING);
		}
		
	}
	
	public static void Throw(Floater f){
		
	}
}
