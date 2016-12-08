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
	
	/**
	 * @return the mainPlayer
	 */
	public Player getMainPlayer() {
		return mainPlayer;
	}
	
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
	
	public int emptyBarriers(){
		int i = 0;
		for(Barriers b : barriers){
			if(b.getType()==eBarrierType.EMPTY){
				i++;
			}
		}
		return i;
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
	
	public ArrayList<Debris> getRestingDebris(){
		ArrayList<Debris> resting = new ArrayList<Debris>();
		for(Debris d : debris){
			if(d.getState() == eFloaterState.RESTING){
				resting.add(d);
			}
		}
		return resting;
	}
	public ArrayList<Coast> getCoast(){
		return coast;
	}
	
	public void addCoast(Coast c){
		coast.add(c);
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

	public Helper getRemoveHelper() {
		return removeHelper;
	}

	public void setRemoveHelper(Helper removeHelper) {
		this.removeHelper = removeHelper;
	}
	
	public void deleteRemoveHelper(){
		this.removeHelper = null;
	}

	public Tool getRebuildTool() {
		return rebuildTool;
	}

	public void setRebuildTool(Tool rebuildTool) {
		this.rebuildTool = rebuildTool;
	}
	
	public void deleteRebuildTool(){
		this.rebuildTool = null;
	}
}
