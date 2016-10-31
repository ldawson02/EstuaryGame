package controller;

import model.*;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import MovingRectangle.AngleAction;
import controller.*;
import eNums.eDebrisType;

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
		//set up automatic movements!
		//	->create timer for debris
	}
	
	public void gameOver(){
		
	}
	
	public void initTitleScreen(){
		//this actually should probably be in the VIEW
	}
	
	public void imageLoad(){
		
	}
	
	/*THIS SHOULD BE IN VIEW I THINK
	 * 
	public void bindKeyWith(String name, KeyStroke keyStroke, Action action){
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();
		im.put(keyStroke, name);
		am.put(name, action);
	}
	*/	
	
	//Increase difficulty based on health and timer
	public void checkDifficulty(){
		//TODO: let's have 5 different difficulty levels where the speed of erosion and debris spawn changes
	}
	
	//At (slightly) random intervals spawn debris, only should be initialized once!!
	public class spawnDebris implements ActionListener{
		public int timePassed = 0;
		//The time after which debris should spawn again (changes every time respawned)
		public int spawnTimeDebris;
		//The average length of time based on difficulty
		public int aveTime;
		//The limit to the random distributions range in milliseconds (AKA +- rTime/2)
		final public int rTime = 500;
		
		public spawnDebris(){
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//if the timer goes off then add another piece of debris at the top
			if(timePassed >= spawnTimeDebris){
				Random r = new Random();
				Debris d = new Debris(eDebrisType.values()[r.nextInt()%2]);
				items.addDebris(d);
				
				spawnTimeDebris = r.nextInt(rTime) + aveTime - rTime/2;
				timePassed = 0;
			}
			for(Debris d : items.getAllDebris()){
				//make each item float
				d.floating();
			}
			timePassed++;
		}
		
		public void updateAveTime(int newTime){
			aveTime = newTime;
		}
		
	}
	
	//At (slightly) random intervals erode stuff
	//there should be one for each coast line, probably also gabions, independent erosion patterns
	public class erosion implements ActionListener{
		public int timePassed;
		public int erosionTime;
		public int aveTime;
		final public int rTime = 500;
		
		public erosion(Coast c){
			Random r = new Random();
			//assumes erosion rate in Coast is in milliseconds
			aveTime = (int) c.getErosionRate();
			erosionTime = r.nextInt(rTime) + aveTime - rTime/2;
		}
		
		public erosion(Barriers b){
			Random r = new Random();
			//assumes decay rate in Barriers is in milliseconds
			aveTime = b.getDecayTime();
			erosionTime = r.nextInt(rTime) + aveTime - rTime/2;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(timePassed >= erosionTime){
				//erode some stuff
				
				timePassed = 0;
			}
			
			timePassed++;
		}
		
	}
	
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
