package controller;

import model.*;
import view.*;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.Timer;

import Serialization.Controller.CleanUpSerializeAction;
import Serialization.Controller.ReadSerializeAction;
import Serialization.Controller.SerializeAction;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import controller.*;
import eNums.eBarrierState;
import eNums.eBarrierType;
import eNums.eDebrisType;
import eNums.eDifficulty;
import eNums.eFloaterState;
import eNums.eHealthChanges;
import eNums.eHelperState;
import eNums.ePlayerState;
import eNums.eScreenTimerState;
import eNums.eThrowDirection;
import eNums.eTutorialState;

public class GameController implements Serializable {

	//the big shebang
	private static EstuaryGame mainGame;
	private Player mainPlayer;
	private ActiveItems items = new ActiveItems();
	Action leftAct;
	Action rightAct;
	Action upAct;
	Action downAct;
	Action caughtLeftAct;
	Action caughtRightAct;
	Action throwAct;
	
	public final static int dimX = 800;
	public final static int dimY = 600;
	
	//Makes sure you can only catch one Floater at a time
	private boolean choosingThrow = false;
	private boolean initiatingPowerUp = false;
	final private int floatDelay = 100; //TODO
	Timer debrisFloating;
	Timer powersFloating;
	final private int erodeDelay = 100;//TODO
	
	GameController thisGame = this;
	private ArrayList<Timer> allTimers = new ArrayList<Timer>();
	private ArrayList<Timer> tutorialTimers = new ArrayList<Timer>();
	
	protected final int paintDelay = 30;
	Timer theBigTimer;
	mainTimer paintTimer;
	private int timeElapsed = 0;
	
	spawnDebris debrisMover;
	spawnPowers powerMover;
	
	eDifficulty difficulty = eDifficulty.MEDIUM;
	//erosion RcoastMover;
	//erosion LcoastMover;
	
	private boolean gameEnd;
	Collisions collision = new Collisions();
		
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
	
	/**
	 * @return the theBigTimer
	 */
	public int getTheBigTimer() {
		//TODO
		return timeElapsed;
	}
	
	public spawnDebris getSpawnDebris() {
		return debrisMover;
	}

	public void normalKeyBind(){
		mainGame.bindKeyWith("x.left", KeyStroke.getKeyStroke("LEFT"), leftAct);
		mainGame.bindKeyWith("x.right", KeyStroke.getKeyStroke("RIGHT"), rightAct);
		mainGame.bindKeyWith("x.up", KeyStroke.getKeyStroke("UP"), upAct);
		mainGame.bindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"), downAct);
	}
	
