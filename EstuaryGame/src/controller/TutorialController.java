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
	private unpause unpauseAction;
	private Timer erosionTimer;
	private ArrayList<Timer> erosionTimers = new ArrayList<Timer>();
	private HashSet<Timer> allTimers = new HashSet<Timer>();
	
	public TutorialController(Tutorial mainGame) {
		super(mainGame);
		t = mainGame;
		unpauseAction = new unpause();
		tutorialSetup();
		debrisSetup();
		debrisFloating.start();
		debrisFloating.setDelay(floatDelay);
		System.out.println("still in constructor");
	}

	@Override
	public void startGame(){
		System.out.println("POLYMORPHISM");
	}
	
	public void gameOver(){
		t.setState(eTutorialState.DONE);
		stopTimers();
	}
	
	public void tutorialSetup(){
		getItems().getAllBarriers().get(8).setType(eBarrierType.Wall);
		getItems().getAllBarriers().get(9).setType(eBarrierType.Gabion);
		getItems().getAllBarriers().get(1).setType(eBarrierType.Gabion);
		getItems().getAllBarriers().get(2).setType(eBarrierType.Wall);
		getItems().getAllBarriers().get(3).setType(eBarrierType.Gabion);
		//getItems().getAllBarriers().get(6).setType(eBarrierType.EMPTY);
	}
	
	@Override
	public void setUpPaintTimer(){
		//Start the paint timer
		System.out.println("Paint timer setup in subclass");
		paintTimer = new mainTimer();
		theBigTimer = new Timer(paintDelay, paintTimer);
		theBigTimer.start();
	}
	
	public int getFloatDelay() {
		return floatDelay;
	}

	public void stageComplete(){
		t.spotlightSwitched = false;
		t.spotlight = false;
		t.nextState();
		timeInStage = 0;
		startStage();
	}
	
	public void stageIdle(){
		t.spotlightSwitched = true;
		t.spotlight = false;
		t.setState(eTutorialState.IDLE);
		//this.getMainGame().bindKeyWith("continueTutorial", KeyStroke.getKeyStroke("ENTER"), unpauseAction);
	}
	
	public void stopErosionTimers(){
		for(Timer t : erosionTimers){
			t.stop();
		}
	}
	
	@Override
	public void stopTimers(){
		super.stopTimers();
		for(Timer t : allTimers){
			t.stop();
		}
	}
	
	public void startStage(){
		System.out.println("State is: " + t.getState());
		switch(t.getState()){
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
	
	public void debrisSetup(){
		this.debrisMover = new spawnDebris();
		System.out.println("float delay: " + this.getFloatDelay());
		debrisFloating = new Timer(this.getFloatDelay(), debrisMover);
		allTimers.add(debrisFloating);
	}
	
	public void erosion1Setup(){
		System.out.println("Erosion setup");
		t.addMouseListener(t.mc);
		t.addMouseMotionListener(t.mc);
		t.mc.setWallSpawnable(false);
		
		freezeMotion();
		
		
		Coast focusCoast = getItems().getCoast().get(5); //Barrier above should be empty
		t.setSpotlightItem(focusCoast);
		Barriers focusBarrierSpot = focusCoast.getBarrier();
		focusBarrierSpot.setErosionTimer(new Timer(erodeCallDelay, new barrierErosion(focusBarrierSpot)));
		//focusBarrierSpot.getErosionTimer().start();
		
		focusCoast.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusCoast)));
		focusCoast.getErosionTimer().start();
		
		DrawableItem arrow = new DrawableItem(EstuaryGame.mc.getGabionsSpawnR().getPosX(), EstuaryGame.mc.getGabionsSpawnR().getPosY(), 32, 50);
		arrow.setStartandEnd(EstuaryGame.mc.getGabionsSpawnR().getPosX()+10, EstuaryGame.mc.getGabionsSpawnR().getPosY()+10, focusBarrierSpot.getPosX()+10, focusBarrierSpot.getPosY()+10);
		t.setArrow(arrow);
		
		erosionTimers.add(focusBarrierSpot.getErosionTimer());
		erosionTimers.add(focusCoast.getErosionTimer());
		allTimers.addAll(erosionTimers);
	}
	
	public void erosion2Setup(){
		t.mc.setGabionSpawnable(false);
		t.mc.setWallSpawnable(true);
		
		Coast focusCoast = getItems().getCoast().get(6);
		Barriers focusBarrierSpot = focusCoast.getBarrier();
		t.setSpotlightItem(focusBarrierSpot);
		focusCoast.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusCoast)));
		focusBarrierSpot.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusBarrierSpot,focusCoast, 2000)));
		focusBarrierSpot.getErosionTimer().start();
		
		DrawableItem arrow = new DrawableItem(EstuaryGame.mc.getWallSpawnR().getPosX(), EstuaryGame.mc.getWallSpawnR().getPosY(), 32, 50);
		arrow.setStartandEnd(EstuaryGame.mc.getWallSpawnR().getPosX()+10, EstuaryGame.mc.getWallSpawnR().getPosY()+10, focusBarrierSpot.getPosX()+10, focusBarrierSpot.getPosY()+10);
		t.setArrow(arrow);
		
		erosionTimers.add(focusBarrierSpot.getErosionTimer());
		erosionTimers.add(focusCoast.getErosionTimer());
		allTimers.addAll(erosionTimers);
	}
	
	public void erosion3Setup(){
		t.mc.setGabionSpawnable(true);
		t.mc.setWallSpawnable(true);
		
		Coast focusCoast1 = getItems().getCoast().get(5);
		Coast focusCoast2 = getItems().getCoast().get(6);
		//not sure if this will work well, but want to draw spotlight at the center of both these items
		t.setSpotlightItem(new DrawableItem(focusCoast1.getPosX(), focusCoast1.getPosY(), focusCoast1.getWidth()+focusCoast2.getWidth(), focusCoast1.getHeight()));
		Barriers focusBarrierSpot1 = focusCoast1.getBarrier();
		Barriers focusBarrierSpot2 = focusCoast2.getBarrier();
		if(focusBarrierSpot1.getType()!=eBarrierType.Gabion || focusBarrierSpot2.getType()!=eBarrierType.Wall){
			System.out.println("ERROR IN BARRIERS");
		}
		
		focusCoast1.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusCoast1)));
		focusCoast2.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusCoast2)));
		
		focusBarrierSpot1.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusBarrierSpot1,focusCoast1, 7000)));
		focusBarrierSpot1.getErosionTimer().start();
		focusBarrierSpot2.setErosionTimer(new Timer(erodeCallDelay, new erosion(focusBarrierSpot2,focusCoast2, 2500)));
		focusBarrierSpot2.getErosionTimer().start();
		
		erosionTimers.add(focusBarrierSpot1.getErosionTimer());
		erosionTimers.add(focusCoast1.getErosionTimer());
		erosionTimers.add(focusBarrierSpot2.getErosionTimer());
		erosionTimers.add(focusCoast2.getErosionTimer());
		
		allTimers.addAll(erosionTimers);
	}
	
	public void powerSetup(eTutorialState state){
		normalKeyBind();
		if(state == eTutorialState.POWERS_REMOVE){
			removeSetup();
		}else{
			rebuildSetup();
		}
		this.powerMover = new spawnPowers(state);
		powersFloating = new Timer(this.getFloatDelay(), powerMover);
		powersFloating.start();
		allTimers.add(powersFloating);
	}
	
	public void removeSetup(){}
	
	public void rebuildSetup(){
		//need to erode some walls
		int[] nums = {1, 3, 4, 6, 8};
		for(int i : nums){
			getItems().getAllBarriers().get(i).erode();
		}
		
	}
	
	public void healthSetup(){
		System.out.println("in health setup");
		t.setSpotlightItem(getItems().getHealthBar());
	}
	
	public void timerSetup(){
		t.setSpotlightItem(getItems().getScreenTimer());
	}
	
	public void doneSetup(){
		t.spotlightSwitched = true;
		t.setSpotlightItem(null);
	}
	
	public class mainTimer extends GameController.mainTimer implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			super.actionPerformed(e);
		}
		
		@Override
		public void checkItems(){
			checkSpotlight();
			switch(t.getState()){
			case HEALTH:
				checkHealthLimit();
				break;
			case TIMER:
				checkTimerLimit();
				break;
			default:
				checkExcessTime();
			}
			timeInStage+=paintDelay;
		}
		
		public void checkHealthLimit(){
			if(timeInStage >= healthStageTime){
				stageComplete();
			}
		}
		
		public void checkTimerLimit(){
			if(timeInStage >= timerStageTime){
				stageComplete();
			}
		}
		
		public void checkSpotlight(){
			if(timeInStage >= delaySpotlight && !t.spotlightSwitched){
				t.spotlight = true;
			}
		}
		
		public void checkExcessTime(){
			if(t.getState() != eTutorialState.IDLE && timeInStage >= idleTime){
				//stageIdle();
			}
		}
		
		public void healthTimerStart(int hTime){
			
		}
		
	}
	
	public class spawnDebris extends GameController.spawnDebris implements ActionListener{

		@Override
		public void spawnTimeReached(){}
		
		@Override
		public Debris newDebris(){
			Debris d = super.newDebris();
			t.setSpotlightItem(d);
			return d;
		}
		
		@Override
		public void move(Debris d){
			super.move(d);
			if(d.getState()==eFloaterState.LIFTED){
				t.setSpotlight(false);
			}
		}
		
		@Override
		public void throwing(Debris d, ArrayList<Debris> toDelete){
			if(d.getCorrectBin()){
				super.throwing(d, toDelete);
				if(toDelete.contains(d)){
					debrisFloating.stop();
					stageComplete();
					System.out.println("Completed Debris Tutorial Stage");
				}				
			}
			else{
				//If they try to send the debris to the wrong bin, don't allow them to
				t.wrongBin();
				d.setState(eFloaterState.LIFTED);
				thisGame.caughtSetup(d);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int distance = GameController.dimY;
			for(Debris d : thisGame.getItems().getAllDebris()){
				if(d.getPosY() < distance && t.getState()==eTutorialState.DEBRIS){
					distance = d.getPosY();
				}
			}
			
			if(distance >= GameController.dimY/4){
				thisGame.getItems().addDebris(newDebris());
			}
			super.actionPerformed(e);
		}
		
	}
	
	public class spawnPowers extends GameController.spawnPowers implements ActionListener{
		eTutorialState eState;
		private int time = 0;
		public boolean animationStarted = false;
		
		public spawnPowers(eTutorialState e){
			super();
			eState = e;
			quickSpawn();
			time = 0;
		}
		
		@Override
		public void spawnTimeReached(){}
		
		@Override
		public void quickSpawn(){
			switch(eState){
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
		public Powers newPower(Powers p){
			p = super.newPower(p);
			t.setSpotlightItem(p);
			return p;
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			super.actionPerformed(e);

			if(time!= 0 & !thisGame.getItems().getAllPowers().contains(t.getSpotlightItem())){
				if(!animationStarted){
					animationStarted = true;
					if(t.getState() == eTutorialState.POWERS_REMOVE){
						t.setSpotlightItem(removeHelper);
					}
					else{
						t.setSpotlight(false);
						t.setSpotlightItem(null);
					}
				}
				if(t.getState() == eTutorialState.POWERS_REMOVE){
					if(removeHelper.getState() == eHelperState.WALKING_OFF){
						t.setSpotlight(false);
					}
					if(!removeMode){
						System.out.println("Stopping power: " + eState);
						powersFloating.stop();
						t.setSpotlight(false);
						stageComplete();
					}
				}else if(t.getState() == eTutorialState.POWERS_REBUILD){
					if(!rebuildMode){
						System.out.println("Stopping power: " + eState);
						powersFloating.stop();
						t.setSpotlight(false);
						stageComplete();
					}
				}

			}
			else{
				int distance = GameController.dimY;
				for(Powers p : thisGame.getItems().getAllPowers()){
					if(p.getPosY() < distance){
						distance = p.getPosY();
					}
				}
				
				if(distance >= GameController.dimY/4 && !this.removeMode && !this.rebuildMode){
					quickSpawn();
				}
			}
			time+=floatDelay;
		}
	}
	
	public class erosion implements ActionListener{
		private Coast erodingCoast;
		private Coast protectedCoast;
		private Barriers erodingBarrier;
		private int erodeDelay = 3000;
		private int time = 0;
		private boolean hold = false;
		private int spotlightDelay = 500;
		
		public erosion(Coast c){
			erodingCoast = c;
			erodingBarrier = null;
		}
		
		public erosion(Barriers b, Coast c, int time){
			erodingBarrier = b;
			erodingCoast = null;
			protectedCoast = c;
			erodeDelay = time;
		}
		public void coastErosion(){
			if(time >= erodeDelay){
				erodingCoast.erode();
				time = 0;
				erodeDelay = 10000;
				hold = true;
				//t.spotlight = false;
				//t.spotlightSwitched = true;
				//TODO: something to point to painting the drag and drop arrow
			}
			if(erodingCoast.isProtected()){
				if(time == 0){
					return;
				}
				if(t.getState() == eTutorialState.EROSION_CHOICE){
					stageComplete();
				}
				//They added the barrier! Yay!
				else if(erodingCoast.getBarrier().getType()==eBarrierType.Gabion){
					//Finished the gabion stage
					erodingCoast.getErosionTimer().stop();
					erodingCoast.getBarrier().getErosionTimer().stop();
					stageComplete();
				}
				else if(erodingCoast.getBarrier().getType()==eBarrierType.Wall){
					//Finished the wall stage
					erodingCoast.getErosionTimer().stop();
					erodingCoast.getBarrier().getErosionTimer().stop();
					stageComplete();
				}
			}else{
				time+=erodeCallDelay;
			}
			if(hold){
				checkSpotlight();
			}
		}
		
		public void barrierErosion(){
			time+=erodeCallDelay;
			if(time >= erodeDelay){
				erodingBarrier.erode();
				t.setSpotlight(false);
				t.spotlightSwitched = true;
				erodingBarrier.getErosionTimer().stop();
				System.out.println("starting protected coast timer: " );
				protectedCoast.getErosionTimer().start();
			}
			else if(erodingBarrier.getState()!=eBarrierState.ONE_HIT & time >= (1/2)*erodeDelay){
				erodingBarrier.erodeHalf();
			}
			
		}
		
		public void checkSpotlight(){
			if(time >= delaySpotlight){
				t.spotlight = false;
				t.spotlightSwitched = true;
				hold = false;
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			if(erodingCoast!=null){
				coastErosion();
			}else if(erodingBarrier!=null){
				barrierErosion();
			}
		}
	}
	
	public class unpause extends AbstractAction{
		
		@Override
		public void actionPerformed(ActionEvent e){
			System.out.println("hit enter!");
			t.setState(t.getLastState());
			startStage();
		}
	}

}
