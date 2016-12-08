package controller;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Iterator;

import eNums.eBarrierType;
import eNums.eDebrisType;
import eNums.eFloaterState;
import model.*;

public class ActiveItems implements Serializable {
	//class that holds all our actual active items in an instance of the game
	Player mainPlayer;
	private HealthBar hBar;
	private ScreenTimer screenTimer;
	private ArrayList<Barriers> barriers;
	private ArrayList<Debris> debris;
	private ArrayList<Coast> coast;
	private ArrayList<Powers> powerups;
	private Bin TrashBin;
	private Bin RecycleBin;
	private Helper removeHelper;
	private Tool rebuildTool;
	private StormVisual stormv;
	
	/**
	 * @return the mainPlayer
	 */
	public Player getMainPlayer() {
		return mainPlayer;
	}
	
	/**
	 * get player
	 * @return mainplayer
	 */
	public Player getPlayer() {
		//an accident
		return mainPlayer;
	}

	/**
	 * @param mainPlayer the mainPlayer to set
	 */
	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

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


	
	public ActiveItems() {
		//At the very least, initialize the lists and add the coasts
		barriers = new ArrayList<Barriers>();
		debris = new ArrayList<Debris>();
		coast = new ArrayList<Coast>();
		powerups = new ArrayList<Powers>();
		TrashBin = new Bin(eDebrisType.TRASH);
		RecycleBin = new Bin(eDebrisType.RECYCLING);
	}
	
	/**
	 * Allows access to all the Barriers for the Model and View
	 * @return
	 */
	public ArrayList<Barriers> getAllBarriers(){
		return barriers;
	}
	
	/**
	 * if barrier's type equals to the ebarriertype and it is empty,then i plus.
	 * @return i
	 */
	
	public int emptyBarriers(){
		int i = 0;
		for(Barriers b : barriers){
			if(b.getType()==eBarrierType.EMPTY){
				i++;
			}
		}
		return i;
	}
	/**
	 * set the barrier type to empty
	 */
	public void setAllBarriers(){
		for(Barriers b: barriers){
			b.setType(eBarrierType.EMPTY);
		}
	}
	/**
	 * if barrier's type is not empty, then count one 
	 * @return count
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
	 * remove the barriers 
	 * @param b
	 * @return
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
	 * @return
	 */
	public ArrayList<Debris> getAllDebris(){
		return debris;
	}
	/**
	 * if debris's state is resting, then add and get resting debris
	 * @return
	 */
	public ArrayList<Debris> getRestingDebris(){
		ArrayList<Debris> resting = new ArrayList<Debris>();
		for(Debris d : debris){
			if(d.getState() == eFloaterState.RESTING){
				resting.add(d);
			}
		}
		return resting;
	}
	/**
	 * get coast
	 * @return coast
	 */
	public ArrayList<Coast> getCoast(){
		return coast;
	}
	/**
	 * add the coast
	 * @param c
	 */
	public void addCoast(Coast c){
		coast.add(c);
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
	 * remove the resting debris
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
	 * use iterator to remove all debris
	 */
	public void removeAllDebris(){
		Iterator debrisitr = debris.iterator();
		while(debrisitr.hasNext()){
			Debris d = (Debris)debrisitr.next();
			d.setState(eFloaterState.RESTING);
			debrisitr.remove();
		}
	}
	/**
	 * use iterator to remove all powers
	 */
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
	 * @return hBar
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
	 * remove powers
	 * @param p
	 */
	public void removePower(Powers p){
		powerups.remove(p);
	}
	/**
	 * get the trash bin
	 * @return
	 */
	public Bin getTrashBin(){
		return TrashBin;
	}
	/**
	 * get the recycle bin
	 * @return
	 */
	public Bin getRecycleBin(){
		return RecycleBin;
	}
	/**
	 * get the remove helper
	 * @return
	 */
	public Helper getRemoveHelper() {
		return removeHelper;
	}
	/**
	 * set the remove helper
	 * @param removeHelper
	 */
	public void setRemoveHelper(Helper removeHelper) {
		this.removeHelper = removeHelper;
	}
	/**
	 * delete removehelper
	 */
	public void deleteRemoveHelper(){
		this.removeHelper = null;
	}
	/**
	 * get build tool
	 * @return
	 */
	public Tool getRebuildTool() {
		return rebuildTool;
	}
	/**
	 * set rebuild tool
	 * @param rebuildTool
	 */
	public void setRebuildTool(Tool rebuildTool) {
		this.rebuildTool = rebuildTool;
	}
	/**
	 * get storm visual
	 * @return stormv
	 */
	public StormVisual getStormv() {
		return stormv;
	}
	/**
	 * set the stormv
	 * @param stormv
	 */
	public void setStormv(StormVisual stormv) {
		this.stormv = stormv;
	}
/**
 * delete rebuild tool
 */
	public void deleteRebuildTool(){
		this.rebuildTool = null;
	}
	/**
	 * delete storm view
	 */
	public void deleteStormv(){
		this.stormv = null;
	}
}