package controller;

import eNums.eDebrisType;
import eNums.eFloaterState;
import model.Bin;
import model.Floater;

public class MovementController {
	static int windowHeight = 600;
	static int windowWidth = 800;
	static int sizeofDebrisCoast = 50; 
	public static int getStart(int shift){
		int xstart = 0;
		if(shift <= windowWidth/2){
			xstart = (-4/225)*(150)^2+shift;	
		}
		else if(shift > windowWidth/2){
			xstart = (4/225)*(150)^2+shift;
		}
		return xstart;	
	}
	
	public static void move(Floater floater){
		if(floater.getState()==eFloaterState.RESTING){
			return;
		}
		
		if(floater.getPosY()< (windowHeight*0.40)){
			double newy = floater.getPosY() + floater.getSpeed();
			
			double newx;
			
			if(floater.getVertex() <= windowWidth/2){
				newx = 50+(-0.00889)*(int)Math.pow(newy-125, 2.0)+ (int)floater.getVertex();
				floater.updatePos((int)newx, (int)newy);
			}
			else if(floater.getVertex() > windowWidth/2){
				newx = -50+(0.008889)*Math.pow(newy-125, 2.0)+(int)floater.getVertex();
				floater.updatePos((int) newx, (int) newy);
			}
			
			
		}
		
		else if(floater.getPosY()>=(windowHeight*0.40)){
			if((floater.getPosX()>(sizeofDebrisCoast) && floater.getPosX()<(windowWidth-(sizeofDebrisCoast+floater.getWidth()))) && floater.getPosY()< 375){
				if(floater.getVertex() <= windowWidth/2){
					floater.updatePos(floater.getPosX()-floater.getSpeed(), floater.getPosY()+floater.getSpeed());
				}
				else if(floater.getVertex() > windowWidth/2){
					floater.updatePos(floater.getPosX()+floater.getSpeed(), floater.getPosY()+floater.getSpeed());
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
	

	
	public static void Throw(Floater f, Bin b){
			double deltaX = b.getPosX() - f.getPosX();
			double deltaY = b.getPosY() - f.getPosY();
			double distance = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
			double speed = 0.25;
			if(distance>1){
				f.updatePos((int)(f.getPosX()+deltaX*speed),(int)(f.getPosY()+deltaY*speed));
			}
			
		
	}
	
	public static void wrongBinMove(Floater f){
		double deltaX;
		if(f.getPosX() < windowWidth/2){
			deltaX = sizeofDebrisCoast - f.getPosX();
		}
		else{
			deltaX = (windowWidth-sizeofDebrisCoast) - f.getPosX();
		}
		double deltaY = windowHeight/2 - f.getPosY();
		double direction = Math.atan2(deltaY, deltaX);
		double speed = 3.0;
		
		while(f.getPosY() != windowHeight/2){
			f.updatePos((int)(f.getPosX()+(speed*Math.cos(direction))), (int)(f.getPosY()+(speed*Math.sin(direction))));	
		}
	}
}
