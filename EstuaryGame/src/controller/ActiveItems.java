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
	
	/**
	 * construct the activeitems
	 */
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
	 * @return mainplayer
	 */
	public Player getPlayer(){
		return mainPlayer;
	}
	
	/**
	 * Allows access to all the Barriers for the Model and View
	 * @return barriers
	 */
	public ArrayList<Barriers> getAllBarriers(){
		return barriers;
	}
	/**
	 * set the barriers to empty
	 */
	public void setAllBarriers(){
		for(Barriers b: barriers){
			b.setType(eBarrierType.EMPTY);
		}
	}
	/**
	 * if barrier's type equals to empty, count plus.
	 * @return
	 */
	public int numActiveBarriers() {
		int count = 0;
		for(Barriers b: barriers) {
			if (b.getType() != eBarrierType.EMPTY) {
				count++;
			}
		}
		return count;
	}
	/**
	 * remove barriers
	 * @param b
	 * @return remove
	 */
	public boolean removeBarrier(Barriers b){
		return barriers.remove(b);
	}
	/**
	 * add barriers
	 * @param b
	 */
	public void addBarrier(Barriers b){
		barriers.add(b);
	}
	/**
	 * get all debris
	 * @return debris
	 */
	public ArrayList<Debris> getAllDebris(){
		return debris;
	}
	/**
	 * get coast
	 * @return coast
	 */
	public ArrayList<Coast> getCoast(){
		return coast;
	}
	/** 
	 * add coast
	 * @param c
	 */
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

	/**
	 * remove debris
	 * @param d
	 * @return
	 */
	public boolean removeDebris(Debris d){
		return debris.remove(d);
	}
	/**
	 * remove all testing debris 
	 */
	public void removeAllRestingDebris(){
		Iterator debrisitr = debris.iterator();
		while(debrisitr.hasNext()){
			Debris d = (Debris)debrisitr.next();
			if(d.getState() == eFloaterState.RESTING){
				debrisitr.remove();
			}
		}
	}
	
	/**
	 * remove debris 
	 */
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
	
	/**
	 * add debris
	 * @param d
	 */
	public void addDebris(Debris d){
		debris.add(d);
	}
	
	/**
	 * get health bar
	 * @return hbar
	 */
	public HealthBar getHealthBar(){
		return hBar;
	}
/**
 * add health bar
 * @param hb
 */
	public void addHealthBar(HealthBar hb) {
		this.hBar = hb;
	}
	
	/** 
	 * get all powers
	 * @return powerups
	 */
	public ArrayList<Powers> getAllPowers(){
		return powerups;
	}
	
	/**
	 * add power
	 * @param p
	 */
	public void addPower(Powers p){
		powerups.add(p);
	}
	/**
	 * remove power
	 * @param p
	 */
	public void removePower(Powers p){
		powerups.remove(p);
	}
	
	/**
	 * get the trash bin
	 * @return Trashbin
	 */
	public Bin getTrashBin(){
		return TrashBin;
	}
	/**
	 * get the recycle bin
	 * @return RecycleBin
	 */
	public Bin getRecycleBin(){
		return RecycleBin;
	}
		
}