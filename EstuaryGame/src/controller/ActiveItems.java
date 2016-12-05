package controller;

import java.util.ArrayList;
import java.util.Iterator;

import eNums.eBarrierType;
import eNums.eDebrisType;
import eNums.eFloaterState;
import model.*;
public class ActiveItems {
	//class that holds all our actual active items in an instance of the game
	Player mainPlayer;

	/**
	 * @return the mainPlayer
	 */
	public Player getMainPlayer() {
		return mainPlayer;
	}

	/**
	 * @param mainPlayer the mainPlayer to set
	 */
	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

	private HealthBar hBar;
	
	private ScreenTimer screenTimer;
	/**
	 * @return the screenTimer
	 */
	public ScreenTimer getScreenTimer() {
		return screenTimer;
	}

	/**
	 * @param screenTimer the screenTimer to set
	 */
	public void addScreenTimer(ScreenTimer screenTimer) {
		this.screenTimer = screenTimer;
	}

	private ArrayList<Barriers> barriers;
	private ArrayList<Debris> debris;
	private ArrayList<Coast> coast;
	private CoastL coastL;
	private CoastR coastR;
	private ArrayList<Powers> powerups;
	private Bin TrashBin;
	private Bin RecycleBin;
	
	public ActiveItems() {
		//At the very least, initialize the lists and add the coasts
		barriers = new ArrayList<Barriers>();
		debris = new ArrayList<Debris>();
		coast = new ArrayList<Coast>();
		coastL = new CoastL();
		coastR = new CoastR();
		powerups = new ArrayList<Powers>();
		TrashBin = new Bin(eDebrisType.TRASH);
		RecycleBin = new Bin(eDebrisType.RECYCLING);
	}
	
	/**
	 * Allows access to the main Player for the Model and View
	 * @return
	 */
	public Player getPlayer(){
		return mainPlayer;
	}
	
	/**
	 * Allows access to all the Barriers for the Model and View
	 * @return
	 */
	public ArrayList<Barriers> getAllBarriers(){
		return barriers;
	}
	
	public void setAllBarriers(){
		for(Barriers b: barriers){
			b.setType(eBarrierType.EMPTY);
		}
	}
	
	public int numActiveBarriers() {
		int count = 0;
		for(Barriers b: barriers) {
			if (b.getType() != eBarrierType.EMPTY) {
				count++;
			}
		}
		return count;
	}
	
	public boolean removeBarrier(Barriers b){
		return barriers.remove(b);
	}
	
	public void addBarrier(Barriers b){
		barriers.add(b);
	}
	
	public ArrayList<Debris> getAllDebris(){
		return debris;
	}
	
	public ArrayList<Coast> getCoast(){
		return coast;
	}
	
	public void addCoast(Coast c){
		coast.add(c);
	}
	/**
	 * @return the coastL
	 */
	public CoastL getCoastL() {
		return coastL;
	}

	/**
	 * @return the coastR
	 */
	public CoastR getCoastR() {
		return coastR;
	}

	public boolean removeDebris(Debris d){
		return debris.remove(d);
	}
	public void removeAllRestingDebris(){
		Iterator debrisitr = debris.iterator();
		while(debrisitr.hasNext()){
			Debris d = (Debris)debrisitr.next();
			if(d.getState() == eFloaterState.RESTING){
				debrisitr.remove();
			}
		}
	}
	public void removeAllDebris(){
		Iterator debrisitr = debris.iterator();
		while(debrisitr.hasNext()){
			Debris d = (Debris)debrisitr.next();
			d.setState(eFloaterState.RESTING);
			debrisitr.remove();
		}
	}
	public void removeAllPowers(){
		Iterator powersitr = powerups.iterator();
		while(powersitr.hasNext()){
			Powers p = (Powers)powersitr.next();
			p.setState(eFloaterState.RESTING);
			powersitr.remove();
		}
	}
	
	public void addDebris(Debris d){
		debris.add(d);
	}
	
	public HealthBar getHealthBar(){
		return hBar;
	}

	public void addHealthBar(HealthBar hb) {
		this.hBar = hb;
	}
	
	public ArrayList<Powers> getAllPowers(){
		return powerups;
	}
	
	public void addPower(Powers p){
		powerups.add(p);
	}
	public void removePower(Powers p){
		powerups.remove(p);
	}
	
	public Bin getTrashBin(){
		return TrashBin;
	}
	
	public Bin getRecycleBin(){
		return RecycleBin;
	}
		
}