package controller;

import model.*;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import controller.*;
import eNums.eDebrisType;

public class GameController {

	//the big shebang
	private EstuaryGame mainGame;
	Player mainPlayer;
	private ActiveItems items;
	Action leftAct;
	Action rightAct;
	Action upAct;
	Action downAct;
	final private int floatDelay = 1; //TODO
	Timer debrisFloating;
	Timer powersFloating;
	final private int erodeDelay = 1;//TODO
	Timer coastRErosion;
	Timer coastLErosion;
	
	final private int paintDelay = 30;
	Timer theBigTimer;
	private int timeElapsed = 0;
	
	spawnDebris debrisMover;
	spawnPowers powerMover;
	erosion RcoastMover;
	erosion LcoastMover;
	
	public GameController(EstuaryGame mainGame){
		setMainGame(mainGame);
		setup();
	}
	
	public EstuaryGame getMainGame() {
		return mainGame;
	}

	public void setMainGame(EstuaryGame mainGame) {
		this.mainGame = mainGame;
	}

	public ActiveItems getItems() {
		return items;
	}

	public void setItems(ActiveItems items) {
		this.items = items;
	}

	public void setup(){
		//Start the paint timer
		theBigTimer = new Timer(paintDelay, new mainTimer());
		theBigTimer.start();
		
		//Create the player
		mainPlayer = new Player();
		mainPlayer.updatePos(380, 280); //These are hard coded
		
		//bind the keys
		leftAct = new HAction(-1 * mainPlayer.speed);
		rightAct = new HAction(1 * mainPlayer.speed);
		upAct = new VAction(1 * mainPlayer.speed);
		downAct = new VAction(1 * mainPlayer.speed);
		mainGame.bindKeyWith("x.left", KeyStroke.getKeyStroke("LEFT"), leftAct);
		mainGame.bindKeyWith("x.right", KeyStroke.getKeyStroke("RIGHT"), rightAct);
		mainGame.bindKeyWith("x.up", KeyStroke.getKeyStroke("UP"), upAct);
		mainGame.bindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"), downAct);
		//maybe also have an action for building gabion, like pressing g
		mainGame.bindKeyWith("gabionBuild", KeyStroke.getKeyStroke("g"), new gabionAct());
		mainGame.bindKeyWith("wallBuild", KeyStroke.getKeyStroke("h"), new wallAct());
		
		//Create the initial walls
		for(int i = 0; i<4; i++){
			items.addBarrier(new Wall());//TODO: meh
		}
		
		//create the coasts
		
		
		
		mainGame.imageLoad();
		initTitleScreen();
		startGame();
	}
	
	public void tearDown(){
		
	}
	
	public void startGame(){
		//set up automatic movements!
		//	->create timer for debris
		//Turn on the screen timer!
		items.addScreenTimer(new ScreenTimer());
		items.getScreenTimer.start();
		
		debrisMover = new spawnDebris();
		powerMover = new spawnPowers();
		RcoastMover = new erosion(items.getCoastR());
		LcoastMover = new erosion(items.getCoastL());
		
		debrisFloating = new Timer(floatDelay, debrisMover);
		debrisFloating.start();
		
		powersFloating = new Timer(floatDelay, powerMover);
		powersFloating.start();
		
		coastRErosion = new Timer(erodeDelay, RcoastMover);
		coastLErosion = new Timer(erodeDelay, LcoastMover);
		coastRErosion.start();
		coastLErosion.start();
	}
	
	public void gameOver(){
		
	}
	
	public void initTitleScreen(){
		//this actually should probably be in the VIEW
	}
	
	public void imageLoad(){
		
	}
	
	
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
		public int aveTime = 8000;
		//The limit to the random distributions range in milliseconds (AKA +- rTime/2)
		final public int rTime = 500;
		
		public spawnDebris(){
			items.addDebris(newDebris());
			resetTimer();
		}
		
		//returns a new randomly generated piece of Debris
		public Debris newDebris(){
			Random r = new Random();
			//generate initial position;
			int xPos = MovementController.getStart(r.nextInt(500)+150);
			
			Debris d = new Debris(eDebrisType.values()[r.nextInt()%2]);
			d.updatePos(xPos, 0);
			return d;
		}
		
		//resets the spawnTimeDebris to a new randomly generated number (within range)
		public void resetTimer(){
			Random r = new Random();
			spawnTimeDebris = r.nextInt(rTime) + aveTime - rTime/2;
			timePassed = 0;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//if the timer goes off then add another piece of debris at the top
			if(timePassed >= spawnTimeDebris){
				items.addDebris(newDebris());
				resetTimer();
			}
			for(Debris d : items.getAllDebris()){
				//make each item float
				//d.floating();
				MovementController.move(d);
			}
			
			timePassed+=floatDelay;
		}
		
		public void updateAveTime(int newTime){
			aveTime = newTime;
		}
		
	}
	
	//The point of this class is to create a timer that calls paints 
	public class mainTimer implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent e) {
			mainGame.paint(mainGame.getGraphics());
			if(items.getScreenTimer.getState()==eScreenTimerState.ON){
				timeElapsed+=paintDelay;
			}
		}
		
		
		public int getTimeElapsed(){
			return timeElapsed;
		}
	}
	//At (slightly) random intervals spawn powers, only should be initialized once!!
	public class spawnPowers implements ActionListener{
		public int timePassed = 0;
		public int spawnTimePowers;
		public int aveTime = 10000;
		final public int rTime = 500;

		public spawnPowers(){
			resetTimer();
		}

		public Powers newPower(){
			Random r = new Random();
			//generate initial position;
			int xPos = MovementController.getStart(r.nextInt(500)+150);

			Powers p;
			if(r.nextBoolean()){
				p = new Rebuild(xPos, 0);
			}
			else{
				p = new Remove(xPos, 0);
			}
			return p;
		}
		
		public void resetTimer(){
			Random r = new Random();
			spawnTimePowers = r.nextInt(rTime) + aveTime - rTime/2;
			timePassed = 0;
		}
		
		//Unimplemented methods
		public void quickSpawn(){}
		public void quickSpawnRebuild(){}
		public void quickSpawnRemove(){}

		@Override
		public void actionPerformed(ActionEvent e) {
			//if the timer goes off then add another power at the top
			if(timePassed >= spawnTimePowers){
				items.addPower(newPower());
				resetTimer();
			}
			for(Powers p : items.getAllPowers()){
				//make each item float
				//p.floating();
				MovementController.move(p);
			}
			timePassed+=floatDelay;
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
			
			timePassed+=erodeDelay;
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
	
	public class wallAct extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			items.addBarrier(new Wall(mainPlayer.getPosX(), mainPlayer.getPosY()));
		}
		
	}
	public class gabionAct extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			items.addBarrier(new Gabions(mainPlayer.getPosX(), mainPlayer.getPosY()));
		}
		
	}

}