	public void serializationKeyBind() {
		mainGame.bindKeyWith("serialize", KeyStroke.getKeyStroke('1'), new SerializeAction());
		mainGame.bindKeyWith("readserialized", KeyStroke.getKeyStroke('2'), new ReadSerializeAction());
		mainGame.bindKeyWith("cleanupserialized", KeyStroke.getKeyStroke('3'), new CleanUpSerializeAction());
	}

	
	public void setup(){
		//Add health bar!!
		items.addHealthBar(new HealthBar());
		
		//Add the screen Timer
		items.addScreenTimer(new ScreenTimer());
		
		//Create the player
		setMainPlayer(new Player());
		items.setMainPlayer(getMainPlayer());
		getMainPlayer().updatePos(380, 280); //These are hard coded
		
		//Connect the Collision controller w the Player
		collision.setPlayer(getMainPlayer());
		
		//bind the keys
		leftAct = new HAction(-1 * getMainPlayer().getSpeed());
		rightAct = new HAction(1 * getMainPlayer().getSpeed());
		upAct = new VAction(-1 * getMainPlayer().getSpeed());
		downAct = new VAction(1 * getMainPlayer().getSpeed());
		normalKeyBind();
		
		serializationKeyBind();
		
		//Reset stuff from last game
		ScoreController.setScore(0);
		Storm.setAppeared(false);
		items.removeAllDebris();
		items.setAllBarriers();
		items.removeAllPowers();
		
		ArrayList<Barriers> left = Barriers.setUpLeftCoast();
		ArrayList<Coast> leftCoast = Coast.setUpLeftCoast(left);
		for (Barriers b : left) {
			items.addBarrier(b);
			b.setProtector(true);
		}
		for(Coast c : leftCoast){
			items.addCoast(c);
		}
		ArrayList<Barriers> right = Barriers.setUpRightCoast();
		ArrayList<Coast> rightCoast = Coast.setUpRightCoast(right);
		for (Barriers b : right) {
			items.addBarrier(b);
			b.setProtector(true);
		}
		for(Coast c : rightCoast){
			items.addCoast(c);
		}
		items.getAllBarriers().get(0).setType(eBarrierType.Gabion);
		items.getAllBarriers().get(1).setType(eBarrierType.Wall);
		items.getAllBarriers().get(2).setType(eBarrierType.Wall);
		items.getAllBarriers().get(3).setType(eBarrierType.Wall);
		items.getAllBarriers().get(4).setType(eBarrierType.Gabion);
		items.getAllBarriers().get(6).setType(eBarrierType.Wall);
		items.getAllBarriers().get(7).setType(eBarrierType.Gabion);
		
		
		//Create the bins
		items.getTrashBin().updatePos(50, 150);
		items.getRecycleBin().updatePos(700, 150);
		

		setUpPaintTimer();
		allTimers.add(theBigTimer);
		
		//mainGame.initTitleScreen();
		startGame();
	}
	
	public void setUpPaintTimer(){
		//Start the paint timer
		paintTimer = new mainTimer();
		theBigTimer = new Timer(paintDelay, paintTimer);
		theBigTimer.start();
	}
	
	public void tearDown(){
		mainGame.unbindKeyWith("GotoEndGame", KeyStroke.getKeyStroke("ENTER"));
	}
	
	public void startGame(){
		//set up automatic movements!
		//	->create timer for debris
		//Turn on the screen timer!
		items.getScreenTimer().start();
		
		debrisMover = new spawnDebris();
		powerMover = new spawnPowers();
		
		debrisFloating = new Timer(floatDelay, debrisMover);
		debrisFloating.start();
		allTimers.add(debrisFloating);
		
		powersFloating = new Timer(floatDelay, powerMover);
		powersFloating.start();
		allTimers.add(powersFloating);
		
		erosionSetup();

	}
	
	public void erosionSetup(){
		barrierErosion bErode; int i = 0;
		for(Barriers b: items.getAllBarriers()){
			bErode = new barrierErosion(b);
			b.setErosionTimer(new Timer(this.erodeDelay, bErode));
			b.getErosionTimer().start();
			allTimers.add(b.getErosionTimer());
			i++;
		}
		System.out.println("NUMBER OF EROSION TIMERS: " + i);
		
		coastErosion cErode;
		for(Coast c : items.getCoast()){
			cErode = new coastErosion(c);
			c.setErosionTimer(new Timer(this.erodeDelay, cErode));
			c.getErosionTimer().start();
			allTimers.add(c.getErosionTimer());
		}
	}
	
	public void gameOver(){
		stopTimers();
		Action endGameAct = new endGameAction();
		mainGame.bindKeyWith("GotoEndGame", KeyStroke.getKeyStroke("ENTER"), endGameAct);
		
	}
	
	public void stopTimers(){
		for(Timer t : allTimers){
			t.stop();
		}
	}
	
