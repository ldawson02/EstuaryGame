package controller;

import model.*;
import view.*;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

/**
 * The main controller for Estuary Game. This class sets up the game logic and
 * game items, keeps a reference of the game items, and holds the various
 * timers.
 * 
 * @author Lia Dawson
 * @since 12/9/16
 * @version 2.0
 *
 */
public class GameController implements Serializable {

	private static EstuaryGame mainGame;
	private Player mainPlayer;
	private ActiveItems items = new ActiveItems();

	public final static int dimX = 800;
	public final static int dimY = 600;

	// Makes sure you can only catch one Floater at a time
	private boolean choosingThrow = false;
	private boolean initiatingPowerUp = false;
	final private int floatDelay = 100; // TODO
	Timer debrisFloating;
	Timer powersFloating;
	final private int erodeDelay = 100;// TODO

	GameController thisGame = this;
	private ArrayList<Timer> allTimers = new ArrayList<Timer>();
	private ArrayList<Timer> tutorialTimers = new ArrayList<Timer>();

	protected final int paintDelay = 30;
	Timer theBigTimer;
	mainTimer paintTimer;
	private int timeElapsed = 0;

	spawnDebris debrisMover;
	spawnPowers powerMover;

	eDifficulty difficulty = eDifficulty.EASY;

	private boolean gameEnd;
	Collisions collision = new Collisions();

	/**
	 * Construct a game controller that is connected to a view (EstuaryGame).
	 * This constructor automatically calls the setup function to set up the
	 * game for play.
	 * 
	 * @param EstuaryGame
	 *            mainGame
	 */
	public GameController(EstuaryGame mainGame) {
		setMainGame(mainGame);
		setup();
	}

	/**
	 * Get the main game, a public class which allows for access of the view
	 * class (mainGame)
	 * 
	 * @return mainGame;
	 */
	public EstuaryGame getMainGame() {
		return mainGame;
	}

	/**
	 * Set the main game
	 * 
	 * @param mainGame
	 */
	public void setMainGame(EstuaryGame mainGame) {
		this.mainGame = mainGame;
	}

	/**
	 * Get the active items
	 * 
	 * @return items
	 */
	public ActiveItems getItems() {
		return items;
	}

	/**
	 * Set the items
	 * 
	 * @param items
	 */
	public void setItems(ActiveItems items) {
		this.items = items;
	}

	/**
	 * @return the theBigTimer
	 */
	public int getTheBigTimer() {
		return timeElapsed;
	}

	/**
	 * Getter for spawn debris
	 * 
	 * @return
	 */
	public spawnDebris getSpawnDebris() {
		return debrisMover;
	}

	/**
	 * Normal key bind for left right up down and quit.
	 */

