package controller;

import java.util.Random;

import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.eHelperState;
import model.Bin;
import model.Floater;
import model.Helper;
import model.Rebuild;
import model.Remove;

public class MovementController {
	static int windowHeight = 600;
	static int windowWidth = 800;
	static int sizeofDebrisCoast = 50;
	
	/**
	 * get a start position
	 * @param shift
	 * @return
	 */
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
	/**
	 * update the move position 
	 * @param floater
	 */
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
	
/**
 * when the trash or things has to go to the bin, the position set up s
 * @param f
 * @param b
 */
	
	public static void Throw(Floater f, Bin b){
			//System.out.println("bin" + b.getPosX());
			double deltaX = b.getPosX() - f.getPosX();
			double deltaY = b.getPosY() - f.getPosY();
			double distance = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
			double speed = 0.25;
			if(distance>10){
				f.updatePos((int)(f.getPosX()+deltaX*speed),(int)(f.getPosY()+deltaY*speed));
			}
			else{
				f.setState(eFloaterState.HITBIN);
				System.out.println("hit bin");
			}
			
		
	}
	/**
	 * When things has a wrong bin move, reset the position
	 * @param f
	 */
	public static void wrongBinMove(Floater f){
		double deltaX;
		if(f.getPosX() < windowWidth/2){
			deltaX = sizeofDebrisCoast - f.getPosX();
		}
		else{
			deltaX = (windowWidth-sizeofDebrisCoast) - f.getPosX();
		}
		Random rand = new Random();
		int randNum = rand.nextInt(100)+650;
		double deltaY = randNum - f.getPosY();
		double distance = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		double speed = 0.35;
		if(distance>10){
			f.updatePos((int)(f.getPosX()+deltaX*speed),(int)(f.getPosY()+deltaY*speed));
		}
		else{
			f.setState(eFloaterState.RESTING);
		}
	}
	
	public static void walkMove(Helper h){
		int move = 8;
		if(h.getState()==eHelperState.WALKING){
			h.updatePosY(h.getPosY()+move);
		}
		if(h.getState()==eHelperState.WALKING_OFF){
			h.updatePosX(h.getPosX()+move);
		}
		
	}
		
}