	public void caughtSetup(Debris d){
		this.choosingThrow = true;
		items.mainPlayer.setState(ePlayerState.Lifting);
		Action caughtLeftAct = new ThrowChoice(eThrowDirection.LEFT,d);
		Action caughtRightAct = new ThrowChoice(eThrowDirection.RIGHT,d);
		Action throwAct = new ThrowChosen(d);
		
		//Change the function of the keys
		mainGame.bindKeyWith("x.leftArrow", KeyStroke.getKeyStroke("LEFT"), caughtLeftAct);
		mainGame.bindKeyWith("x.rightArrow", KeyStroke.getKeyStroke("RIGHT"), caughtRightAct);
		mainGame.bindKeyWith("throwDebris", KeyStroke.getKeyStroke("ENTER"), throwAct);
		
		//Don't let the player move!
		mainGame.unbindKeyWith("x.up", KeyStroke.getKeyStroke("UP"));
		mainGame.unbindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"));
	}
	
	public void caughtSetup(Powers p){
		this.initiatingPowerUp = true;
		Action powerAction = new PowerInitiate(p);
		items.getPlayer().setState(ePlayerState.Lifting);
		mainGame.bindKeyWith("initiatePowerUp", KeyStroke.getKeyStroke("ENTER"), powerAction);
		freezeMotion();
		
	}
	
	public void freezeMotion(){
		mainGame.unbindKeyWith("x.up", KeyStroke.getKeyStroke("UP"));
		mainGame.unbindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"));
		mainGame.unbindKeyWith("x.left", KeyStroke.getKeyStroke("LEFT"));
		mainGame.unbindKeyWith("x.right", KeyStroke.getKeyStroke("RIGHT"));
	}
	
	public void thrownSetup(){
		//Return the keys to their original state, allow Player to move
		normalKeyBind();
		mainGame.unbindKeyWith("throwDebris", KeyStroke.getKeyStroke("ENTER"));
		this.choosingThrow = false;
	}
	public void initiatedPowerSetup(){
		normalKeyBind();
		mainGame.unbindKeyWith("throwDebris", KeyStroke.getKeyStroke("ENTER"));
		this.initiatingPowerUp = false;
		
	}
	
	public void checkCollisions(){
		
	}
	
	//Increase difficulty based on health and timer
	public void checkDifficulty(){
		//TODO: let's have 5 different difficulty levels where the speed of erosion and debris spawn changes
		if(items.getHealthBar().getHealth()>75 && items.getScreenTimer().getElapsedTime() > items.getScreenTimer().getMaxTime()/5){
			this.setDifficulty(difficulty.getNextDifficulty());
			System.out.println("Increasing Difficulty!!");
		}
		else if (items.getHealthBar().getHealth()<25 && items.getScreenTimer().getElapsedTime() > items.getScreenTimer().getMaxTime()/5){
			this.setDifficulty(difficulty.getPreviousDifficulty());
			System.out.println("decreasing Difficulty!!");
		}
		
		System.out.println("Current Difficulty" + difficulty);
		
		switch(difficulty){
		case VERYEASY:
			for(Coast c: items.getCoast()){
				c.setErosionRate(20000);
			}
			this.getSpawnDebris().updateAveTime(15000);
			break;
		case EASY:
			for(Coast c: items.getCoast()){
				c.setErosionRate(15000);
			}
			this.getSpawnDebris().updateAveTime(10000);
			break;
		case MEDIUM:
			for(Coast c: items.getCoast()){
				c.setErosionRate(10000);
			}
			this.getSpawnDebris().updateAveTime(8000);
			break;
		case HARD:
			for(Coast c: items.getCoast()){
				c.setErosionRate(8000);
			}
			this.getSpawnDebris().updateAveTime(5000);
			break;
		case IMPOSSIBLE:
			for(Coast c: items.getCoast()){
				c.setErosionRate(5000);
			}
			this.getSpawnDebris().updateAveTime(1000);
			break;
		}
		
	}
	
	/**
	 * @author Lia Dawson
	 * @version 1.0
	 * @since 10/25/16
	 * 
	 *        The spawnDebris class handles the movement and spawning of Debris
	 *        in EstuaryGame. It should only be initialized once per game, and
	 *        will be called continuously throughout the entire lifecycle of the
	 *        game
	 */
	public class spawnDebris implements ActionListener, Serializable {
		public int timePassed = 0;
		//The time after which debris should spawn again (changes every time respawned)
		public int spawnTimeDebris;
		public int getTimePassed() {
			return timePassed;
		}

