package controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import eNums.eBarrierType;
import model.Barriers;
import model.Gabions;
import model.Wall;

	public class MouseController extends JPanel implements MouseListener, MouseMotionListener {

		boolean dragging = false;
		private Wall wallSpawn = new Wall(Barriers.getLeftEdge(), 390);
		private Gabions gabionsSpawn = new Gabions(Barriers.getRightEdge(), 390);
		private Barriers dragged = null;
		

		public Barriers getWallSpawn() {
			return wallSpawn;
		}
		
		public Barriers getGabionsSpawn() {
			return gabionsSpawn;
		}
		
		public Barriers getDragged() {
			return dragged;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			Point mouseCoords = new Point(e.getX(), e.getY());
			
			if (dragged == null) {	//the dragged "barrier" is not set yet
				System.out.println("Mouse: " + mouseCoords.x + " " + mouseCoords.y);
				if (Collisions.pointInside(wallSpawn, mouseCoords)) {
					dragged = new Barriers(e.getX(), e.getY()); //set it to the same coords as mouse click
					dragged.setType(eBarrierType.Wall);  //set type to wall
					System.out.println("inside wallspawn: " + dragged.getPosX() + " "+ dragged.getPosY());

				}
				else if (Collisions.pointInside(gabionsSpawn, mouseCoords)) {
					dragged = new Barriers(e.getX(), e.getY()); //set it to the same coords as mouse click
					dragged.setType(eBarrierType.Gabion);  //set type to gabion
					System.out.println("inside gabionspawn: " + dragged.getPosX() + " "+ dragged.getPosY());

				}
				dragging = true; //mouse is being dragged now
			}
			repaint();
			System.out.println("pressed");

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (dragging == false)
				return;
			dragging = false;
			if ((dragged != null) && (Collisions.emptyBarrierCollision(dragged))) {  //dragged temp to any of the barrier spaces that was empty
				System.out.println("collided");
				if (dragged.getType() == eBarrierType.Wall) {
					Barriers.setBarrierType(dragged, eBarrierType.Wall); 
					//set barrier with same coords as temp to Wall
				}
				else if (dragged.getType() == eBarrierType.Gabion) {
					Barriers.setBarrierType(dragged, eBarrierType.Gabion);
				}
			}
			dragged = null; //done dragging, don't need dragging shape
			repaint();
			// TODO Auto-generated method stub

		}
		


		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragging == false)
				return;
			//update coords of dragged shape
			if (dragged != null) {
				dragged.updatePos(e.getX(), e.getY());
			}
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
