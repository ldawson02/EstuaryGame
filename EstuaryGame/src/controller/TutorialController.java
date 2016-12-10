package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import controller.GameController.mainTimer;
import eNums.eBarrierState;
import eNums.eBarrierType;
import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.eHealthChanges;
import eNums.eHelperState;
import eNums.eTutorialState;
import model.Barriers;
import model.Coast;
import model.Debris;
import model.DrawableItem;
import model.Item;
import model.Powers;
import view.EstuaryGame;
import view.Tutorial;

/**
 * The main controller for the Tutorial mode of the game. This class extends
 * Game Controller, and goes through similar logical processes as the game
 * controller, but limits the functionality to a few specific actions at a time.
 * 
 * @author Lia Dawson
 */
public class TutorialController extends GameController {

	Tutorial t;
	private int floatDelay = 100;
	private int erodeCallDelay = 100;
	private int delaySpotlight = 1000;
	private int healthStageTime = 5000;
	private int timerStageTime = 5000;
	private int idleTime = 500000;
	public int timeInStage;
	private mainTimer tutorialPaintTimer;
	private Timer erosionTimer;
	private ArrayList<Timer> erosionTimers = new ArrayList<Timer>();
	private HashSet<Timer> allTimers = new HashSet<Timer>();

	/**
	 * Construct a tutorial controller connected to the main tutorial game. The
	 * game is setup via the same process as game controller. The game begins
	 * playing when this class is called.
	 * 
	 * @param mainGame
	 */
	public TutorialController(Tutorial mainGame) {
		super(mainGame);
		t = mainGame;
		tutorialSetup();
		debrisSetup();
		debrisFloating.start();
		debrisFloating.setDelay(floatDelay);
	}

	@Override
	/**
	 * Overrides the super startGame method to prevent certain actions from
	 * occurring (set up of erosion timers, debris and power spawns).
	 */
	public void startGame() {
	}

	/**
	 * Overrides super method to stop the game
	 */
	public void gameOver() {
		t.setState(eTutorialState.DONE);
		stopTimers();
	}

	/**
	 * Set up more barriers for the tutorial version
	 */
	public void tutorialSetup() {
		getItems().getAllBarriers().get(8).setType(eBarrierType.Wall);
		getItems().getAllBarriers().get(9).setType(eBarrierType.Gabion);
		getItems().getAllBarriers().get(1).setType(eBarrierType.Gabion);
		getItems().getAllBarriers().get(2).setType(eBarrierType.Wall);
		getItems().getAllBarriers().get(3).setType(eBarrierType.Gabion);
		// getItems().getAllBarriers().get(6).setType(eBarrierType.EMPTY);
	}

	@Override
	/**
	 * Set up the paint timer
	 */
	public void setUpPaintTimer() {
		// Start the paint timer
		paintTimer = new mainTimer();
		theBigTimer = new Timer(paintDelay, paintTimer);
		theBigTimer.start();
	}

	/**
	 * Get the float delay
	 * 
	 * @return
	 */
	public int getFloatDelay() {
		return floatDelay;
	}

	/**
	 * Called when the stage is complete, turn the spotlight off and go to the
	 * next stage.
	 */
	public void stageComplete() {
		t.spotlightSwitched = false;
		t.spotlight = false;
		t.nextState();
		timeInStage = 0;
		startStage();
	}

	/**
	 * Stop all the erosion timers. This is utilized to prevent erosion from
	 * occurring during non-erosion tutorial states.
	 */
	public void stopErosionTimers() {
		for (Timer t : erosionTimers) {
			t.stop();
		}
	}

	@Override
	/**
	 * Stop all the timers
	 */
	public void stopTimers() {
		super.stopTimers();
		for (Timer t : allTimers) {
			t.stop();
		}
	}