		public void setTimePassed(int timePassed) {
			this.timePassed = timePassed;
		}

		public int getSpawnTimeDebris() {
			return spawnTimeDebris;
		}

		public void setSpawnTimeDebris(int spawnTimeDebris) {
			this.spawnTimeDebris = spawnTimeDebris;
		}

		//The average length of time based on difficulty
		public int aveTime = 3000;
		//The limit to the random distributions range in milliseconds (AKA +- rTime/2)
		final public int rTime = 500;
	
		
		public spawnDebris(){
			items.addDebris(newDebris());
			resetTimer();
		}
		
		public spawnDebris(boolean t){
			items.addDebris(newDebris());
			resetTimer();
		}
		
		//returns a new randomly generated piece of Debris
		public Debris newDebris(){
			Random r = new Random();
			//generate initial position;
			int randomx = r.nextInt(500)+150;
			System.out.println(randomx);
			int xPos = MovementController.getStart(randomx);
			
			int dtype = r.nextInt() % 2;
			Debris d;
			if (dtype == 0) {
				d = new Debris(eDebrisType.TRASH);
			} else {
				d = new Debris(eDebrisType.RECYCLING);
			}
			//d.setController(thisGame); //Bin stuff
			d.setBins(items.getTrashBin(), items.getRecycleBin());
			d.updatePos(xPos, 0);
			d.setVertex(xPos);
			return d;
		}
		
		//resets the spawnTimeDebris to a new randomly generated number (within range)
		public void resetTimer(){
			Random r = new Random();
			spawnTimeDebris = r.nextInt(rTime) + aveTime - rTime/2;
			timePassed = 0;
		}
		
		public void checkCatchDebris(Debris d){
			if(!thisGame.choosingThrow && collision.checkCollision(d) && !thisGame.initiatingPowerUp){
				//sets the Debris state to Lifted
				d.catching();
				//sequence of events for a caught Debris initiated
				thisGame.caughtSetup(d);
				//Move the trash to above the Player's head
				d.updatePos(mainPlayer.getPosX()+mainPlayer.getWidth()/2 - d.getWidth()/2, mainPlayer.getPosY()-d.getHeight());
			}
		}
		
		public void move(Debris d){
			MovementController.move(d);
			checkCatchDebris(d);
		}
		
		public void throwing(Debris d, ArrayList<Debris> toDelete){
			//This should be a sequence like move()
			//Function could return true or false to indicate it it's hit the Bin yet, then initiate next sequence
			MovementController.Throw(d, d.getBin());

			//Update the healthbar if it hit on this round
			if (d.getState() == eFloaterState.HITBIN) {
				System.out.print("\nBin hit this round and");
				if (d.getCorrectBin()) {
					System.out.print(" bin was correct.\n");
					d.setState(eFloaterState.RESTING);
					toDelete.add(d);
					items.getHealthBar().update(eHealthChanges.CorrectBin.getDelta());
					ScoreController.scoreBin();
				}
				else {
					System.out.print(" bin was incorrect.\n");
					MovementController.wrongBinMove(d);
					d.setState(eFloaterState.RESTING);
					items.getHealthBar().update(eHealthChanges.IncorrectBin.getDelta());
				}
			}
			//If the debris hit the wrong bin it should go back to the coast
		
		}
		
		public void spawnTimeReached(){
			items.addDebris(newDebris());
			resetTimer();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			ArrayList<Debris> toDelete = new ArrayList<Debris>();
			//might want to put this for loop in its own class in the Controller
			for(Debris d : items.getAllDebris()){
				//make each item float
				if(d.getState()==eFloaterState.MOVING){

					move(d);
					//If the debris hit the coast this round, decrement health
					if (d.getState() == eFloaterState.RESTING) {
						System.out.println("Debris hit coast");
						items.getHealthBar().update(eHealthChanges.DebrisHitCoast.getDelta());
					}
				}
				else if(d.getState()==eFloaterState.THROWING){
					throwing(d, toDelete);
				}
				else if(d.getState()==eFloaterState.RESTING) {
					checkCatchDebris(d);
				}
			}
			
			//Now delete any debris that hit
			for(Debris del : toDelete){
				items.removeDebris(del);
			}
				
			//if the timer goes off then add another piece of debris at the top
			if(timePassed >= spawnTimeDebris){
				spawnTimeReached();
			}
			
			timePassed+=floatDelay;
		}
		
