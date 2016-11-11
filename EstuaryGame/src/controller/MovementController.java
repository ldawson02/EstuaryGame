package controller;

import eNums.eDebrisType;
import eNums.eFloaterState;
import model.Bin;
import model.Floater;

public class MovementController {
	static int windowHeight = 600;
	static int windowWidth = 800;
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
		
		if(floater.getPosY()<250){
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
		
		else if(floater.getPosY()>=250){
			if((floater.getPosX()>50 && floater.getPosX()<700)){
				if(floater.getVertex() <= windowWidth/2){
					floater.updatePos(floater.getPosX()-floater.getSpeed(), floater.getPosY()+2);
				}
				else if(floater.getVertex() > windowWidth/2){
					floater.updatePos(floater.getPosX()+floater.getSpeed(), floater.getPosY()+2);
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
			double direction = Math.atan2(deltaY, deltaX);
			double speed = 3.0;
			while(f.getPosX() != b.getPosX() && f.getPosY() != b.getPosY()){
				f.updatePos((int)(f.getPosX()+(speed*Math.cos(direction))), (int)(f.getPosY()+(speed*Math.sin(direction))));	
			}	
		
	}
	
	public static void wrongBinMove(Floater f){
		double deltaX;
		if(f.getPosX() < 400){
			deltaX = 50 - f.getPosX();
		}
		else{
			deltaX = 700 - f.getPosX();
		}
		double deltaY = 300 - f.getPosY();
		double direction = Math.atan2(deltaY, deltaX);
		double speed = 3.0;
		
		while(f.getPosY() != 300){
			f.updatePos((int)(f.getPosX()+(speed*Math.cos(direction))), (int)(f.getPosY()+(speed*Math.sin(direction))));	
		}
	}
}