	/**
	 * Start the current stage. This involves calling a corresponding setup
	 * class.
	 */
	public void startStage() {
		switch (t.getState()) {
		case DEBRIS:
			break;
		case EROSION_GABION:
			erosion1Setup();
			break;
		case EROSION_WALL:
			erosion2Setup();
			break;
		case EROSION_CHOICE:
			erosion3Setup();
			break;
		case POWERS_REMOVE:
			stopErosionTimers();
		case POWERS_REBUILD:
			powerSetup(t.getState());
			break;
		case HEALTH:
			healthSetup();
			break;
		case TIMER:
			timerSetup();
			break;
		case DONE:
			doneSetup();
		default:
			break;
		}
	}

	/**
	 * Setup for debris stage, start the debris timer.
	 */
	public void debrisSetup() {
		this.debrisMover = new spawnDebris();
		debrisFloating = new Timer(this.getFloatDelay(), debrisMover);
		allTimers.add(debrisFloating);
	}

	/**
	 * Setup for the first erosion stage. Turn on the erosion timer for a
	 * specific coast and barrier. Activate the gabion spawn points, but not the
	 * wall spawn points. Also, create an arrow that moves to indicate dragging.
	 */
	public void erosion1Setup() {
		t.addMouseListener(t.mc);
		t.addMouseMotionListener(t.mc);
		t.mc.setWallSpawnable(false);

		freezeMotion();

		Coast focusCoast = getItems().getCoast().get(5); // Barrier above should
															// be empty
		t.setSpotlightItem(focusCoast);
		Barriers focusBarrierSpot = focusCoast.getBarrier();
		focusBarrierSpot.setErosionTimer(new Timer(erodeCallDelay, new barrierErosion(focusBarrierSpot)));
		// focusBarrierSpot.getErosionTimer().start();

		focusCoast.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusCoast)));
		focusCoast.getErosionTimer().start();

		DrawableItem arrow = new DrawableItem(EstuaryGame.mc.getGabionsSpawnR().getPosX(),
				EstuaryGame.mc.getGabionsSpawnR().getPosY(), 32, 50);
		arrow.setStartandEnd(EstuaryGame.mc.getGabionsSpawnR().getPosX() + 10,
				EstuaryGame.mc.getGabionsSpawnR().getPosY() + 10, focusBarrierSpot.getPosX() + 10,
				focusBarrierSpot.getPosY() + 10);
		t.setArrow(arrow);

		erosionTimers.add(focusBarrierSpot.getErosionTimer());
		erosionTimers.add(focusCoast.getErosionTimer());
		allTimers.addAll(erosionTimers);
	}

	/**
	 * Setup for the second erosion stage. Turn on the erosion timer for a
	 * specific coast and barrier. Activate the wall spawn points, but not the
	 * gabion points. Create an arrow that moves to indicate dragging.
	 */
	public void erosion2Setup() {
		t.mc.setGabionSpawnable(false);
		t.mc.setWallSpawnable(true);

		Coast focusCoast = getItems().getCoast().get(6);
		Barriers focusBarrierSpot = focusCoast.getBarrier();
		t.setSpotlightItem(focusBarrierSpot);
		focusCoast.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusCoast)));
		focusBarrierSpot.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusBarrierSpot, focusCoast, 2000)));
		focusBarrierSpot.getErosionTimer().start();

		DrawableItem arrow = new DrawableItem(EstuaryGame.mc.getWallSpawnR().getPosX(),
				EstuaryGame.mc.getWallSpawnR().getPosY(), 32, 50);
		arrow.setStartandEnd(EstuaryGame.mc.getWallSpawnR().getPosX() + 10,
				EstuaryGame.mc.getWallSpawnR().getPosY() + 10, focusBarrierSpot.getPosX() + 10,
				focusBarrierSpot.getPosY() + 10);
		t.setArrow(arrow);

		erosionTimers.add(focusBarrierSpot.getErosionTimer());
		erosionTimers.add(focusCoast.getErosionTimer());
		allTimers.addAll(erosionTimers);
	}

	/**
	 * Setup for the third erosion stage. Turn on the erosion timer for two
	 * specific coasts and barriers. Activate both wall and gabion spawn points.
	 */
	public void erosion3Setup() {
		t.mc.setGabionSpawnable(true);
		t.mc.setWallSpawnable(true);

		Coast focusCoast1 = getItems().getCoast().get(5);
		Coast focusCoast2 = getItems().getCoast().get(6);
		// not sure if this will work well, but want to draw spotlight at the
		// center of both these items
		t.setSpotlightItem(new DrawableItem(focusCoast1.getPosX(), focusCoast1.getPosY(),
				focusCoast1.getWidth() + focusCoast2.getWidth(), focusCoast1.getHeight()));
		Barriers focusBarrierSpot1 = focusCoast1.getBarrier();
		Barriers focusBarrierSpot2 = focusCoast2.getBarrier();
		if (focusBarrierSpot1.getType() != eBarrierType.Gabion || focusBarrierSpot2.getType() != eBarrierType.Wall) {
			System.out.println("ERROR IN BARRIERS");
		}

		focusCoast1.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusCoast1)));
		focusCoast2.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusCoast2)));

		focusBarrierSpot1.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusBarrierSpot1, focusCoast1, 7000)));
		focusBarrierSpot1.getErosionTimer().start();
		focusBarrierSpot2.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusBarrierSpot2, focusCoast2, 2500)));
		focusBarrierSpot2.getErosionTimer().start();

		erosionTimers.add(focusBarrierSpot1.getErosionTimer());
		erosionTimers.add(focusCoast1.getErosionTimer());
		erosionTimers.add(focusBarrierSpot2.getErosionTimer());
		erosionTimers.add(focusCoast2.getErosionTimer());

		allTimers.addAll(erosionTimers);
	}

	/**
	 * Setup for the power stages. This occurs twice, once for the Remove power
	 * and once for the Rebuild power. This involves starting the timer for
	 * powers.
	 * 
	 * @param state
	 */
	public void powerSetup(eTutorialState state) {
		normalKeyBind();
		if (state == eTutorialState.POWERS_REBUILD) {
			rebuildSetup();
		}
		this.powerMover = new spawnPowers(state);
		powersFloating = new Timer(this.getFloatDelay(), powerMover);
		powersFloating.start();
		allTimers.add(powersFloating);
	}

	/**
	 * Extra set up for the Rebuild stage, which includes eroding some barriers
	 * to allow Rebuild action to be more obvious.
	 */
	public void rebuildSetup() {
		// need to erode some walls
		int[] nums = { 1, 3, 4, 6, 8 };
		for (int i : nums) {
			getItems().getAllBarriers().get(i).erode();
		}

	}

	/**
	 * Setup for health stage, which involves setting the spotlight.
	 */
	public void healthSetup() {
		t.setSpotlightItem(getItems().getHealthBar());
	}

	/**
	 * Set up for the timer stage, which involves setting the timer spotlight.
	 */
	public void timerSetup() {
		t.setSpotlightItem(getItems().getScreenTimer());
	}

	/**
	 * Setup for the done stage, turn the spotlight off.
	 */
	public void doneSetup() {
		t.spotlightSwitched = true;
		t.setSpotlightItem(null);
	}

	/**
	 * This class extends the mainTimer in GameController. It has similar
	 * functionality of calling paint, and also checks to make sure the player
	 * hasn't been in one stage for too long.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class mainTimer extends GameController.mainTimer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}

		@Override
		/**
		 * Override the checkItems method in the super class to allow for
		 * different functionality. Checks the stage and calls a corresponding
		 * check limit function.
		 */
		public void checkItems() {
			checkSpotlight();
			switch (t.getState()) {
			case HEALTH:
				checkHealthLimit();
				break;
			case TIMER:
				checkTimerLimit();
				break;
			default:
				break;
			}
			timeInStage += paintDelay;
		}

		/**
		 * Checks to see if the health stage has been on for too long.
		 */
		public void checkHealthLimit() {
			if (timeInStage >= healthStageTime) {
				stageComplete();
			}
		}

		/**
		 * Checks to see if the timer stage has been on for too long.
		 */
		public void checkTimerLimit() {
			if (timeInStage >= timerStageTime) {
				stageComplete();
			}
		}

		/**
		 * Creates a delay in the spotlight
		 */
		public void checkSpotlight() {
			if (timeInStage >= delaySpotlight && !t.spotlightSwitched) {
				t.spotlight = true;
			}
		}

	}

	/**
	 * This class extends the spawnDebris in GameController. It has similar
	 * functionality of moving and spawning debris, but it makes sure there is
	 * only one or two debris falling at a time to make the tutorial phase
	 * simple.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class spawnDebris extends GameController.spawnDebris implements ActionListener {

		@Override
		/**
		 * Overrides the super so that nothing happens when the spawn time is
		 * reached
		 */
		public void spawnTimeReached() {
		}

		@Override
		/**
		 * Same functionality as super class (creates debris) but also sets the
		 * new debris as the spotlight item.
		 */
		public Debris newDebris() {
			Debris d = super.newDebris();
			t.setSpotlightItem(d);
			return d;
		}

		@Override
		/**
		 * Same functionality as super class (moves debris) but turns spotlight
		 * off when the debris is caught.
		 */
		public void move(Debris d) {
			super.move(d);
			if (d.getState() == eFloaterState.LIFTED) {
				t.setSpotlight(false);
			}
		}

		@Override
		/**
		 * Same functionality as super, but doesn't allow the debris to be
		 * thrown in the wrong direction.
		 */
		public void throwing(Debris d, ArrayList<Debris> toDelete) {
			if (d.getCorrectBin()) {
				super.throwing(d, toDelete);
				if (toDelete.contains(d)) {
					debrisFloating.stop();
					stageComplete();
				}
			} else {
				// If they try to send the debris to the wrong bin, don't allow
				// them to
				t.wrongBin();
				d.setState(eFloaterState.LIFTED);
				thisGame.caughtSetup(d);
			}
		}

		@Override
		/**
		 * Same functionality as super, but creates a new debris if the current
		 * debris has floated too far down into the estuary.
		 */
		public void actionPerformed(ActionEvent e) {
			int distance = GameController.dimY;
			for (Debris d : thisGame.getItems().getAllDebris()) {
				if (d.getPosY() < distance && t.getState() == eTutorialState.DEBRIS) {
					distance = d.getPosY();
				}
			}

			if (distance >= GameController.dimY / 4) {
				thisGame.getItems().addDebris(newDebris());
			}
			super.actionPerformed(e);
		}

	}

	/**
	 * This class extends the spawnPowers in GameController. It has similar
	 * functionality of moving and spawning debris, but it makes sure there is
	 * only one or two powers falling at a time to make the tutorial phase
	 * simple.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class spawnPowers extends GameController.spawnPowers implements ActionListener {
		eTutorialState eState;
		private int time = 0;
		public boolean animationStarted = false;

		public spawnPowers(eTutorialState e) {
			super();
			eState = e;
			quickSpawn();
			time = 0;
		}

		@Override
		/**
		 * Overrides the super so that nothing happens when spawn time is
		 * reached
		 */
		public void spawnTimeReached() {
		}

		@Override
		/**
		 * Spawn a new power based on the current state.
		 */
		public void quickSpawn() {
			switch (eState) {
			case POWERS_REMOVE:
				System.out.println("making new Remove");
				super.quickSpawnRemove();
				break;
			case POWERS_REBUILD:
				System.out.println("making new Rebuild");
				super.quickSpawnRebuild();
				break;
			default:
				System.out.println("Incorrect creation of Powers Timer");
				break;
			}
		}

		@Override
		/**
		 * Same functionality as super, but sets the spotlight item.
		 */
		public Powers newPower(Powers p) {
			p = super.newPower(p);
			t.setSpotlightItem(p);
			return p;
		}

		@Override
		/**
		 * Same functionality as super, but has controls to allow the spotlight
		 * to follow different items based on the point in the stage. Also
		 * spawns new power if the current power has floated too far down river.
		 */
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);

			if (time != 0 & !thisGame.getItems().getAllPowers().contains(t.getSpotlightItem())) {
				if (!animationStarted) {
					animationStarted = true;
					if (t.getState() == eTutorialState.POWERS_REMOVE) {
						t.setSpotlightItem(removeHelper);
					} else {
						t.setSpotlight(false);
						t.setSpotlightItem(null);
					}
				}
				if (t.getState() == eTutorialState.POWERS_REMOVE) {
					if (removeHelper.getState() == eHelperState.WALKING_OFF) {
						t.setSpotlight(false);
					}
					if (!removeMode) {
						System.out.println("Stopping power: " + eState);
						powersFloating.stop();
						t.setSpotlight(false);
						stageComplete();
					}
				} else if (t.getState() == eTutorialState.POWERS_REBUILD) {
					if (!rebuildMode) {
						System.out.println("Stopping power: " + eState);
						powersFloating.stop();
						t.setSpotlight(false);
						stageComplete();
					}
				}

			} else {
				int distance = GameController.dimY;
				for (Powers p : thisGame.getItems().getAllPowers()) {
					if (p.getPosY() < distance) {
						distance = p.getPosY();
					}
				}

				if (distance >= GameController.dimY / 4 && !this.removeMode && !this.rebuildMode) {
					quickSpawn();
				}
			}
			time += floatDelay;
		}
	}

	/**
	 * This class controls the erosion of the barriers and coast in the tutorial
	 * mode.
	 * 
	 * @author Lia Dawson
	 *
	 */
	public class erosion implements ActionListener {
		private Coast erodingCoast;
		private Coast protectedCoast;
		private Barriers erodingBarrier;
		private int erodeDelay = 3000;
		private int time = 0;
		private boolean hold = false;
		private int spotlightDelay = 500;

		public erosion(Coast c) {
			erodingCoast = c;
			erodingBarrier = null;
		}

		/**
		 * Set barrier and coast for erosion
		 * 
		 * @param b
		 * @param c
		 * @param time
		 */
		public erosion(Barriers b, Coast c, int time) {
			erodingBarrier = b;
			erodingCoast = null;
			protectedCoast = c;
			erodeDelay = time;
		}

		/**
		 * Functionality for the erosion of the coast, calculating when it
		 * should erode.
		 */
		public void coastErosion() {
			if (time >= erodeDelay) {
				erodingCoast.erode();
				time = 0;
				erodeDelay = 10000;
				hold = true;
			}
			if (erodingCoast.isProtected()) {
				if (time == 0) {
					return;
				}
				if (t.getState() == eTutorialState.EROSION_CHOICE) {
					stageComplete();
				}
				// They added the barrier! Yay!
				else if (erodingCoast.getBarrier().getType() == eBarrierType.Gabion) {
					// Finished the gabion stage
					erodingCoast.getErosionTimer().stop();
					erodingCoast.getBarrier().getErosionTimer().stop();
					stageComplete();
				} else if (erodingCoast.getBarrier().getType() == eBarrierType.Wall) {
					// Finished the wall stage
					erodingCoast.getErosionTimer().stop();
					erodingCoast.getBarrier().getErosionTimer().stop();
					stageComplete();
				}
			} else {
				time += erodeCallDelay;
			}
			if (hold) {
				checkSpotlight();
			}
		}

		/**
		 * Functionality for the barrier erosion, calculating when the barrier
		 * should erode.
		 */
		public void barrierErosion() {
			time += erodeCallDelay;
			if (time >= erodeDelay) {
				erodingBarrier.erode();
				t.setSpotlight(false);
				t.spotlightSwitched = true;
				erodingBarrier.getErosionTimer().stop();
				protectedCoast.getErosionTimer().start();
			} else if (erodingBarrier.getState() != eBarrierState.ONE_HIT & time >= (1 / 2) * erodeDelay) {
				erodingBarrier.erodeHalf();
			}

		}

		/**
		 * Turn off the spotlight after a certain amount of time.
		 */
		public void checkSpotlight() {
			if (time >= delaySpotlight) {
				t.spotlight = false;
				t.spotlightSwitched = true;
				hold = false;
			}
		}

		@Override
		/**
		 * Calls the coast and barrier erosion if applicable
		 */
		public void actionPerformed(ActionEvent e) {
			if (erodingCoast != null) {
				coastErosion();
			} else if (erodingBarrier != null) {
				barrierErosion();
			}
		}
	}

}
