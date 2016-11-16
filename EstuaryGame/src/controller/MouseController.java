package controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import eNums.eBarrierType;
import model.Barriers;

	public class MouseController extends JPanel implements MouseListener, MouseMotionListener {

		boolean dragging = false;
		
		@Override
		public void mousePressed(MouseEvent e) {
			Barriers temp = GameController.getDragged();
			Point mouseCoords = new Point(e.getX(), e.getY());
			
			if (temp == null) {	//the dragged "barrier" is not set yet
				GameController.setDragged(new Barriers(e.getX(), e.getY())); //set it to the same coords as mouse click
				System.out.println("Mouse: " + e.getX() + " " + e.getY());
				System.out.println("Dragged: " + GameController.getDragged().getPosX() + " "+ GameController.getDragged().getPosY());
				if (Collisions.pointInside(GameController.getWallSpawn(), mouseCoords)) {
					GameController.getDragged().setType(eBarrierType.Wall);  //set type to wall
				}
				else if (Collisions.pointInside(GameController.getGabionsSpawn(), mouseCoords)) {
					GameController.getDragged().setType(eBarrierType.Gabion);  //set type to gabion
				}
			}
			dragging = true; //mouse is being dragged now
			repaint();
			System.out.println("pressed");

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (dragging == false)
				return;
			dragging = false;
			Barriers temp = GameController.getDragged();
			//Barriers b = Collisions.checkCollision(temp);
			if (Collisions.emptyBarrierCollision(temp)) {  //dragged temp to any of the barrier spaces that was empty
				if (temp.getType() == eBarrierType.Wall) {
					Barriers.setBarrierType(temp, eBarrierType.Wall); 
					//set barrier with same coords as temp to Wall
				}
				else if (temp.getType() == eBarrierType.Gabion) {
					Barriers.setBarrierType(temp, eBarrierType.Gabion);
				}
			}
			GameController.setDragged(null); //done dragging, don't need dragging shape
			repaint();
			// TODO Auto-generated method stub

		}
		


		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragging == false)
				return;
			//update coords of dragged shape
			GameController.getDragged().updatePos(e.getX(), e.getY());
			repaint();

		}
		
		//methods not needed to implement
		
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}
