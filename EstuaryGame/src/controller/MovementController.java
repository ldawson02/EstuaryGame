package controller;


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
	public static void move(model.Floater floater){
		if(floater.getPosY()<300){
			double newy = floater.getPosY() + floater.getSpeed();
			
			double newx;
			
			if(floater.getVertex() <= 400){
				newx = 150+(-2/225)*Math.pow(newy-150, 2.0)+ (int)floater.getVertex();
				//System.out.println("newx " + newx + "newy " + newy);
				floater.updatePos((int)newx, (int)newy);
				//System.out.println("we moved left");
			}
			else if(floater.getVertex() > 400){
				newx = (4/225)*Math.pow(newy-150, 2.0)+floater.getVertex();
				floater.updatePos((int) newx, (int) newy);
				//System.out.println("we moved right");
			}
			
			
		}
		
		else if(floater.getPosY()>=300 && (floater.getPosX()>10 && floater.getPosX()<790)){
			
			if(floater.getVertex() <= 400){
				floater.updatePos(floater.getPosX()-floater.getSpeed(), floater.getPosY());
			}
			else if(floater.getVertex() > 400){
				floater.updatePos(floater.getPosX()+floater.getSpeed(), floater.getPosY());
			}
			
		}
		
		
		
	}
}