		public void updateAveTime(int newTime){
			aveTime = newTime;
		}
		
	}
	
	//The point of this class is to create a timer that calls paint
	public class mainTimer implements ActionListener, Serializable {
		int maxTime = items.getScreenTimer().getMaxTime();
		
		public int scoringTime = 0;
		final public int scoreCheck = maxTime/500;
		public int healthTime = 0;

		final public int healthCheck = maxTime/18;
		final public int delaySpotlight = 1000;
		
		public int stormTime = 0;
		public int realStormTime = 0;
		boolean stormChecked = false;
		final public int stormCheck = maxTime*3/4; //storm check 3/4th into the game
		
		@Override
		public void actionPerformed(ActionEvent e) {
			mainGame.repaint();

			checkItems();
		}
		
		public void checkItems(){
			if(items.getScreenTimer().getState() == eScreenTimerState.ON){
				timeElapsed+=paintDelay;
				scoringTime+=paintDelay;
				healthTime+=paintDelay;
				stormTime+=paintDelay;
				items.getScreenTimer().setElapsedTime(timeElapsed);
				
				checkStormTime();
				checkScoreTime();
				checkOverallHealth();
				if(items.getScreenTimer().getState()==eScreenTimerState.OFF){
					gameOver();
				}
				if(items.getHealthBar().getHealth() <= 0){
					gameOver();
				}
			}
		}
		
		public void checkStormTime() {
			if (stormTime >= 10000){  //later change this to stormCheck
				HealthBar hb = items.getHealthBar();
				if (!Storm.getAppeared() && (hb.getHealth() >= hb.getMaxHealth()*.75)) {
					if (!stormChecked) {
						realStormTime = stormTime + delaySpotlight*3;
						stormChecked = true;
						System.out.println("\nStorm incoming in 3 seconds!!");
					}
					//storm appears only when it hasn't appeared yet, and current health is 75% or more
					callStorm(stormTime);
				}
			}

		}
		
		public void callStorm(int time){
			if (stormTime >= realStormTime) {
				Storm.stormEffects(items, debrisMover);
				Storm.setAppeared(true);
			}
		}
		
		public void checkScoreTime(){
			if(scoringTime >= scoreCheck){
				ScoreController.scoreHealth(items.getHealthBar().getHealth());
				checkDifficulty();
				scoringTime = 0;
			}
		}
		
		public void checkOverallHealth(){
			if(healthTime >= healthCheck){
				items.getHealthBar().update(eHealthChanges.RestingDebrisGradual.getDelta());
				healthTime = 0;
			}
		}
		
		public int getTimeElapsed(){
			return timeElapsed;
		}
		

	}
	
	//At (slightly) random intervals spawn powers, only should be initialized once!!
	public class spawnPowers implements ActionListener, Serializable {
		public int timePassed = 0;
		public int spawnTimePowers;
		public int aveTime = 10000;
		final public int rTime = 500;
		private boolean rebuildMode= false;
		private boolean removeMode = false;
		private Helper removeHelper;
		private Tool rebuildTool;

		public spawnPowers(){
			resetTimer();
		}

		public Powers newPower(){
			Random r = new Random();
			Powers p;
			if(r.nextInt()%2==0){
				p = newPower(new Rebuild());
			}
			else{
				p = newPower(new Remove());
			}
			
			return p;
		}
		
