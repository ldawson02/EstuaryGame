package controller;

import model.*;
import view.*;

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
import eNums.eScreenTimerState;
import eNums.eThrowDirection;

public class GameController {

	//the big shebang
	private EstuaryGame mainGame;
	Player mainPlayer;
	private ActiveItems items = new ActiveItems();
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
	final private int erodeDelay = 1;//TODO
	Timer coastRErosion;
	Timer coastLErosion;
	private GameController thisGame = this;
	
	final private int paintDelay = 30;
	Timer theBigTimer;
	private int timeElapsed = 0;
	
	spawnDebris debrisMover;
	spawnPowers powerMover;
	erosion RcoastMover;
	erosion LcoastMover;
	
	private static int bWidth = 40;
	private static int bHeight = 20;
	public static Rectangle wallSpawn = new Rectangle(Barriers.getLeftEdge(), 390, bWidth, bHeight);
	public static Rectangle gabionsSpawn = new Rectangle(Barriers.getRightEdge(), 390, bWidth, bHeight);
	public static Rectangle temp;
	
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

	public void normalKeyBind(){
		mainGame.bindKeyWith("x.left", KeyStroke.getKeyStroke("LEFT"), leftAct);
		mainGame.bindKeyWith("x.right", KeyStroke.getKeyStroke("RIGHT"), rightAct);
		mainGame.bindKeyWith("x.up", KeyStroke.getKeyStroke("UP"), upAct);
		mainGame.bindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"), downAct);
	}
	public void setup(){
		//Start the paint timer
		theBigTimer = new Timer(paintDelay, new mainTimer());
		theBigTimer.start();
		
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
		
		//maybe also have an action for building gabion, like pressing g
		mainGame.bindKeyWith("gabionBuild", KeyStroke.getKeyStroke("g"), new gabionAct());
		mainGame.bindKeyWith("wallBuild", KeyStroke.getKeyStroke("h"), new wallAct());
		
		/*
		JFrame f = new JFrame();
		MouseController mouse = new MouseController();
		f.addMouseListener(mouse);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
		
		ArrayList<Barriers> left = Barriers.setUpLeftCoast();
		for (Barriers b : left) {
			items.addBarrier(b);
		}
		ArrayList<Barriers> right = Barriers.setUpRightCoast();
		for (Barriers b : right) {
			items.addBarrier(b);
		}
		
		//Create the initial walls
		/*
		for (int i = 0; i < 5; i++) {
			items.addBarrier(new Barriers(20+50*i));
		}
		for (int i = 0; i < 5; i++) {
			items.addBarrier(new Barriers(540+50*i));
		}*/
	
		items.getAllBarriers().get(1).setType(eBarrierType.Wall);
		items.getAllBarriers().get(7).setType(eBarrierType.Gabion);
		
		
		/*
		//Create the initial walls
		//TODO: this is gross I'll make it look better later
		//Random r = new Random();
		int xPos = items.getCoastL().getBarrierSpaces().get(3).getPosX();
		int yPos = items.getCoastL().getBarrierSpaces().get(3).getPosY();
		items.addBarrier(new Wall(xPos,yPos));
		xPos = items.getCoastL().getBarrierSpaces().get(1).getPosX();
		yPos = items.getCoastL().getBarrierSpaces().get(1).getPosY();
		items.addBarrier(new Wall(xPos,yPos));
		xPos = items.getCoastR().getBarrierSpaces().get(2).getPosX();
		yPos = items.getCoastR().getBarrierSpaces().get(2).getPosY();
		items.addBarrier(new Wall(xPos,yPos));
		xPos = items.getCoastR().getBarrierSpaces().get(4).getPosX();
		yPos = items.getCoastR().getBarrierSpaces().get(4).getPosY();
		items.addBarrier(new Wall(xPos,yPos));*/
		
		//Add health bar!!
		items.addHealthBar(new HealthBar());
		
		//Create the bins
		items.getTrashBin().updatePos(50, 150);
		items.getRecycleBin().updatePos(700, 150);
		
		//mainGame.imageLoad();
		mainGame.initTitleScreen();
		startGame();
	}
	
	public void tearDown(){
		
	}
	
	public void startGame(){
		//set up automatic movements!
		//	->create timer for debris
		//Turn on the screen timer!
		items.addScreenTimer(new ScreenTimer());
		items.getScreenTimer().start();
		
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
	
	
	public void imageLoad(){
		//TODO: that method currently returns a completely empty instance
		library = ImageLibrary.loadLibrary();
	}
	
	public void caughtSetup(Debris d){
		this.choosingThrow = true;
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
		mainGame.bindKeyWith("initiatePowerUp", KeyStroke.getKeyStroke("ENTER"), powerAction);
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
	
		
		public spawnDebris(){
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
			d.setController(thisGame); //Bin stuff
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
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//if the timer goes off then add another piece of debris at the top
			if(timePassed >= spawnTimeDebris){
				items.addDebris(newDebris());
				resetTimer();
			}
			//might want to put this for loop in its own class in the Controller
			for(Debris d : items.getAllDebris()){
				//make each item float
				if(d.getState()==eFloaterState.MOVING){
					MovementController.move(d);
					if(!thisGame.choosingThrow && collision.checkCollision(d) && !thisGame.initiatingPowerUp){
						//sets the Debris state to Lifted
						d.catching();
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
						System.out.print("\nBin hit this round and");
						if (d.getCorrectBin()) {
							System.out.print(" bin was correct.\n");
							items.getHealthBar().update(eHealthChanges.CorrectBin.getDelta());
						}
						else {
							System.out.print(" bin was incorrect.\n");
							MovementController.wrongBinMove(d);
							items.getHealthBar().update(eHealthChanges.IncorrectBin.getDelta());
						}
					}
					//If the debris hit the wrong bin it should go back to the coast
				}
				
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
			mainGame.repaint();
			if(items.getScreenTimer().getState()==eScreenTimerState.ON){
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
						//Rebuilding of coast
					}
					else{
						//Removes all Debris from coast
						items.removeAllRestingDebris();
						items.getHealthBar().update(eHealthChanges.CoastDebrisRemoved.getDelta());
						
					}
				}
				
			}
			//Removes power up if hits coast
			Iterator poweritr = items.getAllPowers().iterator();
			while(poweritr.hasNext()){
				Powers p = (Powers)poweritr.next();
				if(p.getState() == eFloaterState.INITIATED){
					System.out.println("Power initiated");
					poweritr.remove();
				}
				if (p.getState() == eFloaterState.RESTING) {
					System.out.println("Power hit coast");
					poweritr.remove();
				}
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
		for (Barriers b : this.items.getAllBarriers()) {
			if (barr.getPosX() == b.getPosX()) //"match"
				b.setType(t);
		}
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
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

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

}