	public void normalKeyBind() {
		mainGame.bindKeyWith("left", KeyStroke.getKeyStroke("A"), new HAction(-1 * getMainPlayer().getSpeed()));
		mainGame.bindKeyWith("right", KeyStroke.getKeyStroke("D"), new HAction(1 * getMainPlayer().getSpeed()));
		mainGame.bindKeyWith("up", KeyStroke.getKeyStroke("W"), new VAction(-1 * getMainPlayer().getSpeed()));
		mainGame.bindKeyWith("down", KeyStroke.getKeyStroke("S"), new VAction(1 * getMainPlayer().getSpeed()));
		mainGame.bindKeyWith("left", KeyStroke.getKeyStroke("LEFT"), new HAction(-1 * getMainPlayer().getSpeed()));
		mainGame.bindKeyWith("right", KeyStroke.getKeyStroke("RIGHT"), new HAction(1 * getMainPlayer().getSpeed()));
		mainGame.bindKeyWith("up", KeyStroke.getKeyStroke("UP"), new VAction(-1 * getMainPlayer().getSpeed()));
		mainGame.bindKeyWith("down", KeyStroke.getKeyStroke("DOWN"), new VAction(1 * getMainPlayer().getSpeed()));
		mainGame.bindKeyWith("quit", KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), new quitAction());
	}

	/**
	 * Binds the keys for serialization: 1 is serialize, 2 is read
	 * serialization, 3 is clear serialization
	 */
	public void serializationKeyBind() {
		mainGame.bindKeyWith("save", KeyStroke.getKeyStroke('1'), new SerializeAction());
		mainGame.bindKeyWith("load", KeyStroke.getKeyStroke('2'), new ReadSerializeAction());
		mainGame.bindKeyWith("delete", KeyStroke.getKeyStroke('3'), new CleanUpSerializeAction());
	}

	/**
	 * This function is for the set up the game. It creates and adds: the health
	 * bar, screen timer, player, coast, bins, barriers. It also binds the keys,
	 * sets default values, creates the main timer for the game, and calls the
	 * start game function.
	 */
	public void setup() {
		items.addHealthBar(new HealthBar());

		items.addScreenTimer(new ScreenTimer());

		setMainPlayer(new Player());
		items.setMainPlayer(getMainPlayer());
		getMainPlayer().updatePos(380, 280);

		// Connect the Collision controller w the Player
		collision.setPlayer(getMainPlayer());

		normalKeyBind();

		serializationKeyBind();

		// Reset stuff from last game
		ScoreController.setScore(0);
		Storm.setAppeared(false);

		items.removeAllDebris();
		items.setAllBarriers();
		items.removeAllPowers();

		ArrayList<Barriers> left = Barriers.setUpLeftCoast();
		ArrayList<Coast> leftCoast = Coast.setUpLeftCoast(left);
		for (Barriers b : left) {
			items.addBarrier(b);
		}
		for (Coast c : leftCoast) {
			items.addCoast(c);
		}
		ArrayList<Barriers> right = Barriers.setUpRightCoast();
		ArrayList<Coast> rightCoast = Coast.setUpRightCoast(right);
		for (Barriers b : right) {
			items.addBarrier(b);
		}
		for (Coast c : rightCoast) {
			items.addCoast(c);
		}
		items.getAllBarriers().get(0).setType(eBarrierType.Gabion);
		items.getAllBarriers().get(1).setType(eBarrierType.Wall);
		items.getAllBarriers().get(2).setType(eBarrierType.Wall);
		items.getAllBarriers().get(3).setType(eBarrierType.Wall);
		items.getAllBarriers().get(4).setType(eBarrierType.Gabion);
		items.getAllBarriers().get(6).setType(eBarrierType.Wall);
		items.getAllBarriers().get(7).setType(eBarrierType.Gabion);

		// Create the bins
		items.getTrashBin().updatePos(50, 150);
		items.getRecycleBin().updatePos(700, 150);

		setupDebrisOnCoast();

		setUpPaintTimer();
		allTimers.add(theBigTimer);

		// mainGame.initTitleScreen();
		startGame();
	}

	/**
	 * Set up the debris that initially rests on the coast, and it includes
	 * trash and recycling
	 */
	public void setupDebrisOnCoast() {
		// need to add trash to walls first so that they can be deleted
		ArrayList<Debris> toAdd = new ArrayList<Debris>();
		toAdd.add(new Debris(36, 290));
		toAdd.add(new Debris(45, 230));
		toAdd.add(new Debris(46, 320));
		toAdd.add(new Debris(738, 243));
		toAdd.add(new Debris(741, 285));
		int i = 0;
		for (Debris d : toAdd) {
			d.setState(eFloaterState.RESTING);
			if (i % 2 == 0) {
				d.setType(eDebrisType.TRASH);
			} else {
				d.setType(eDebrisType.RECYCLING);
			}
			getItems().addDebris(d);
			d.setBins(items.getRecycleBin(), items.getTrashBin());
			i++;
		}
	}

	/**
	 * Create and start the paint timer
	 */
	public void setUpPaintTimer() {
		// Start the paint timer
		paintTimer = new mainTimer();
		theBigTimer = new Timer(paintDelay, paintTimer);
		theBigTimer.start();
	}

	/**
	 * Called when the game ends, unbinds the key for going to the end card
	 * layout
	 */
	public void tearDown() {
		mainGame.unbindKeyWith("GotoEndGame", KeyStroke.getKeyStroke("SPACE"));
	}

	/**
	 * Start the game by starting all the timers that are used to automatically
	 * move key items in the game. These timers include the screen timer, debris
	 * move timer, power move timer. Erosion setup is also called.
	 */
	public void startGame() {
		System.out.print("in super startGame");
		// set up automatic movements!
		// ->create timer for debris
		// Turn on the screen timer!
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
	 * Set up the erosion timers for all pieces of the coast and all barrier
	 * spaces.
	 */
	public void erosionSetup() {
		barrierErosion bErode;
		int i = 0;
		for (Barriers b : items.getAllBarriers()) {
			bErode = new barrierErosion(b);
			b.setErosionTimer(new Timer(this.erodeDelay, bErode));
			b.getErosionTimer().start();
			allTimers.add(b.getErosionTimer());
			i++;
		}

		coastErosion cErode;
		for (Coast c : items.getCoast()) {
			cErode = new coastErosion(c);
			c.setErosionTimer(new Timer(this.erodeDelay, cErode));
			c.getErosionTimer().start();
			allTimers.add(c.getErosionTimer());
		}
	}

	/**
	 * End the game by stopping all timers and binding the end of game key.
	 */
	public void gameOver() {
		stopTimers();
		Action endGameAct = new endGameAction();
		mainGame.bindKeyWith("GotoEndGame", KeyStroke.getKeyStroke("SPACE"), endGameAct);

	}

	/**
	 * Stop the timers
	 */
	public void stopTimers() {
		for (Timer t : allTimers) {
			t.stop();
		}
	}

	/**
	 * Set up keys and player for when debris is caught
	 * 
	 * @param debris
	 *            - the caught item
	 */
	public void caughtSetup(Debris d) {
		this.choosingThrow = true;
		items.mainPlayer.setState(ePlayerState.Lifting);

		// Change the function of the keys
		mainGame.bindKeyWith("leftArrow", KeyStroke.getKeyStroke("A"), new ThrowChoice(eThrowDirection.LEFT, d));
		mainGame.bindKeyWith("rightArrow", KeyStroke.getKeyStroke("D"), new ThrowChoice(eThrowDirection.RIGHT, d));
		mainGame.bindKeyWith("leftArrow", KeyStroke.getKeyStroke("LEFT"), new ThrowChoice(eThrowDirection.LEFT, d));
		mainGame.bindKeyWith("rightArrow", KeyStroke.getKeyStroke("RIGHT"), new ThrowChoice(eThrowDirection.RIGHT, d));
		mainGame.bindKeyWith("throwDebris", KeyStroke.getKeyStroke("SPACE"), new ThrowChosen(d));

		// Don't let the player move!
		mainGame.unbindKeyWith("x.up", KeyStroke.getKeyStroke("W"));
		mainGame.unbindKeyWith("x.down", KeyStroke.getKeyStroke("S"));
		mainGame.unbindKeyWith("x.up", KeyStroke.getKeyStroke("UP"));
		mainGame.unbindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"));

	}

	/**
	 * Set up keys and player for when a power is caught
	 * 
	 * @param p
	 *            - the caught power
	 */
	public void caughtSetup(Powers p) {
		this.initiatingPowerUp = true;
		Action powerAction = new PowerInitiate(p);
		items.getPlayer().setState(ePlayerState.Lifting);
		mainGame.bindKeyWith("initiatePowerUp", KeyStroke.getKeyStroke("SPACE"), powerAction);
		freezeMotion();

	}

	/**
	 * Freeze the motion of the player by unbinding the direction move keys
	 */
	public void freezeMotion() {
		mainGame.unbindKeyWith("up", KeyStroke.getKeyStroke("W"));
		mainGame.unbindKeyWith("down", KeyStroke.getKeyStroke("S"));
		mainGame.unbindKeyWith("left", KeyStroke.getKeyStroke("A"));
		mainGame.unbindKeyWith("right", KeyStroke.getKeyStroke("D"));
		mainGame.unbindKeyWith("up", KeyStroke.getKeyStroke("UP"));
		mainGame.unbindKeyWith("down", KeyStroke.getKeyStroke("DOWN"));
		mainGame.unbindKeyWith("left", KeyStroke.getKeyStroke("LEFT"));
		mainGame.unbindKeyWith("right", KeyStroke.getKeyStroke("RIGHT"));
	}

	/**
	 * Return the keys to their original state, allow Player to move
	 */
	public void thrownSetup() {
		// Return the keys to their original state, allow Player to move
		normalKeyBind();
		mainGame.unbindKeyWith("throwDebris", KeyStroke.getKeyStroke("SPACE"));
		this.choosingThrow = false;
	}

	/**
	 * Return the keys to their original state, allow Player to move
	 */
	public void initiatedPowerSetup() {
		normalKeyBind();
		mainGame.unbindKeyWith("throwDebris", KeyStroke.getKeyStroke("SPACE"));
		this.initiatingPowerUp = false;

	}

	/**
	 * Check how the player is doing and adjust the difficulty if necessary.
	 * Variables that change based on difficulty include coast erosion rates and
	 * spawn debris times.
	 */
	public void checkDifficulty() {
		if (items.getHealthBar().getHealth() > 75
				&& items.getScreenTimer().getElapsedTime() > items.getScreenTimer().getMaxTime() / 5) {
			if (difficulty.equals(eDifficulty.HARD)) {
				if (items.getScreenTimer().getElapsedTime() < items.getScreenTimer().getMaxTime() * 0.9) {
					return;
				}
			}
			this.setDifficulty(difficulty.getNextDifficulty());
			System.out.println("Increasing Difficulty!!");
		} else if (items.getHealthBar().getHealth() < 25
				&& items.getScreenTimer().getElapsedTime() > items.getScreenTimer().getMaxTime() / 5) {
			this.setDifficulty(difficulty.getPreviousDifficulty());
			System.out.println("decreasing Difficulty!!");
		}

		switch (difficulty) {
		case VERYEASY:
			for (Coast c : items.getCoast()) {
				c.setErosionRate(35000);
			}
			this.getSpawnDebris().updateAveTime(15000);
			break;
		case EASY:
			for (Coast c : items.getCoast()) {
				c.setErosionRate(25000);
			}
			this.getSpawnDebris().updateAveTime(10000);
			break;
		case MEDIUM:
			for (Coast c : items.getCoast()) {
				c.setErosionRate(20000);
			}
			this.getSpawnDebris().updateAveTime(8000);
			break;
		case HARD:
			for (Coast c : items.getCoast()) {
				c.setErosionRate(15000);
			}
			this.getSpawnDebris().updateAveTime(5000);
			break;
		case IMPOSSIBLE:
			for (Coast c : items.getCoast()) {
				c.setErosionRate(6000);
			}
			this.getSpawnDebris().updateAveTime(2000);
			break;
		}

	}

	/**
	 * The spawnDebris class handles the movement and spawning of Debris in
	 * EstuaryGame. It should only be initialized once per game, and will be
	 * called continuously throughout the entire lifecycle of the game
	 * 
	 * @author Lia Dawson
	 * @version 1.0
	 * @since 10/25/16
	 */
	public class spawnDebris implements ActionListener, Serializable {

		public int timePassed = 0;
		public int spawnTimeDebris = 3000;
		public int aveTime = 3000; // the average time, used as a base for
									// randomization
		final public int rTime = 500;

		/**
		 * Get the time passed
		 * 
		 * @return timePassed
		 */
		public int getTimePassed() {
			return timePassed;
		}

		/**
		 * Set the time passed
		 * 
		 * @param timePassed
		 */
		public void setTimePassed(int timePassed) {
			this.timePassed = timePassed;
		}

		/**
		 * Get the the spawn time
		 * 
		 * @return spawnTime
		 */
		public int getSpawnTimeDebris() {
			return spawnTimeDebris;
		}

		/**
		 * Set the spawn time for debris
		 * 
		 * @param spawnTimeDebris
		 */
		public void setSpawnTimeDebris(int spawnTimeDebris) {
			this.spawnTimeDebris = spawnTimeDebris;
		}

		/**
		 * Construct a spawn debris action listener
		 */
		public spawnDebris() {
			spawnTimeDebris = 3000;
			items.addDebris(newDebris());
			resetTimer();
		}

		/**
		 * Returns a new piece of Debris with a randomly generated float path
		 * 
		 * @return
		 */
		public Debris newDebris() {
			timePassed = 0;
			Random r = new Random();
			// generate initial position;
			int randomx = r.nextInt(500) + 150;
			System.out.println(randomx);
			int xPos = MovementController.getStart(randomx);

			int dtype = r.nextInt() % 2;
			Debris d;
			if (dtype == 0) {
				d = new Debris(eDebrisType.TRASH);
			} else {
				d = new Debris(eDebrisType.RECYCLING);
			}
			d.setBins(items.getTrashBin(), items.getRecycleBin());
			d.updatePos(xPos, 0);
			d.setVertex(xPos);
			return d;
		}

		/**
		 * Resets the spawnTimeDebris to a new randomly generated number (within
		 * range)
		 */
		public void resetTimer() {
			Random r = new Random();
			spawnTimeDebris = r.nextInt(rTime) + aveTime - rTime / 2;
			timePassed = 0;
		}

		/**
		 * Check the debris if it catches or not, has to check the collisions
		 * 
		 * @param d
		 */
		public void checkCatchDebris(Debris d) {
			if (!thisGame.choosingThrow && collision.checkCollision(d) && !thisGame.initiatingPowerUp) {
				// sets the Debris state to Lifted
				d.catching();
				// sequence of events for a caught Debris initiated
				thisGame.caughtSetup(d);
				// Move the trash to above the Player's head
				d.updatePos(mainPlayer.getPosX() + mainPlayer.getWidth() / 2 - d.getWidth() / 2,
						mainPlayer.getPosY() - d.getHeight());
			}
		}

		/**
		 * Move debris and check if it was caught
		 * 
		 * @param d
		 */
		public void move(Debris d) {
			MovementController.move(d);
			checkCatchDebris(d);
		}

		/**
		 * Throw movement for debris. When the debris reaches the bin, it checks
		 * whether it was thrown to the correct bin, which correspond with
		 * different next actions
		 * 
		 * @param d
		 *            - the piece of debris being thrown
		 * @param toDelete
		 */
		public void throwing(Debris d, ArrayList<Debris> toDelete) {
			// This should be a sequence like move()

			MovementController.Throw(d, d.getBin());

			// Update the healthbar if it hit on this round
			if (d.getState() == eFloaterState.HITBIN) {
				if (d.getCorrectBin()) {
					d.setState(eFloaterState.RESTING);
					toDelete.add(d);
					items.getHealthBar().update(eHealthChanges.CorrectBin.getDelta());
					ScoreController.scoreBin();
				} else {
					MovementController.wrongBinMove(d);
					if (d.getState() == eFloaterState.RESTING) {
						items.getHealthBar().update(eHealthChanges.IncorrectBin.getDelta());
					}
				}
			}

		}

		/**
		 * Creates a new debris when the spawn time is reached
		 */
		public void spawnTimeReached() {
			items.addDebris(newDebris());
			resetTimer();
		}

		/**
		 * Perform the debris movement action for all debris, called at a fixed
		 * rate by a timer
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Debris> toDelete = new ArrayList<Debris>();
			// might want to put this for loop in its own class in the
			// Controller
			for (Debris d : items.getAllDebris()) {
				// make each item float
				if (d.getState() == eFloaterState.MOVING) {

					move(d);
					// If the debris hit the coast this round, decrement health
					if (d.getState() == eFloaterState.RESTING) {
						items.getHealthBar().update(eHealthChanges.DebrisHitCoast.getDelta());
					}
				} else if (d.getState() == eFloaterState.THROWING || d.getState() == eFloaterState.HITBIN) {
					throwing(d, toDelete);
				} else if (d.getState() == eFloaterState.RESTING) {
					checkCatchDebris(d);
				}
			}

			// Now delete any debris that hit
			for (Debris del : toDelete) {
				items.removeDebris(del);
			}

			// if the timer goes off then add another piece of debris at the top
			if (timePassed >= spawnTimeDebris) {
				spawnTimeReached();
			}

			timePassed += floatDelay;
		}

		/**
		 * update the average time
		 * 
		 * @param newTime
		 */
		public void updateAveTime(int newTime) {
			aveTime = newTime;
		}

	}

	/**
	 * This class is the main timer for the game. Its major function is to call
	 * the paint method. It also calls methods to update the health, update the
	 * difficulty, and calculate when the storm should be called.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class mainTimer implements ActionListener, Serializable {
		int maxTime = items.getScreenTimer().getMaxTime();

		public int scoringTime = 0;
		public int difficultyTime = 0;
		final public int scoreCheck = maxTime / 500;
		final public int difficultyCheck = maxTime / 10;
		public int healthTime = 0;

		final public int healthCheck = maxTime / 18;
		final public int delaySpotlight = 1000;

		public int stormTime = 0;
		public int realStormTime = 0;
		boolean stormChecked = false;
		final public int stormCheck = maxTime * 3 / 4; // storm check 3/4th into
														// the game

		@Override
		/**
		 * Calls repaint and calls the method that checks other game logic
		 */
		public void actionPerformed(ActionEvent e) {
			mainGame.repaint();
			checkItems();
		}

		/**
		 * Checks whether certain calculations and actions should occur. This
		 * includes: checking if the game is over (if health is 0 or the time is
		 * up), and calling methods to check the storm action, to score the
		 * game, to check the difficulty, and checking overall health
		 */
		public void checkItems() {
			if (items.getScreenTimer().getState() == eScreenTimerState.ON) {
				timeElapsed += paintDelay;
				scoringTime += paintDelay;
				difficultyTime += paintDelay;
				healthTime += paintDelay;
				stormTime += paintDelay;
				items.getScreenTimer().setElapsedTime(timeElapsed);

				checkStormTime();
				checkScoreTime();
				checkDifficultyTime();
				checkOverallHealth();
				if (items.getScreenTimer().getState() == eScreenTimerState.OFF) {
					gameOver();
				}
				if (items.getHealthBar().getHealth() <= 0) {
					gameOver();
				}
			}
		}

		/**
		 * Checks if the storm should be called based on the health of the
		 * estuary and the time elapsed
		 */
		public void checkStormTime() {
			if (stormTime >= stormCheck) { // later change this to stormCheck
				HealthBar hb = items.getHealthBar();
				if (!Storm.getAppeared() && (hb.getHealth() >= hb.getMaxHealth() * .5)) {
					if (!stormChecked) {
						realStormTime = stormTime + delaySpotlight * 3;
						stormChecked = true;
						items.setStormv(new StormVisual());
						System.out.println("\nStorm incoming in 3 seconds!!");
					}
					// storm appears only when it hasn't appeared yet, and
					// current health is 75% or more
					callStorm(stormTime);
				}
			}

		}

		/**
		 * Call the storm action
		 * 
		 * @param time
		 */
		public void callStorm(int time) {
			if (stormTime >= realStormTime) {
				Storm.stormEffects(items, debrisMover);
				Storm.setAppeared(true);
			}
		}

		/**
		 * Update the score if a fixed amount of time has passed
		 */
		public void checkScoreTime() {
			if (scoringTime >= scoreCheck) {
				ScoreController.scoreHealth(items.getHealthBar().getHealth());
				scoringTime = 0;
			}
		}

		/**
		 * Update the difficulty if a fixed amount of time has passed
		 */
		public void checkDifficultyTime() {
			if (difficultyTime >= difficultyCheck) {
				checkDifficulty();
				difficultyTime = 0;
			}
		}

		/**
		 * Check the overall health from the health bar
		 */
		public void checkOverallHealth() {
			if (healthTime >= healthCheck) {
				for (Debris d : items.getAllDebris()) {
					if (d.getState() == eFloaterState.RESTING) {
						items.getHealthBar().update(eHealthChanges.RestingDebrisGradual.getDelta());
					}
				}
				healthTime = 0;
			}
		}

		/**
		 * Get the elapsed time
		 * 
		 * @return
		 */
		public int getTimeElapsed() {
			return timeElapsed;
		}

	}

	/**
	 * The spawnPowers class handles the movement and spawning of powers in
	 * EstuaryGame. It should only be initialized once per game, and will be
	 * called continuously throughout the entire lifecycle of the game.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class spawnPowers implements ActionListener, Serializable {
		public int timePassed = 0;
		public int spawnTimePowers;
		public int aveTime = 15000;
		final public int rTime = 3000;
		protected boolean rebuildMode = false;
		protected boolean removeMode = false;
		protected Helper removeHelper;
		protected Tool rebuildTool;

		/**
		 * Construct a spawn power
		 */
		public spawnPowers() {
			resetTimer();
		}

		/**
		 * Randomly generate a new power
		 * 
		 * @return power
		 */
		public Powers newPower() {
			Random r = new Random();
			Powers p;
			if (r.nextInt() % 2 == 0) {
				p = newPower(new Rebuild());
			} else {
				p = newPower(new Remove());
			}

			return p;
		}

		/**
		 * Randomly generates a float path for the power
		 * 
		 * @param power
		 *            the power to update
		 * @return power
		 */
		public Powers newPower(Powers p) {
			Random r = new Random();
			// generate initial position;
			int randomx = r.nextInt(500) + 150;
			System.out.println("power:" + randomx);
			int xPos = MovementController.getStart(randomx);

			if (p instanceof Rebuild) {
				System.out.println("rebuild");
				p.updatePos(xPos, 0);
			} else {
				System.out.println("remove");
				p.updatePos(xPos, 0);
			}
			p.setVertex(xPos);

			return p;
		}

		/**
		 * Reset the timer that determines when to create a new power
		 */
		public void resetTimer() {
			Random r = new Random();
			spawnTimePowers = r.nextInt(rTime) + aveTime - rTime / 2;
			timePassed = 0;
		}

		public void quickSpawn() {
		}

		/**
		 * Generate a new Rebuild power
		 */
		public void quickSpawnRebuild() {
			items.addPower(newPower(new Rebuild()));
		}

		/**
		 * Generate a new Remove power
		 */
		public void quickSpawnRemove() {
			items.addPower(newPower(new Remove()));
		}

		/**
		 * Move the power and check if it is caught by the player
		 * 
		 * @param power
		 */
		public void move(Powers p) {
			MovementController.move(p);

			if (collision.checkCollision(p) && !thisGame.choosingThrow && !thisGame.initiatingPowerUp) {
				p.catching();
				thisGame.caughtSetup(p);
				p.updatePos(mainPlayer.getPosX() + mainPlayer.getWidth() / 2 - p.getWidth() / 2,
						mainPlayer.getPosY() - p.getHeight());
			}
		}

		/**
		 * Called when the spawn time is reached, this method determines which
		 * type of power should be generated based on the existence of the items
		 * affected by the power ups (resting debris and empty barriers)
		 */
		public void spawnTimeReached() {
			if (!items.getRestingDebris().isEmpty()) {
				if (items.emptyBarriers() >= 4) {
					items.addPower(newPower());
				} else {
					this.quickSpawnRemove();
				}
			} else {
				if (items.emptyBarriers() >= 4) {
					this.quickSpawnRebuild();
				}
			}

			resetTimer();
		}

		/**
		 * Calls the addTime function on the active rebuild tool, which does the
		 * rebuild action and visuals. When the rebuild tool is done building,
		 * the tool is deleted, the temporary coast protection is turned off,
		 * and the barrier spawn points are active again.
		 */
		public void rebuildAction() {
			rebuildTool.addTime(floatDelay);
			if (rebuildTool.doneBuilding()) {
				items.deleteRebuildTool();
				for (Coast c : items.getCoast()) {
					c.tempProtect(false);
				}
				EstuaryGame.mc.setWallSpawnable(true);
				EstuaryGame.mc.setGabionSpawnable(true);
				rebuildMode = false;
			}
		}

		/**
		 * Moves the remove helper and calls its addTime method, which controls
		 * the logic of its power up action.
		 */
		public void removeAction() {
			MovementController.walkMove(removeHelper);
			removeHelper.addTime(floatDelay);
			if (removeHelper.getState() == eHelperState.WALKING_OFF && removeHelper.getTimeInStage() == 0) {
				removeHelper.getPower().power(items);
			}
			if (removeHelper.getState() == eHelperState.VOID) {
				items.deleteRemoveHelper();
				removeMode = false;
			}
		}

		@Override
		/**
		 * Perform the movement for all powers in game, and handle if the power
		 * is caught
		 */
		public void actionPerformed(ActionEvent e) {

			for (Powers p : items.getAllPowers()) {
				// make each item float
				if (p.getState() == eFloaterState.MOVING) {
					move(p);
				} else if (p.getState() == eFloaterState.INITIATED) {
					if (p instanceof Rebuild) {
						if (!rebuildMode) {
							if (getItems().emptyBarriers() != 0) {
								rebuildTool = new Tool(Rebuild.getRebuildBarriers(getItems()));
								items.setRebuildTool(rebuildTool);
								rebuildTool.stopErosion(items.getCoast());
								rebuildMode = true;
								EstuaryGame.mc.setWallSpawnable(false);
								EstuaryGame.mc.setGabionSpawnable(false);
							}
						}
					} else if (p instanceof Remove) {
						if (!removeMode) {
							if (getItems().getRestingDebris().size() != 0) {
								removeHelper = new Helper();
								removeHelper.setFinalY(items.getAllDebris());
								items.setRemoveHelper(removeHelper);
								removeMode = true;
							}
						}

					}
					ScoreController.scorePower();
				}

			}
			// Removes power up if hits coast or powerup was initiated
			Iterator poweritr = items.getAllPowers().iterator();
			while (poweritr.hasNext()) {
				Powers p = (Powers) poweritr.next();
				if (p.getState() == eFloaterState.INITIATED || p.getState() == eFloaterState.RESTING) {
					poweritr.remove();
				}
			}

			if (removeMode) {
				removeAction();
			}

			if (rebuildMode) {
				rebuildAction();
			}
			// if the timer goes off then add another power at the top
			if (timePassed >= spawnTimePowers) {
				spawnTimeReached();
			}
			timePassed += floatDelay;
		}

		/**
		 * update the average time;
		 * 
		 * @param newTime
		 */
		public void updateAveTime(int newTime) {
			aveTime = newTime;
		}

	}

	/**
	 * The class that controls the logic of coast erosion. An instance of this
	 * class and a corresponding timer is made for each coast piece and plays
	 * throughout the entirety of the game.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class coastErosion implements ActionListener, Serializable {
		private Coast coast;
		public int timePassed;
		public int erosionTime;
		public int aveTime;
		final public int rTime = 5000;

		/**
		 * construct a coast erosion
		 * 
		 * @param c
		 */
		public coastErosion(Coast c) {
			coast = c;
			updateErosion(coast);
			timePassed = 0;
		}

		/**
		 * Calculate the erosion rate of the cast
		 * 
		 * @param coast
		 */
		public void updateErosion(Coast coast) {
			Random r = new Random();
			// assumes erosion rate in Coast is in milliseconds
			aveTime = (int) coast.getErosionRate();
			erosionTime = r.nextInt(rTime) + aveTime - rTime / 2;
		}

		@Override
		/**
		 * Calculate whether erosion should occur
		 */
		public void actionPerformed(ActionEvent e) {
			if (coast.isProtected()) {
				// System.out.println("The coast is protected at: " +
				// coast.getPosX());
				return;
			} else {
				if (timePassed >= erosionTime) {
					coast.erode();
					timePassed = 0;
					updateErosion(coast);
					items.getHealthBar().update(eHealthChanges.CoastEroded.getDelta());

					System.out.println("Updating erosion rate" + coast.getErosionRate());
				} else {
					timePassed += erodeDelay;
				}
			}
		}
	}

	/**
	 * Controls the logic of barrier erosion. This class is implemented as a
	 * timer. There is an instance of this class and its corresponding timer for
	 * each barrier, and they run throughout the entirety of the game.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class barrierErosion implements ActionListener, Serializable {
		private Barriers barrier;
		public int timePassed = 0;
		public int erosionTime;
		public int aveTime;
		final public int rTime = 4000;

		/**
		 * Construct a barrier erosion
		 * 
		 * @param b
		 */
		public barrierErosion(Barriers b) {
			barrier = b;
			newTime();
		}

		/**
		 * Calculate a new erosion time
		 */
		public void newTime() {
			Random r = new Random();
			aveTime = barrier.getDecayTime();
			erosionTime = r.nextInt(rTime) + aveTime - rTime / 2;
			System.out.println("new erosion: " + erosionTime);

		}

		/**
		 * Determine if barrier erosion should occur, and call the corresponding
		 * methods
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (barrier.getType() == eBarrierType.EMPTY) {
				return;
			}

			if (timePassed == 0) {
				newTime();
				timePassed += erodeDelay;
			} else if (timePassed >= erosionTime) {
				barrier.erode();
				newTime();
				timePassed = 0;
				// items.getHealthBar().update(eHealthChanges.BarrierFallen.getDelta());
			} else if (barrier.getState() == eBarrierState.NO_HIT && timePassed >= (0.5) * erosionTime) {
				System.out.println("Eroding half at: " + timePassed);
				barrier.erodeHalf();
				timePassed += erodeDelay;
			} else {
				timePassed += erodeDelay;
			}
		}

	}

	/**
	 * The action class for moving horizontally. This class is bound with A and
	 * D, as well as left and right to allow for multifunctional use.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class HAction extends AbstractAction {

		// the amount the player moves when you press the key
		private int moveSize;

		/**
		 * Create the action class, set the move amount
		 * @param jump
		 */
		public HAction(int jump) {
			this.moveSize = jump;
		}

		/**
		 * Update the amount moved by one action
		 * @param jump
		 */
		public void updateSpeed(int jump) {
			this.moveSize = jump;
		}

		@Override
		/**
		 * Action performed - move the player, unless they're off the screen
		 */
		public void actionPerformed(ActionEvent e) {
			int next = getMainPlayer().getPosX() + moveSize;
			int edge = dimX - getMainPlayer().getWidth();
			if (next <= 0) {
				getMainPlayer().updatePosX(0);
			} else if (next >= edge) {
				getMainPlayer().updatePosX(edge);
			} else {
				getMainPlayer().updatePosX(getMainPlayer().getPosX() + moveSize);
			}
		}

	}

	/**
	 * 
	 * @author Lia
	 *
	 */
	public class VAction extends AbstractAction {

		// the amount the player moves when you press the key
		private int moveSize;

		public VAction(int jump) {
			this.moveSize = jump;
		}

		public void updateSpeed(int jump) {
			this.moveSize = jump;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int next = getMainPlayer().getPosY() + moveSize;
			int edge = dimY - getMainPlayer().getHeight();
			if (next <= 0) {
				getMainPlayer().updatePosY(0);
			} else if (next >= edge) {
				getMainPlayer().updatePosY(edge);
			} else {
				getMainPlayer().updatePosY(getMainPlayer().getPosY() + moveSize);
			}
		}

	}

	/**
	 * This class
	 *
	 */
	public class quitAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/**
	 * When the player catches Debris, left and right keys bind with this action
	 *
	 */
	public class ThrowChoice extends AbstractAction {
		private eThrowDirection dir;
		private Debris caughtDebris;

		public ThrowChoice(eThrowDirection dir, Debris d) {
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
	 * <<<<<<< HEAD Action to be bound with the enter key when a piece of Debris
	 * is caught. Pressing enter releases the Debris ======= Action to be bound
	 * with the space key when a piece of Debris is caught. Pressing space
	 * releases the Debris >>>>>>> branch 'master' of
	 * https://github.com/ldawson02/EstuaryGame.git
	 *
	 */
	public class ThrowChosen extends AbstractAction {

		private Debris caughtDebris;

		public ThrowChosen(Debris d) {
			this.caughtDebris = d;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			caughtDebris.setState(eFloaterState.THROWING);
			items.getPlayer().setState(ePlayerState.Idle);
			thisGame.thrownSetup();
		}

	}

	public class PowerInitiate extends AbstractAction {

		private Powers caughtPower;

		public PowerInitiate(Powers p) {
			this.caughtPower = p;
		}

		@Override
		/**
		 * perform the power up initial action
		 */
		public void actionPerformed(ActionEvent e) {
			caughtPower.setState(eFloaterState.INITIATED);
			thisGame.initiatedPowerSetup();
			items.getPlayer().setState(ePlayerState.Idle);
		}

	}

	/**
	 * goes through list of barriers and changes the one with the matching
	 * coords to type t
	 * 
	 * @param barr
	 * @param t
	 */
	public void setBarrierType(Barriers barr, eBarrierType t) {

		for (Barriers b : this.items.getAllBarriers()) {
			if ((barr.getPosX() >= b.getPosX() && barr.getPosX() <= b.getPosX() + b.getWidth())
					&& (barr.getPosY() >= b.getPosY() && barr.getPosY() <= b.getPosY() + b.getHeight())) { // "match"
				b.setType(t);
				System.out.println("inside space");
			}
		}
	}

	/**
	 * Returns whether a Barrier barr has collided with any other Barrier on the
	 * screen
	 * 
	 * @param barr
	 * @return
	 */
	public Barriers emptyBarrierCollision(Barriers barr) {
		// checks if barr collided with any of the barriers and if it is empty
		for (Barriers b : this.items.getAllBarriers()) {
			if ((Collisions.checkCollision(b, barr) && (b.getType() == eBarrierType.EMPTY))) {
				System.out.println("empty barrier collide");
				return b;
			}
		}
		return null;
	}

	/**
	 * get the main player
	 * 
	 * @return
	 */
	public Player getMainPlayer() {
		return mainPlayer;
	}

	/**
	 * set the main player
	 * 
	 * @param mainPlayer
	 */
	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

	/**
	 * set the difficulty
	 * 
	 * @param diff
	 */
	public void setDifficulty(eDifficulty diff) {
		difficulty = diff;
	}

	/**
	 * when end the game, then end screen will show up
	 * 
	 * @author Pu
	 *
	 */
	public class endGameAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
			c1.show(EstuaryGame.getCards(), "EndScreen");
		}

	}

	public class SerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Serialize to file
			try {
				System.out.print("writing");
				FileOutputStream fos = new FileOutputStream("testSerialize.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				// TODO

				// oos.writeObject(items);
				// Write the player
				oos.writeObject(mainPlayer);
				// Write the ActiveItems
				oos.writeObject(items);
				// Write the rest
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
			} catch (Exception ex) {
				System.out.println("Exception thrown during test: " + ex.toString());
				ex.printStackTrace();
			}

		}

	}

	// TODO
	public class ReadSerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Serialize to file
			try {
				System.out.print("reading");
				FileInputStream fis = new FileInputStream("testSerialize.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);

				// TODO: ois.readObject()
				// Write the player
				mainPlayer = (Player) ois.readObject();
				// Write the ActiveItems
				items = (ActiveItems) ois.readObject();
				// Write the rest
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
			} catch (Exception ex) {
				System.out.println("Exception thrown during test: " + ex.toString());
			}
		}
	}

	public class CleanUpSerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Serialize to file
			try {
				// Clean up the file
				new File("testSerialize.ser").delete();
			} catch (Exception ex) {
				System.out.println("Exception thrown during test: " + ex.toString());
			}
		}

	}

}