		public Powers newPower(Powers p){
			Random r = new Random();
			//generate initial position;
			int randomx = r.nextInt(500)+150;
			System.out.println("power:" + randomx);
			int xPos = MovementController.getStart(randomx);
			
			if(p instanceof Rebuild){
				System.out.println("rebuild");
				p.updatePos(xPos,0);
			}
			else{
				System.out.println("remove");
				p.updatePos(xPos,0);
			}
			p.setVertex(xPos);
			
			return p;
		}
		
		
		public void resetTimer(){
			Random r = new Random();
			spawnTimePowers = r.nextInt(rTime) + aveTime - rTime/2;
			timePassed = 0;
		}
		
		//Unimplemented methods
		public void quickSpawn(){
			items.addPower(newPower());
		}
		public void quickSpawnRebuild(){
			items.addPower(newPower(new Rebuild()));
		}
		public void quickSpawnRemove(){
			items.addPower(newPower(new Remove()));
		}

		public void move(Powers p){
			MovementController.move(p);
			
			if(collision.checkCollision(p) && !thisGame.choosingThrow && !thisGame.initiatingPowerUp){
				p.catching();
				thisGame.caughtSetup(p);
				p.updatePos(mainPlayer.getPosX()+mainPlayer.getWidth()/2 - p.getWidth()/2, mainPlayer.getPosY()-p.getHeight());
			}
		}
		
		public void spawnTimeReached(){
			System.out.println("new power spawn");
			if(!items.getRestingDebris().isEmpty()){
				if( items.emptyBarriers() >=4){	
					items.addPower(newPower());
				}
				else{
					this.quickSpawnRemove();
				}
			}
			else{
				if(items.emptyBarriers() >=4){
					this.quickSpawnRebuild();
				}
			}
			
			resetTimer();
		}
		
		public void rebuildAction(){
			rebuildTool.addTime(floatDelay);
			if(rebuildTool.doneBuilding()){
				items.deleteRebuildTool();
				rebuildMode = false;
			}
		}
		
		public void removeAction(){
			MovementController.walkMove(removeHelper);
			removeHelper.addTime(floatDelay);
			if(removeHelper.getState() == eHelperState.WALKING_OFF && removeHelper.getTimeInStage()==0){
				removeHelper.getPower().power(items);
			}
			if(removeHelper.getState()==eHelperState.VOID){
				items.deleteRemoveHelper();
				removeMode = false;
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			for(Powers p : items.getAllPowers()){
				//make each item float
				if(p.getState()==eFloaterState.MOVING){
					move(p);
				}
				else if(p.getState()==eFloaterState.INITIATED){
					if(p instanceof Rebuild){
						if(!rebuildMode){
							p.power(getItems());
							rebuildTool = new Tool(((Rebuild) p).getBarriersToRebuild());
							items.setRebuildTool(rebuildTool);
							rebuildMode = true;
						}
					}
					else if(p instanceof Remove){
						if(!removeMode){
							removeHelper = new Helper((Remove) p);
							removeHelper.setFinalY(items.getAllDebris());
							items.setRemoveHelper(removeHelper);
							removeMode = true;
						}

					}
					ScoreController.scorePower();
				}
				
			}
			//Removes power up if hits coast or powerup was initiated
			Iterator poweritr = items.getAllPowers().iterator();
			while(poweritr.hasNext()){
				Powers p = (Powers)poweritr.next();
				if(p.getState() == eFloaterState.INITIATED || p.getState() == eFloaterState.RESTING){
					poweritr.remove();
				}
			}
			
			if(removeMode){
				removeAction();
			}
			
			if(rebuildMode){
				rebuildAction();
			}
			//if the timer goes off then add another power at the top
			if(timePassed >= spawnTimePowers){
				spawnTimeReached();
			}
			timePassed+=floatDelay;
		}

		public void updateAveTime(int newTime){
			aveTime = newTime;
		}

	}
	
	public class walkController implements ActionListener, Serializable {
		private int timePassed = 0;
		
		
		@Override
		public void actionPerformed(ActionEvent e){
			
		}
	}
	
