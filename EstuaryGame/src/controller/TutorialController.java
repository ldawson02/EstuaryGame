package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import controller.GameController.mainTimer;
import eNums.eFloaterState;
import eNums.eHealthChanges;
import eNums.eTutorialState;
import model.Debris;
import model.Powers;
import view.EstuaryGame;
import view.Tutorial;

public class TutorialController extends GameController {

	Tutorial t;
	private int floatDelay = 100;
	private int delaySpotlight = 1000;
	private int healthStageTime = 5000;
	private int timerStageTime = 5000;
	public int timeInStage;
	private mainTimer tutorialPaintTimer;
	
	public TutorialController(Tutorial mainGame) {
		super(mainGame);
		t = mainGame;
		debrisFloating.setDelay(floatDelay);
	}

	@Override
	public void startGame(){
		System.out.println("POLYMORPHISM");
		debrisSetup();
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
	
	public void startStage(){
		System.out.println("State is: " + t.getState());
		switch(t.getState()){
		case DEBRIS:
			debrisSetup();
			break;
		case EROSION:
			erosionSetup();
			break;
		case POWERS_REMOVE:
		case POWERS_REBUILD:
			powerSetup(t.getState());
			break;
		case HEALTH:
			healthSetup();
			break;
		case TIMER:
			timerSetup();
			break;
		default:
			break;
		}
	}
	
	public void debrisSetup(){
		this.debrisMover = new spawnDebris();
		System.out.println("float delay: " + this.getFloatDelay());
		debrisFloating = new Timer(this.getFloatDelay(), debrisMover);
		debrisFloating.start();
	}
	
	public void erosionSetup(){
		System.out.println("Erosion setup");
		this.stageComplete();
	}
	
	public void powerSetup(eTutorialState state){
		this.powerMover = new spawnPowers(state);
		powersFloating = new Timer(this.getFloatDelay(), powerMover);
		powersFloating.start();
	}
	
	public void healthSetup(){}
	
	public void timerSetup(){}
	
	public class mainTimer extends GameController.mainTimer implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			super.actionPerformed(e);
		}
		
		@Override
		public void checkItems(){
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
		
		public void checkExcessTime(){
			if(timeInStage >= 100000){
				System.out.println("Tutorial Stalled!");
				stageComplete();
			}
		}
		
		public void healthTimerStart(int hTime){
			
		}
		
	}
	
	public class spawnDebris extends GameController.spawnDebris implements ActionListener{

		@Override
		public void spawnTimeReached(){}
		
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
			super.actionPerformed(e);
			int distance = 0;
			for(Debris d : thisGame.getItems().getAllDebris()){
				if(d.getPosY() > distance && t.getState()==eTutorialState.DEBRIS){
					distance = d.getPosY();
				}
			}
			
			if(distance >= t.getScreenY()/4){
				thisGame.getItems().addDebris(newDebris());
			}
		}
		
	}
	
	public class spawnPowers extends GameController.spawnPowers implements ActionListener{
		eTutorialState eState;
		
		public spawnPowers(eTutorialState e){
			super();
			eState = e;
			quickSpawn();
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
			if(!thisGame.getItems().getAllPowers().contains(t.getSpotlightItem())){
				powersFloating.stop();
				stageComplete();
			}
			else{
				int distance = t.getScreenY();
				for(Powers p : thisGame.getItems().getAllPowers()){
					if(p.getPosY() < distance){
						distance = p.getPosY();
					}
				}
				
				if(distance >= t.getScreenY()/4){
					quickSpawn();
				}
			}
		}
	}
	
	public class erosion implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			
		}
	}
	

}
