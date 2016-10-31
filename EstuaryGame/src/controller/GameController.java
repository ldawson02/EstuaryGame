package controller;

import model.*;
import view.*;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import MovingRectangle.AngleAction;
import controller.*;

public class GameController {

	//the big shebang
	EstuaryGame mainGame;
	Player mainPlayer;
	ActiveItems items;
	Action leftAct;
	Action rightAct;
	Action upAct;
	Action downAct;
	
	public void setup(){
		//Create the player
		mainPlayer = new Player();
		
		//bind the keys
		leftAct = new HAction(-1 * mainPlayer.speed);
		rightAct = new HAction(1 * mainPlayer.speed);
		upAct = new VAction(1 * mainPlayer.speed);
		downAct = new VAction(1 * mainPlayer.speed);
		view.bindKeyWith("x.left", KeyStroke.getKeyStroke("LEFT"), leftAct);
		view.bindKeyWith("x.right", KeyStroke.getKeyStroke("RIGHT"), rightAct);
		view.bindKeyWith("x.up", KeyStroke.getKeyStroke("UP"), upAct);
		view.bindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"), downAct);
		//maybe also have an action for building gabion, like pressing g
		
		//Create the initial walls
		
		
		
		imageLoad();
		initTitleScreen();
		startGame();
	}
	
	public void tearDown(){
		
	}
	
	public void startGame(){
		
	}
	
	public void gameOver(){
		
	}
	
	public void initTitleScreen(){
		
	}
	
	public void imageLoad(){
		
	}
	
	/*THIS SHOULD BE IN VIEW I THINK
	public void bindKeyWith(String name, KeyStroke keyStroke, Action action){
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();
		im.put(keyStroke, name);
		am.put(name, action);
	}
	*/	
	public class HAction extends AbstractAction{
		
		//the amount the player moves when you press the key
		private int moveSize;
		
		public HAction(int jump){
			this.moveSize = jump;
		}
		
		public void updateSpeed(int jump){
			this.moveSize = jump;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			mainPlayer.updatePosX(mainPlayer.getPosX() + moveSize);
		}
		
	}
	
	public class VAction extends AbstractAction{

		//the amount the player moves when you press the key
		private int moveSize;
		
		public VAction(int jump){
			this.moveSize = jump;
		}
		
		public void updateSpeed(int jump){
			this.moveSize = jump;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			mainPlayer.updatePosY(mainPlayer.getPosY()+moveSize);
		}
		
	}
	
}
