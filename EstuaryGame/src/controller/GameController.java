
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import controller.*;
import eNums.eBarrierType;
import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.eHealthChanges;
import eNums.ePlayerState;
import eNums.eScreenTimerState;
import eNums.eThrowDirection;
import eNums.eTutorialState;

/**
 * this class is the game controller
 * 
 *
 */
public class GameController {

	//the big shebang
	private EstuaryGame mainGame;
	Player mainPlayer;
	private static ActiveItems items = new ActiveItems();
	private ImageLibrary library;
	Action leftAct;
	Action rightAct;
	Action upAct;
	Action downAct;
	Action caughtLeftAct;
	Action caughtRightAct;
	Action throwAct;
	//Makes sure you can only catch one Floater at a time
	private boolean choosingThrow = false;
	private boolean initiatingPowerUp = false;
	final private int floatDelay = 100; //TODO
	Timer debrisFloating;
	Timer powersFloating;
	final private int erodeDelay = 100;//TODO
	
	private GameController thisGame = this;
	private ArrayList<Timer> allTimers = new ArrayList<Timer>();
	private ArrayList<Timer> tutorialTimers = new ArrayList<Timer>();
	
	final private int paintDelay = 30;
	Timer theBigTimer;
	private int timeElapsed = 0;
	
	spawnDebris debrisMover;
	spawnPowers powerMover;
	//erosion RcoastMover;
	//erosion LcoastMover;
	
	private boolean gameEnd;
	public boolean tutorialMode = false;
	public Tutorial tutorial;
	
	Collisions collision = new Collisions();
		
	/**
	 * construct the game controller
	 * @param mainGame
	 */
	public GameController(EstuaryGame mainGame){
		setMainGame(mainGame);
		setup(true);
	}
	/**
	 * construct the game controller
	 * @param t
	 */
	public GameController(Tutorial t){
		tutorial = t;
		tutorialMode = true;
		setup(false);
	}
	
	/**
	 * main game getter and setter
	 * @return
	 */
	public EstuaryGame getMainGame() {
		return mainGame;
	}

	public void setMainGame(EstuaryGame mainGame) {
		this.mainGame = mainGame;
	}