	public class coastErosion implements ActionListener, Serializable {
		private Coast coast;
		public int timePassed;
		public int erosionTime;
		public int aveTime;
		final public int rTime = 5000;
		
		public coastErosion(Coast c){
			coast = c;
			Random r = new Random();
			//assumes erosion rate in Coast is in milliseconds
			aveTime = (int) c.getErosionRate();
			erosionTime = r.nextInt(rTime) + aveTime - rTime/2;
			timePassed = 0;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(coast.isProtected()){
				//System.out.println("The coast is protected at: " + coast.getPosX());
				return;
			}
			else{
				if(timePassed >= erosionTime){
					coast.erode();
					timePassed = 0;
					items.getHealthBar().update(eHealthChanges.CoastEroded.getDelta());
				}
				else{
					timePassed+=erodeDelay;
				}
			}
		}	
	}
	
	public class barrierErosion implements ActionListener, Serializable {
		private Barriers barrier;
		public int timePassed = 0;
		public int erosionTime;
		public int aveTime;
		final public int rTime = 2000;
		
		public barrierErosion(Barriers b){
			barrier = b;
		}
		public void newTime(){
			Random r = new Random();
			aveTime = barrier.getDecayTime();
			erosionTime = r.nextInt(rTime) + aveTime - rTime/2;
			System.out.println("new erosion: " + erosionTime);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(barrier.getType()==eBarrierType.EMPTY){
				return;
			}

			if(timePassed == 0){
				newTime();
			}
			if(timePassed >= erosionTime){
				barrier.erode();
				timePassed = 0;
				items.getHealthBar().update(eHealthChanges.BarrierFallen.getDelta());
			}
			else if(barrier.getState()!=eBarrierState.ONE_HIT & timePassed >= (1/2)*erosionTime){
				barrier.erodeHalf();
				timePassed+=erodeDelay;
			}
			else{
				timePassed+=erodeDelay;
			}
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
			getMainPlayer().updatePosX(getMainPlayer().getPosX() + moveSize);
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
			getMainPlayer().updatePosY(getMainPlayer().getPosY()+moveSize);
		}
		
	}
	
	//When the player catches Debris, left and right keys bind with this action
	public class ThrowChoice extends AbstractAction{
		private eThrowDirection dir;
		private Debris caughtDebris;
		
		public ThrowChoice(eThrowDirection dir, Debris d){
			this.dir = dir;
			this.caughtDebris = d;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Change throw dir" + dir.name());
			caughtDebris.setThrowDirection(dir);
		}
		
	}
	
	//Action to be bound with the enter key when a piece of Debris is caught. Pressing enter releases the Debris
	public class ThrowChosen extends AbstractAction{

		private Debris caughtDebris;
		public ThrowChosen(Debris d){
			this.caughtDebris = d;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			caughtDebris.setState(eFloaterState.THROWING);
			items.getPlayer().setState(ePlayerState.Idle);
			thisGame.thrownSetup();
		}
		
	}
	public class PowerInitiate extends AbstractAction{

		private Powers caughtPower;
		public PowerInitiate(Powers p){
			this.caughtPower = p;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			caughtPower.setState(eFloaterState.INITIATED);
			thisGame.initiatedPowerSetup();
			items.getPlayer().setState(ePlayerState.Idle);
		}
		
	}
	
	public Barriers collision(Rectangle r) {
		/*
		if ( ((x2 <= (x1+w1)) && (x2 >= x1)) //checking x left edge collisions
				|| (((x2+w2) <= (x1+w1)) && ((x2+w2) >= x1)) //checking x right edge collisions
				|| ((y2 <= (y1+h1)) && (y2 >= y1)) //checking y top edge collisions
				|| (((y2+h2) <= (y1+h1)) && ((y2+h2) >= y1)) ) //checking y bottom edge collisions*/
		for (Barriers b : this.items.getAllBarriers()) {
			Rectangle barrier = new Rectangle(b.getPosX(), b.getPosY(), b.getWidth(), b.getHeight());
			if (barrier.intersects(r))
				return b;
		}

		return null;
	}
	
