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
		
		private GameController gc;

		boolean dragging = false;
		private Wall wallSpawn = new Wall(Barriers.getLeftEdge(), 390);
		private Gabions gabionsSpawn = new Gabions(Barriers.getRightEdge(), 390);
		private Barriers dragged = null;
		private Barriers dropSpot = null;
				
		public Barriers getWallSpawn() {
			return wallSpawn;
		}
		
		public Barriers getGabionsSpawn() {
			return gabionsSpawn;
		}
		
		public Barriers getDragged() {
			return dragged;
		}
		
		public void setGC(GameController g) {
			this.gc = g;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			Point mouseCoords = new Point(e.getX(), e.getY());
			
			if (dragged == null) {	//the dragged "barrier" is not set yet
				System.out.println("Mouse: " + mouseCoords.x + " " + mouseCoords.y);
				if (Collisions.pointInside(wallSpawn, mouseCoords)) {
					dragged = new Barriers(e.getX(), e.getY()); //set it to the same coords as mouse click
					dragged.setType(eBarrierType.Wall);  //set type to wall
					//System.out.println("inside wallspawn: " + dragged.getPosX() + " "+ dragged.getPosY());

				}
				else if (Collisions.pointInside(gabionsSpawn, mouseCoords)) {
					dragged = new Barriers(e.getX(), e.getY()); //set it to the same coords as mouse click
					dragged.setType(eBarrierType.Gabion);  //set type to gabion
					//System.out.println("inside gabionspawn: " + dragged.getPosX() + " "+ dragged.getPosY());

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
			dropSpot = gc.emptyBarrierCollision(dragged);
			if ((dragged != null) && (dropSpot!=null)) {  //dragged temp to any of the barrier spaces that was empty
				System.out.println("collided");
				if (dragged.getType() == eBarrierType.Wall) {
					gc.setBarrierType(dragged, eBarrierType.Wall); 
					dropSpot.setType(eBarrierType.Wall);
					dropSpot.geterosionTimer().start();
					//set barrier with same coords as temp to Wall
				}
				else if (dragged.getType() == eBarrierType.Gabion) {
					gc.setBarrierType(dragged, eBarrierType.Gabion);
					dropSpot.setType(eBarrierType.Gabion);
					dropSpot.geterosionTimer().start();
				}
			}
			dragged = null; //done dragging, don't need dragging shape
			repaint();
			// TODO Auto-generated method stub
			
			
			/*
			Point mouseCoords = new Point(e.getX(), e.getY());
			
			if (dragging == false)
				return;
			dragging = false;
			
			if (dragged != null) {
				System.out.println(mouseCoords);
				if ((dragged.getType() == eBarrierType.Wall) && (Collisions.pointInside(wallSpawn, mouseCoords))) {
					gc.setBarrierType(dragged, eBarrierType.Wall);
				}
				else if ((dragged.getType() == eBarrierType.Gabion) && (Collisions.pointInside(gabionsSpawn, mouseCoords))) {
					gc.setBarrierType(dragged, eBarrierType.Gabion);
				}
			}
			dragged = null; //done dragging, don't need dragging shape
			repaint();
			// TODO Auto-generated method stub*/

		}
		


		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragging == false)
				return;
			//update coords of dragged shape
			if (dragged != null) {
				dragged.updatePos(e.getX() - dragged.getWidth()/2, e.getY() - dragged.getHeight()/2);
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