	/**
	 * get and set the items
	 * @return items
	 */
	public static ActiveItems getItems() {
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
	
	/**
	 * get spawn debris
	 * @return
	 */
	public spawnDebris getSpawnDebris() {
		return debrisMover;
	}

	/**
	 * set up the keyboard using left, right, up and down
	 */
	public void normalKeyBind(){
		mainGame.bindKeyWith("x.left", KeyStroke.getKeyStroke("LEFT"), leftAct);
		mainGame.bindKeyWith("x.right", KeyStroke.getKeyStroke("RIGHT"), rightAct);
		mainGame.bindKeyWith("x.up", KeyStroke.getKeyStroke("UP"), upAct);
		mainGame.bindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"), downAct);
	}

	/**
	 * we set up the game, add the health bar, screen timer, player, bind the keys and other things listed in the comment
	 * @param game
	 */
	public void setup(boolean game){
		//Add health bar!!
		items.addHealthBar(new HealthBar());
		
		//Add the screen Timer
		items.addScreenTimer(new ScreenTimer());
		
		//Create the player
		mainPlayer = new Player();
		items.setMainPlayer(mainPlayer);
		mainPlayer.updatePos(380, 280); //These are hard coded
		
		//Connect the Collision controller w the Player
		collision.setPlayer(mainPlayer);
		
		//bind the keys
		leftAct = new HAction(-1 * mainPlayer.speed);
		rightAct = new HAction(1 * mainPlayer.speed);
		upAct = new VAction(-1 * mainPlayer.speed);
		downAct = new VAction(1 * mainPlayer.speed);
		normalKeyBind();
		
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
		items.getAllBarriers().get(4).setType(eBarrierType.Wall);
		items.getAllBarriers().get(6).setType(eBarrierType.Wall);
		items.getAllBarriers().get(7).setType(eBarrierType.Gabion);
		
		
		//Create the bins
		items.getTrashBin().updatePos(50, 150);
		items.getRecycleBin().updatePos(700, 150);
		

		//Start the paint timer
		theBigTimer = new Timer(paintDelay, new mainTimer());
		theBigTimer.start();
		allTimers.add(theBigTimer);

		
		//mainGame.imageLoad();
		//mainGame.initTitleScreen();
		if(game){
			startGame();
		}
		else{
			startTutorial();
		}
	}
	//TODO
	public void tearDown(){
		mainGame.unbindKeyWith("GotoEndGame", KeyStroke.getKeyStroke("ENTER"));
	}
	
	/**
	 * start the game we set up the timer for debris and turn on the screen timer
	 */
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
	/**
	 * set up the erosion, the barrier will set the btimer and erosion timer
	 */
	public void erosionSetup(){
		barrierErosion bErode; int i = 0;
		for(Barriers b: items.getAllBarriers()){
			bErode = new barrierErosion(b);
			b.setbTimer(new Timer(this.erodeDelay, bErode));
			b.geterosionTimer().start();
			allTimers.add(b.geterosionTimer());
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
	//TODO
	public void startTutorial(){
		//probably needs to be a timer just for tutorial
		debrisMover = new spawnDebris(true);
		powerMover = new spawnPowers();
		
		debrisFloating = new Timer(floatDelay, debrisMover);
		debrisFloating.start();
		tutorialTimers.add(debrisFloating);

		
		//Add power timer, but don't start it yet
		powersFloating = new Timer(floatDelay, powerMover);
		tutorialTimers.add(powersFloating);
	}
	/**
	 * one the game overs, the timer stops and game act ends.
	 */
	public void gameOver(){
		stopTimers();
		Action endGameAct = new endGameAction();
		mainGame.bindKeyWith("GotoEndGame", KeyStroke.getKeyStroke("ENTER"), endGameAct);
		
	}
	
	/**
	 * stop the timer
	 */
	public void stopTimers(){
		for(Timer t : allTimers){
			t.stop();
		}
	}
	/**
	 * load the images
	 */
	public void imageLoad(){
		//TODO: that method currently returns a completely empty instance
		library = ImageLibrary.loadLibrary();
	}
	
	/**
	 * set up the caught
	 * @param d
	 */
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
		mainGame.unbindKeyWith("x.up", KeyStroke.getKeyStroke("UP"));
		mainGame.unbindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"));
		mainGame.unbindKeyWith("x.left", KeyStroke.getKeyStroke("LEFT"));
		mainGame.unbindKeyWith("x.right", KeyStroke.getKeyStroke("RIGHT"));
		
		
	}
	/**
	 * see comment
	 */
	public void thrownSetup(){
		//Return the keys to their original state, allow Player to move
		normalKeyBind();
		mainGame.unbindKeyWith("throwDebris", KeyStroke.getKeyStroke("ENTER"));
		this.choosingThrow = false;
	}
	/**
	 * initiate the power
	 */
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
	public class spawnDebris implements ActionListener{
		public int timePassed = 0;
		//The time after which debris should spawn again (changes every time respawned)
		public int spawnTimeDebris;
		//The average length of time based on difficulty
		public int aveTime = 3000;
		//The limit to the random distributions range in milliseconds (AKA +- rTime/2)
		final public int rTime = 500;
	
		
		/**
		 * construct the spawndebris
		 */
		public spawnDebris(){
			items.addDebris(newDebris());
			resetTimer();
		}
		
		public spawnDebris(boolean t){
			items.addDebris(newDebris());
			resetTimer();
		}
		
		/**
		 * returns a new randomly generated piece of Debris
		 * @return d
		 */
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
			d.setController(thisGame); //Bin stuff
			d.updatePos(xPos, 0);
			d.setVertex(xPos);
			return d;
		}
		
		/**
		 * resets the spawnTimeDebris to a new randomly generated number (within range)
		 */
		public void resetTimer(){
			Random r = new Random();
			spawnTimeDebris = r.nextInt(rTime) + aveTime - rTime/2;
			timePassed = 0;
		}
		
		/**
		 * might want to put this for loop in its own class in the Controller and make each item float,
		 * also set the debris state to lifted and more detail are listed in the comment line
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			ArrayList<Debris> toDelete = new ArrayList<Debris>();
			//might want to put this for loop in its own class in the Controller
			for(Debris d : items.getAllDebris()){
				//make each item float
				if(d.getState()==eFloaterState.MOVING){
					MovementController.move(d);
					if(!thisGame.choosingThrow && collision.checkCollision(d) && !thisGame.initiatingPowerUp){
						//sets the Debris state to Lifted
						d.catching();
						if(tutorialMode){
							tutorial.setSpotlight(false);
						}
						//sequence of events for a caught Debris initiated
						thisGame.caughtSetup(d);
						//Move the trash to above the Player's head
						d.updatePos(mainPlayer.getPosX()+mainPlayer.getWidth()/2 - d.getWidth()/2, mainPlayer.getPosY()-d.getHeight());
					}
					
					//If the debris hit the coast this round, decrement health
					if (d.getState() == eFloaterState.RESTING) {
						System.out.println("Debris hit coast");
						items.getHealthBar().update(eHealthChanges.DebrisHitCoast.getDelta());
					}
				}
				else if(d.getState()==eFloaterState.THROWING){
					//This should be a sequence like move()
					//Function could return true or false to indicate it it's hit the Bin yet, then initiate next sequence
					MovementController.Throw(d, d.getBin());

					//Update the healthbar if it hit on this round
					if (d.getState() == eFloaterState.HITBIN) {
						if(tutorialMode){
							tutorial.nextState();
						}
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
				
			}
			//Now delete any debris that hit
			for(Debris del : toDelete){
				items.removeDebris(del);
			}
			
			if(tutorialMode){
				//Don't add any more debris if it's tutorial mode
				return;
			}
			
			//if the timer goes off then add another piece of debris at the top
			if(timePassed >= spawnTimeDebris){
				items.addDebris(newDebris());
				resetTimer();
			}
			
			timePassed+=floatDelay;
		}
		
		/**
		 * update the average time
		 * @param newTime
		 */
		public void updateAveTime(int newTime){
			aveTime = newTime;
		}
		
	}
	
	/**
	 * The point of this class is to create a timer that calls paint
	 * 
	 *
	 */
	public class mainTimer implements ActionListener{
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
		/**
		 * check the timer and set up the storm time, health time, scoring time and elapsed time
		 */
		public void actionPerformed(ActionEvent e) {
			mainGame.repaint();
			if(tutorialMode){
				return;
			}
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
		/**
		 * set up the real storm time and call storm if stormchecked is true.
		 */
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
		/**
		 * call storm if storm time larger than real time
		 * @param time
		 */
		public void callStorm(int time){
			if (stormTime >= realStormTime) {
				Storm.stormEffects(items, debrisMover);
				Storm.setAppeared(true);
			}
		}
		
		/**
		 * check the score time if it is larger than score check, the controller set the score health.
		 */
		public void checkScoreTime(){
			if(scoringTime >= scoreCheck){
				ScoreController.scoreHealth(items.getHealthBar().getHealth());
				scoringTime = 0;
			}
		}
		/**
		 * TODO
		 */
		public void checkOverallHealth(){
			if(healthTime >= healthCheck){
				items.getHealthBar().update(eHealthChanges.RestingDebrisGradual.getDelta());
				healthTime = 0;
			}
		}
		/**
		 * time elaperd getter
		 * @return
		 */
		public int getTimeElapsed(){
			return timeElapsed;
		}
		
		//TODO
		public void tutorialTimer(){
			if(tutorial.getTimeInStage() > delaySpotlight){
				tutorial.setSpotlight(true);
			}
			if(tutorial.getState()==eTutorialState.DEBRIS){
				//check if you need to add another debris
				int distance = mainGame.getScreenY();
				for(Debris d : GameController.getItems().getAllDebris()){
					if(d.getPosY() < distance){
						distance = d.getPosY();
					}
				}
				if(distance > mainGame.getScreenY()/4){
					debrisMover.newDebris();
				}
				
			}
			tutorial.addTime(paintDelay);
		}
		

	}
	
	/**
	 * At (slightly) random intervals spawn powers, only should be initialized once!!
	 *
	 *
	 */
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
			int randomx = r.nextInt(500)+150;
			System.out.println("power:" + randomx);
			int xPos = MovementController.getStart(randomx);
			
			int ptype = r.nextInt() % 2;
			Powers p;
			if(ptype==0){
				System.out.println("rebuild");
				p = new Rebuild(xPos,0);
			}
			else{
				System.out.println("remove");
				p = new Remove(xPos,0);
			}
			p.setVertex(xPos);
			
			return p;

		}
		
		/**
		 * reset the timer
		 */
		public void resetTimer(){
			Random r = new Random();
			spawnTimePowers = r.nextInt(rTime) + aveTime - rTime/2;
			timePassed = 0;
		}
		
		/**
		 * Unimplemented methods
		 */
		public void quickSpawn(){
			items.addPower(newPower());
		}
		public void quickSpawnRebuild(){}
		public void quickSpawnRemove(){}

		@Override
		public void actionPerformed(ActionEvent e) {
			//if the timer goes off then add another power at the top
			if(timePassed >= spawnTimePowers){
				System.out.println("new power spawn !!!!!!!!!!!!!!!");
				items.addPower(newPower());
				resetTimer();
			}
			for(Powers p : items.getAllPowers()){
				//make each item float
				if(p.getState()==eFloaterState.MOVING){
					MovementController.move(p);
			
					if(collision.checkCollision(p) && !thisGame.choosingThrow && !thisGame.initiatingPowerUp){
						p.catching();
						thisGame.caughtSetup(p);
						p.updatePos(mainPlayer.getPosX()+mainPlayer.getWidth()/2 - p.getWidth()/2, mainPlayer.getPosY()-p.getHeight());
					}
					
				}
				else if(p.getState()==eFloaterState.INITIATED){
					if(p instanceof Rebuild){	
						((Rebuild) p).power(items.getAllBarriers());
						items.getHealthBar().update(eHealthChanges.CoastRebuilt.getDelta());
					}
					else{
						//Removes all Debris from coast
						//TODO: remove up to a certain amount of debris
						items.removeAllRestingDebris();
						items.getHealthBar().update(eHealthChanges.CoastDebrisRemoved.getDelta());
						
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
			timePassed+=floatDelay;
		}

		public void updateAveTime(int newTime){
			aveTime = newTime;
		}

	}
	
/**	//At (slightly) random intervals erode stuff
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
*/
	/**
	 * the coast erosion has a coast, and timer
	 *
	 */
	public class coastErosion implements ActionListener{
		private Coast coast;
		public int timePassed;
		public int erosionTime;
		public int aveTime;
		final public int rTime = 5000;
		/**
		 * construct the coast erosion
		 * @param c
		 */
		public coastErosion(Coast c){
			coast = c;
			Random r = new Random();
			//assumes erosion rate in Coast is in milliseconds
			aveTime = (int) c.getErosionRate();
			erosionTime = r.nextInt(rTime) + aveTime - rTime/2;
			timePassed =0;
		}
		
		
		@Override
		/**
		 * if time passed larger than erosion time, then the coast erode, otherwise reset the time passed.
		 */
		public void actionPerformed(ActionEvent e) {
			if(coast.isProtected()){
				//System.out.println("The coast is protected at: " + coast.getPosX());
				return;
			}
			else{
				if(timePassed >= erosionTime){
					coast.erode();
					timePassed = 0;
				}
				else{
					timePassed+=erodeDelay;
				}
			}
		}
		
	}
	
	/**
	 * 
	 * this class has to have a barrier, erosion time, average time, and time passed variable
	 *
	 */
	public class barrierErosion implements ActionListener{
		private Barriers barrier;
		public int timePassed;
		public int erosionTime;
		public int aveTime;
		final public int rTime = 2000;
		
		/**
		 * construct the barrier erosion
		 * @param b
		 */
		public barrierErosion(Barriers b){
			barrier = b;
		}
		/**
		 * set up the variable time
		 */
		public void newTime(){
			Random r = new Random();
			aveTime = barrier.getDecayTime();
			erosionTime = r.nextInt(rTime) + aveTime - rTime/2;
			System.out.println("new erosion: " + erosionTime);
			
		}
		@Override
		/**
		 * if barrier's type is empty, then stops, and time passed larger than erosion time, the barrier will erode.
		 * 
		 */
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
		
		/**
		 * update the speed
		 * @param jump
		 */
		public void updateSpeed(int jump){
			this.moveSize = jump;
		}
		
		/**
		 * set up the main player's updated position
		 */
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
		/**
		 * update the speed 
		 * @param jump
		 */
		public void updateSpeed(int jump){
			this.moveSize = jump;
		}
		
		@Override
		/**
		 * update the main player's position
		 */
		public void actionPerformed(ActionEvent e) {
			mainPlayer.updatePosY(mainPlayer.getPosY()+moveSize);
		}
		
	}
	
	/**
	 * When the player catches Debris, left and right keys bind with this action
	 *
	 */
	public class ThrowChoice extends AbstractAction{
		private eThrowDirection dir;
		private Debris caughtDebris;
		
		/**
		 * consruct the throw choice
		 * @param dir
		 * @param d
		 */
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
	
	/**
	 * Action to be bound with the enter key when a piece of Debris is caught. Pressing enter releases the Debri
	 *
	 */
	public class ThrowChosen extends AbstractAction{

		private Debris caughtDebris;
		public ThrowChosen(Debris d){
			this.caughtDebris = d;
		}
		@Override
		/**
		 * set up the item's state and caught debris's state
		 */
		public void actionPerformed(ActionEvent e) {
			caughtDebris.setState(eFloaterState.THROWING);
			items.getPlayer().setState(ePlayerState.Idle);
			thisGame.thrownSetup();
		}
		
	}
	public class PowerInitiate extends AbstractAction{

		private Powers caughtPower;
		/**
		 * construct powerinitiate
		 * @param p
		 */
		public PowerInitiate(Powers p){
			this.caughtPower = p;
		}
		@Override
		/**
		 * the basic set up for power
		 */
		public void actionPerformed(ActionEvent e) {
			caughtPower.setState(eFloaterState.INITIATED);
			thisGame.initiatedPowerSetup();
			items.getPlayer().setState(ePlayerState.Idle);
		}
		
	}
	
	/**
	 * to see if barriers has collision or not.
	 * @param r
	 * @return
	 */
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
	/**
	 * goes through list of barriers and changes the one with the matching coords to type t
	 * @param barr
	 * @param t
	 */
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
	 * checks if barr collided with any of the barriers and if it is empty
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

	public class MouseController extends JPanel implements MouseListener, MouseMotionListener {

		boolean dragging = false;
		Rectangle temp; //the thing being dragged
		private eBarrierType type; 

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(type + " clicked");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			/*
			Point p = new Point(e.getX(), e.getY());
			if (wallSpawn.contains(p)) {
				type = eBarrierType.Wall;
			} else if (gabionsSpawn.contains(p)) {
				type = eBarrierType.Gabion;
			}
			temp = new Rectangle(e.getX(), e.getY(), bWidth, bHeight); 
			//the temp rectangle made here for dragging
			dragging = true;
			repaint();*/
			System.out.println(e.getClickCount());
			System.out.println(type + " pressed");

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (dragging == false)
				return;
			dragging = false;
			Barriers b = collision(temp);
			if (b != null) {  //there was a collision, the temp rectangle selected a barrier space 
								//(do we want it so a new barrier can only be made if the space is empty?)
				if (type == eBarrierType.Wall) {
					setBarrierType(b, eBarrierType.Wall); //set barrier at the coords type to wall 
				}
				else if (type == eBarrierType.Gabion) {
					setBarrierType(b, eBarrierType.Gabion);
				}
			}
			temp = null; //we no longer need this temp rectangle
			repaint();
			// TODO Auto-generated method stub

		}
		
		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragging == false)
				return;
			//update coords of temp rectangle-barrier
			//idea: use barriers for spawns and temp, convert to Rectangle when needed to compare intersections etc.
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
	
	public class endGameAction extends AbstractAction{

		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
			c1.last(EstuaryGame.getCards());
		}
		
	}


}