package controller;

public class MovementController {
	public static int getStart(int vertex){
		int xstart = 0;
		if(vertex<=400){
			xstart = -(150)^2-vertex;
		}
		if(vertex<=400){
			xstart = (150)^2 + vertex;
		}
		return xstart;
	}
	public static void move(model.Floater floater){
		
		
	}
}
