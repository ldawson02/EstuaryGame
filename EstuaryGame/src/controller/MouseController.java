package controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import eNums.eBarrierType;
import model.Barriers;


	public class MouseController extends JPanel implements MouseListener, MouseMotionListener {
		
		private GameController gc;

		boolean dragging = false; 
		private Barriers wallSpawnL = new Barriers(Barriers.getLeftEdge(), Barriers.getSpawnY(), eBarrierType.Wall);
		private Barriers wallSpawnR = new Barriers(Barriers.getRightEdge(), Barriers.getSpawnY(), eBarrierType.Wall);
		private Barriers gabionsSpawnL = new Barriers(Barriers.getLeftEdge(), 
				Barriers.getSpawnY()+Barriers.defaultHeight, eBarrierType.Gabion);
		private Barriers gabionsSpawnR = new Barriers(Barriers.getRightEdge(), 
				Barriers.getSpawnY()+Barriers.defaultHeight, eBarrierType.Gabion);
		private Barriers dragged = null;
		private Barriers dropSpot = null;
		private boolean wallSpawnable = true;
		private boolean gabionSpawnable = true;
				
		/**
		 * left wall spawn getter
		 * @return wallSpawnL
		 */
		public Barriers getWallSpawnL() {
			return wallSpawnL;
		}
		
		/**
		 * right wall spawn getter
		 * @return wallSpawnR
		 */
		public Barriers getWallSpawnR() {
			return wallSpawnR;
		}
		
		/**
		 * left gabion spawn getter
		 * @return gabionsSpawnL
		 */
		public Barriers getGabionsSpawnL() {
			return gabionsSpawnL;
		}
		
		/**
		 * right gabion spawn getter
		 * @return gabionsSpawnR
		 */
		public Barriers getGabionsSpawnR() {
			return gabionsSpawnR;
		}
		/**
		 * getter for barrier that is getting dragged
		 * @return dragged
		 */
		public Barriers getDragged() {
			return dragged;
		}
		
		/**
		 * GAME CONTROLLER SETTER
		 * @param g
		 */
		public void setGC(GameController g) {
			this.gc = g;
		}
		
		/**
		 * sets whether the wall spawn can spawn
		 * @param b
		 */
		public void setWallSpawnable(boolean b){
			wallSpawnable = b;
		}
		
		/**
		 * sets whether the gabion spawn can spawn
		 * @param b
		 */
		public void setGabionSpawnable(boolean b){
			gabionSpawnable = b;
		}
		
		/**
		 * this method is to see after the mouse pressed, if is within the bounds of a spawn
		 * if so, it will set the mouse to dragging
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			Point mouseCoords = new Point(e.getX(), e.getY());
			
			if (dragged == null) {	//the dragged "barrier" is not set yet
				System.out.println("Mouse: " + mouseCoords.x + " " + mouseCoords.y);
				if (wallSpawnable && ((Collisions.pointInside(wallSpawnL, mouseCoords))
						|| (Collisions.pointInside(wallSpawnR, mouseCoords)))) {
					dragged = new Barriers(e.getX(), e.getY()); //set it to the same coords as mouse click
					dragged.setType(eBarrierType.Wall);  //set type to wall
					//System.out.println("inside wallspawn: " + dragged.getPosX() + " "+ dragged.getPosY());

				}
				else if (gabionSpawnable && ((Collisions.pointInside(gabionsSpawnL, mouseCoords))
						|| (Collisions.pointInside(gabionsSpawnR, mouseCoords)))) {
					dragged = new Barriers(e.getX(), e.getY()); //set it to the same coords as mouse click
					dragged.setType(eBarrierType.Gabion);  //set type to gabion
					//System.out.println("inside gabionspawn: " + dragged.getPosX() + " "+ dragged.getPosY());

				}
				dragging = true; //mouse is being dragged now
			}
			repaint();
			//System.out.println("pressed");

		}

		/**
		 * once the mouse is released, if it was dragging a barrier and is placed in an empty
		 * barrier spot, the spot will change to the correct type
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			
			if(dragged == null){
				return;
			}
			if (dragging == false) {
				return;
			}
			dragging = false;
			dropSpot = gc.emptyBarrierCollision(dragged);
			System.out.println("dragged: " + dragged.getPosX());
			if (dropSpot != null)
				System.out.println("dropspot: " + dropSpot.getPosX());
			System.out.println("mouse coord: " + e.getX());
			if ((dragged != null) && (dropSpot!=null)) {  //dragged temp to any of the barrier spaces that was empty
				System.out.println("new barrier");
				if (dragged.getType() == eBarrierType.Wall) {
					dragged = new Barriers(dragged.getPosX()+dragged.getWidth()/2, dragged.getPosY()+dragged.getHeight()/2, eBarrierType.Wall);
					gc.setBarrierType(dragged, eBarrierType.Wall); 
					dropSpot.setType(eBarrierType.Wall);
					dropSpot.getErosionTimer().start();
					ScoreController.scoreWall();
					//set barrier with same coords as temp to Wall
				}
				else if (dragged.getType() == eBarrierType.Gabion) {
					dragged = new Barriers(dragged.getPosX()+dragged.getWidth()/2, dragged.getPosY()+dragged.getHeight()/2, eBarrierType.Gabion);
					gc.setBarrierType(dragged, eBarrierType.Gabion);
					dropSpot.setType(eBarrierType.Gabion);
					dropSpot.getErosionTimer().start();
					ScoreController.scoreGabion();
				}
			}
			dragged = null; //done dragging, don't need dragging shape
		}
		


		@Override
		/**
		 * If mouse dragged, the position of the dragged barrier is updated, with the 
		 * middle of the barrier at the mouse cursor
		 */
		public void mouseDragged(MouseEvent e) {
			if (dragging == false)
				return;
			//update coords of dragged shape
			if (dragged != null) {
				dragged.updatePos(e.getX() - dragged.getWidth()/2, e.getY() - dragged.getHeight()/2);
			}
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
