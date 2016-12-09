package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

import eNums.eAnimation;
import eNums.eBarrierType;
import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.eHelperState;
import eNums.ePlayerState;
import model.Barriers;
import model.Bin;
import model.Debris;
import model.Floater;
import model.Helper;
import model.Player;
import model.Powers;
import model.Rebuild;
import model.Remove;
import model.StormVisual;
import model.Tool;

/**
 * The big bad index of images. Essentially a custom-rolled data structure of its own.
 * All of the images involved in the game are loaded into this one class, which resides 
 * in the view.
 * Contains an instance of ImageSequence for each type of viewable object.
 * Every animation will be mapped out to an enum, save for the coast images.
 * 
 * Due to the complexity of the coast's images, the loading of the coast is handled
 * separately, as a 2D array.
 * 
 * @author Ian
 * @version 1.3
 * @since 11/16/16
 */
public class ImageLibrary {

	private HashMap<eAnimation, ImageSequence> library;
	private BufferedImage[][] coastLibrary;
	
	/**
	 * Private constructor to force the use of the factor method
	 */
	private ImageLibrary() {
		library = new HashMap<eAnimation, ImageSequence>();
		coastLibrary = new BufferedImage[4][4];
	}
	
	/**
	 * Factory method to load up the whole library. Handles initialization
	 * and scaling.
	 * @return ImageLibrary
	 */
	public static ImageLibrary loadLibrary() {
		ImageLibrary lib = new ImageLibrary();

		eAnimation[] allAnims = eAnimation.values();
		
		for (eAnimation eAnim: allAnims) {
			lib.getLibrary().put(eAnim, new ImageSequence(eAnim));
		}
		
		lib.loadCoastLibrary();
		lib.fixFrameDelays();
				
		lib.initScaleLibrary();
				
		return lib;
	}
	
