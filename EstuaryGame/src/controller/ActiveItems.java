package controller;

import java.util.ArrayList;

import model.*;
public class ActiveItems {
	//class that holds all our actual active items in an instance of the game
	Player mainPlayer;

	public static ArrayList<Barriers> barriers;
	public static ArrayList<Debris> debris;
	public static HealthBar hBar;
	public static ArrayList<Coast> coasts;
	public static ArrayList<Powers> powerups;
	
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
	
	public boolean removeBarrier(Barriers b){
		return barriers.remove(b);
	}
	
	public void addBarrier(Barriers b){
		barriers.add(b);
	}
	
	public ArrayList<Debris> getAllDebris(){
		return debris;
	}
	
	public boolean removeDebris(Debris d){
		return debris.remove(d);
	}
	
	public void addDebris(Debris d){
		debris.add(d);
	}
	
	public HealthBar getHealthBar(){
		return hBar;
	}
	
	public ArrayList<Coast> getAllCoasts(){
		return coasts;
	}
	
	public ArrayList<Powers> getAllPowers(){
		return powerups;
	}
	
	public void addPower(Powers p){
		powerups.add(p);
	}
	
}