	public void setBarrierType(Barriers barr, eBarrierType t) {
		//goes through list of barriers and changes the one with the matching coords to type t
		for (Barriers b : this.items.getAllBarriers()) {
			if((barr.getPosX()>=b.getPosX() && barr.getPosX()<=b.getPosX()+b.getWidth()) && (barr.getPosY()>=b.getPosY() && barr.getPosY()<=b.getPosY()+b.getHeight())){
				System.out.println("inside space");
				b.setType(t);
			}
			if (barr.getPosX() == b.getPosX()) { //"match" 
				System.out.println("set barrier type");
				b.setType(t);
			}
		}
	}
	
	/**
	 * Returns whether a Barrier barr has collided with any other Barrier on the screen
	 * @param barr
	 * @return
	 */
	public Barriers emptyBarrierCollision(Barriers barr) {
		//checks if barr collided with any of the barriers and if it is empty
		for (Barriers b : this.items.getAllBarriers()) {
			if ((Collisions.checkCollision(b, barr) && (b.getType() == eBarrierType.EMPTY))) {
				//System.out.println("empty barrier collide");
				return b;
			}
		}
		return null;
	}

	public Player getMainPlayer() {
		return mainPlayer;
	}


	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}
	
	public void setDifficulty(eDifficulty diff){
		difficulty = diff;
	}
	
	
	public class endGameAction extends AbstractAction{

		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
			c1.last(EstuaryGame.getCards());
		}
		
	}
	
	public class SerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Serialize to file
			try
			{
				System.out.print("writing");
				FileOutputStream fos = new FileOutputStream("testSerialize.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				//TODO
				
				//oos.writeObject(items);
				//Write the player
				oos.writeObject(mainPlayer);
				//Write the ActiveItems
				oos.writeObject(items);
				//Write the rest
				oos.writeObject(debrisFloating);
				oos.writeObject(powersFloating);
				oos.writeObject(choosingThrow);
				oos.writeObject(initiatingPowerUp);
				oos.writeObject(allTimers);
				oos.writeObject(theBigTimer);
				oos.writeObject(paintTimer);
				oos.writeObject(timeElapsed);
				oos.writeObject(debrisMover);
				oos.writeObject(powerMover);
				
				oos.close();
			}
			catch (Exception ex)
			{
				System.out.println("Exception thrown during test: " + ex.toString());
				ex.printStackTrace();
			}

		}


	}

	public class ReadSerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Serialize to file
			try
			{
				System.out.print("reading");
				FileInputStream fis = new FileInputStream("testSerialize.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);

				//TODO: ois.readObject()
				//Write the player
				mainPlayer = (Player) ois.readObject();
				//Write the ActiveItems
				items = (ActiveItems) ois.readObject();
				//Write the rest
				debrisFloating = (Timer) ois.readObject();
				powersFloating = (Timer) ois.readObject();
				
				choosingThrow = (boolean) ois.readObject();
				initiatingPowerUp = (boolean) ois.readObject();
				
				ArrayList<Timer> readObject = (ArrayList<Timer>) ois.readObject();
				allTimers = readObject;
				
				theBigTimer = (Timer) ois.readObject();
				paintTimer = (mainTimer) ois.readObject();
				
				timeElapsed = (int) ois.readObject();
				
				debrisMover = (spawnDebris) ois.readObject();
				powerMover = (spawnPowers) ois.readObject();			

				ois.close();
			}
			catch (Exception ex)
			{
				System.out.println("Exception thrown during test: " + ex.toString());
			}
		}
	}
	
	public class CleanUpSerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Serialize to file
			try
			{
				//Clean up the file
				new File("testSerialize.ser").delete();
			}
			catch (Exception ex)
			{
				System.out.println("Exception thrown during test: " + ex.toString());
			}
		}


	}


}