	/**
	 * Because the coast library is handled most efficiently through a 2D array,
	 * it is loaded separately.
	 */
	private void loadCoastLibrary() {
		String srcpath = "resources" + File.separator + "erosion" + File.separator;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				String filepath = srcpath + "erosion " + i + "-" + j + ".png";
				coastLibrary[i][j] = createImage(filepath);
			}
		}
	}
	
	/**
	 * Some animations run at different speeds, this updates those specifically.
	 */
	private void fixFrameDelays() {
		library.get(eAnimation.hammer).setFrameDelay(4);
	}
	
	/**
	 * This initializes the scaling on ALL the Images, hard-coded for a
	 * 800x600 resolution. Not efficient.
	 */
	private void initScaleLibrary() {
		//TODO: this has to scale the images initially
		//Scale player
		ArrayList<Image> scaled = new ArrayList<Image>();
		for (Image idles : library.get(eAnimation.playerIdle).getSeq()) {
			scaled.add(idles.getScaledInstance(Player.defaultWidth, Player.idleHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.playerIdle).setSeq(scaled);
		
		ArrayList<Image> scaled2 = new ArrayList<Image>();
		for (Image lifts : library.get(eAnimation.playerLift).getSeq()) {
			scaled2.add(lifts.getScaledInstance(Player.defaultWidth, Player.liftingHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.playerLift).setSeq(scaled2);
		
		ArrayList<Image> scaled3 = new ArrayList<Image>();
		//Scale bins
		for (Image bin1 : library.get(eAnimation.recycleBin).getSeq()) {
			scaled3.add(bin1.getScaledInstance(Bin.defaultWidth, Bin.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.recycleBin).setSeq(scaled3);
		
		ArrayList<Image> scaled4 = new ArrayList<Image>();
		for (Image bin2 : library.get(eAnimation.trashBin).getSeq()) {
			scaled4.add(bin2.getScaledInstance(Bin.defaultWidth, Bin.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.trashBin).setSeq(scaled4);
		
		ArrayList<Image> scaled5 = new ArrayList<Image>();
		//Scale floaters
		for (Image recyc1 : library.get(eAnimation.recyclingCoast).getSeq()) {
			scaled5.add(recyc1.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.recyclingCoast).setSeq(scaled5);
		
		ArrayList<Image> scaled6 = new ArrayList<Image>();
		for (Image recyc2 : library.get(eAnimation.recyclingFloat).getSeq()) {
			scaled6.add(recyc2.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.recyclingFloat).setSeq(scaled6);
		
		ArrayList<Image> scaled7 = new ArrayList<Image>();
		for (Image recyc3 : library.get(eAnimation.recyclingLifted).getSeq()) {
			scaled7.add(recyc3.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.recyclingLifted).setSeq(scaled7);
		
		ArrayList<Image> scaled8 = new ArrayList<Image>();
		for (Image trash1 : library.get(eAnimation.trashCoast).getSeq()) {
			scaled8.add(trash1.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.trashCoast).setSeq(scaled8);
		
		ArrayList<Image> scaled9 = new ArrayList<Image>();
		for (Image trash2 : library.get(eAnimation.trashFloat).getSeq()) {
			scaled9.add(trash2.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.trashFloat).setSeq(scaled9);
		
		ArrayList<Image> scaled10 = new ArrayList<Image>();
		for (Image trash3 : library.get(eAnimation.trashLifted).getSeq()) {
			scaled10.add(trash3.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.trashLifted).setSeq(scaled10);
		
		ArrayList<Image> scaled11 = new ArrayList<Image>();
		for (Image rebuild : library.get(eAnimation.rebuild).getSeq()) {
			scaled11.add(rebuild.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.rebuild).setSeq(scaled11);
		
		ArrayList<Image> scaled12 = new ArrayList<Image>();
		for (Image remove1 : library.get(eAnimation.remove).getSeq()) {
			scaled12.add(remove1.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.remove).setSeq(scaled12);
		
		ArrayList<Image> scaled13 = new ArrayList<Image>();
		for (Image remove2 : library.get(eAnimation.removeLift).getSeq()) {
			scaled13.add(remove2.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.removeLift).setSeq(scaled13);
		
		ArrayList<Image> scaled14 = new ArrayList<Image>();
		for (Image fullgab : library.get(eAnimation.fullGabion).getSeq()) {
			scaled14.add(fullgab.getScaledInstance(Barriers.defaultWidth, Barriers.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.fullGabion).setSeq(scaled14);
		
		ArrayList<Image> scaled15 = new ArrayList<Image>();
		for (Image halfgab : library.get(eAnimation.halfGabion).getSeq()) {
			scaled15.add(halfgab.getScaledInstance(Barriers.defaultWidth, Barriers.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.halfGabion).setSeq(scaled15);
		
		ArrayList<Image> scaled16 = new ArrayList<Image>();
		for (Image fullwall : library.get(eAnimation.fullWall).getSeq()) {
			scaled16.add(fullwall.getScaledInstance(Barriers.defaultWidth, Barriers.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.fullWall).setSeq(scaled16);
		
		ArrayList<Image> scaled17 = new ArrayList<Image>();
		for (Image halfwall : library.get(eAnimation.halfWall).getSeq()) {
			scaled17.add(halfwall.getScaledInstance(Barriers.defaultWidth, Barriers.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.halfWall).setSeq(scaled17);
		
		ArrayList<Image> scaled18 = new ArrayList<Image>();
		for (Image helplift : library.get(eAnimation.helperLift).getSeq()) {
			scaled18.add(helplift.getScaledInstance(Helper.defaultWidth, Helper.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.helperLift).setSeq(scaled18);

		ArrayList<Image> scaled19 = new ArrayList<Image>();
		for (Image helppick : library.get(eAnimation.helperPickUp).getSeq()) {
			scaled19.add(helppick.getScaledInstance(Helper.defaultWidth, Helper.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.helperPickUp).setSeq(scaled19);

		ArrayList<Image> scaled20 = new ArrayList<Image>();
		for (Image helpwalk : library.get(eAnimation.helperWalk).getSeq()) {
			scaled20.add(helpwalk.getScaledInstance(Helper.defaultWidth, Helper.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.helperWalk).setSeq(scaled20);
		
		ArrayList<Image> scaled21 = new ArrayList<Image>();
		for (Image helpwalkr : library.get(eAnimation.helperWalkRight).getSeq()) {
			scaled21.add(helpwalkr.getScaledInstance(Helper.defaultWidth, Helper.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.helperWalkRight).setSeq(scaled21);
		
		ArrayList<Image> scaled22 = new ArrayList<Image>();
		for (Image hammer : library.get(eAnimation.hammer).getSeq()) {
			scaled22.add(hammer.getScaledInstance(Tool.defaultWidth, Tool.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.hammer).setSeq(scaled22);
		
		ArrayList<Image> scaled23 = new ArrayList<Image>();
		for (Image storm : library.get(eAnimation.storm).getSeq()) {
			scaled23.add(storm.getScaledInstance(StormVisual.defaultWidth, StormVisual.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.storm).setSeq(scaled23);
	}
	
	/**
	 * Reaches into the ImageSequence for the specified animation and returns
	 * the next frame.
	 * @param eAnim
	 * @return Image
	 */
	public Image draw(eAnimation eAnim) {
		ImageSequence seq = library.get(eAnim);
		return (seq.draw());
	}

	/**
	 * Returns the next frame of the Player's animation based on its state
	 * @param Player p
	 * @return Image
	 */
	public Image draw(Player p) {
		if (p.getState() == ePlayerState.Idle) {
			return draw(eAnimation.playerIdle);
		}
		else if (p.getState() == ePlayerState.Lifting) {
			return draw(eAnimation.playerLift);
		}
		else {
			return draw(eAnimation.error);
		}
	}
	
	/**
	 * Returns the next frame of the Debris's animation based on its state
	 * @param Debris d
	 * @return Image
	 */
	public Image draw(Debris d) {
		if (d.getType() == eDebrisType.RECYCLING) {
			switch (d.getState()) {
			case RESTING:
			case THROWING:
				return draw(eAnimation.recyclingCoast);
			case LIFTED: 
				return draw(eAnimation.recyclingLifted);
			case MOVING:
				return draw(eAnimation.recyclingFloat);
			}
		}
		else if (d.getType() == eDebrisType.TRASH) {
			switch (d.getState()) {
			case RESTING:
			case THROWING:
				return draw(eAnimation.trashCoast);
			case LIFTED: 
				return draw(eAnimation.trashLifted);
			case MOVING:
				return draw(eAnimation.trashFloat);
			}
		}
		
		//Implied else
		return draw(eAnimation.error);
	}
	
	/**
	 * Returns the next frame of the Helper's animation
	 * @param Helper h
	 * @return Image
	 */
	public Image draw(Helper h) {
		if (h.getState() == eHelperState.WALKING) {
			return draw(eAnimation.helperWalk);
		}
		else if (h.getState() == eHelperState.PICKING_UP) {
			return draw(eAnimation.helperPickUp);
		}
		else if (h.getState() == eHelperState.HOLDING) {
			return draw(eAnimation.helperLift);
		}
		else {
			return draw(eAnimation.helperWalkRight);
		}
	}
	
	/**
	 * Returns the next frame for the Barriers' animation
	 * @param Barriers b
	 * @return Image
	 */
	public Image draw(Barriers b) {
		if (b.getType() == eBarrierType.Gabion) {
    		switch (b.getState()) {
    		case ONE_HIT:
    			return draw(eAnimation.halfGabion);
    		case NO_HIT: 
    		default:
    			return draw(eAnimation.fullGabion);
    		}
    	}
    	else {
    		switch (b.getState()) {
    		case ONE_HIT:
    			return draw(eAnimation.halfWall);
    		case NO_HIT: 
    		default:
    			return draw(eAnimation.fullWall);
    		}
    	}
		
	}
	
	/**
	 * Returns the next frame of the Powers' animation
	 * @param Powers p
	 * @return Image
	 */
	public Image draw(Powers p) {
		if (p instanceof Remove) {
			switch (p.getState()) {
			case RESTING:
			case THROWING:
			case LIFTED: 
			case INITIATED: 
				return draw(eAnimation.removeLift);
			case MOVING:
				return draw(eAnimation.remove);
			}
		}
		else if (p instanceof Rebuild) {
			switch (p.getState()) {
			case RESTING:
			case THROWING:
			case LIFTED: 
			case INITIATED:
				//return draw(eAnimation.trashLifted);
			case MOVING:
				return draw(eAnimation.rebuild);
			}
		}
		//Implied else
		return draw(eAnimation.error);
	}
	
	/**
	 * Returns the correct Coast image based on the state of the current
	 * coast item and the state of the adjacent coast item.
	 * @param thisState
	 * @param leftState
	 * @return Image
	 */
	public Image drawCoast(int thisState, int leftState) {
		try {
			return coastLibrary[thisState][leftState];
		}
		catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the underlying HashMap
	 * Only for testing
	 * @return HashMap
	 */
	public HashMap<eAnimation, ImageSequence> getLibrary() {
		return library;
	}
	
	/**
	 * Gets the 2D array 
	 * Only used for testing
	 * @return the coastLibrary
	 */
	public BufferedImage[][] getCoastLibrary() {
		return coastLibrary;
	}

	/**
	 * Method for safely instantiating an Image.
	 * @param filename
	 * @return Image
	 */
	private BufferedImage createImage(String filename){
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(filename));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println("Couldn't create image from " + filename);
			e.printStackTrace();
		}
		return null;
	}
	
}
