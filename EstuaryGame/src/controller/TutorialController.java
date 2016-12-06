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
	public int timeInStage;
	
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
		theBigTimer = new Timer(paintDelay, new mainTimer());
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
		
		
		public void tutorialTimer(){
			if(timeInStage >= delaySpotlight && !t.getSpotlightSwitched()){
				t.setSpotlight(true);
			}
			switch (t.getState()){
			case DEBRIS:
				debrisStage();
				break;
			case EROSION:
				erosionStage();
				break;
			case POWERS_REMOVE:
				removeStage();
				break;
			case POWERS_REBUILD:
				rebuildStage();
				break;
			case HEALTH:
				healthStage();
			case TIMER:
				timerStage();
				break;
			default:
				break;
			}
			timeInStage+=paintDelay;
		}
		
		public void debrisStage(){}
		
		public void erosionStage(){

			// this part seems like its gonna be pretty complicated
		}
		
		public void removeStage(){
			
		}
		
		public void rebuildStage(){
			
		}
		
		public void healthStage(){
			//turn on the arrow and text
		}
		
		public void timerStage(){
			//turn on the arrow and text
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
					System.out.println("Completed Tutorial Stage");
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
		public void quickSpawn(){
			switch(eState){
			case POWERS_REMOVE:
				super.quickSpawnRemove();
				break;
			case POWERS_REBUILD:
				super.quickSpawnRebuild();
				break;
			default:
				System.out.println("Incorrect creation of Powers Timer");
				break;
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			super.actionPerformed(e);
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
	
	public class erosion implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			
		}
	}
	

}
