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
import eNums.ePlayerState;
import model.Barriers;
import model.Bin;
import model.Debris;
import model.Floater;
import model.Player;
import model.Powers;
import model.Rebuild;
import model.Remove;

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
	
	private ImageLibrary() {
		library = new HashMap<eAnimation, ImageSequence>();
		coastLibrary = new BufferedImage[4][4];
	}
	
	public static ImageLibrary loadLibrary() {
		ImageLibrary lib = new ImageLibrary();

		eAnimation[] allAnims = eAnimation.values();
		
		for (eAnimation eAnim: allAnims) {
			lib.getLibrary().put(eAnim, new ImageSequence(eAnim));
		}
		
		lib.loadCoastLibrary();
		
		System.out.println("All loaded.");
		
		lib.initScaleLibrary();
		
		System.out.println("All scaled.");
		
		return lib;
	}
	
	private void loadCoastLibrary() {
		System.out.println("Loading: erosions files");
		String srcpath = "resources" + File.separator + "erosion" + File.separator;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				String filepath = srcpath + "erosion " + i + "-" + j + ".png";
				coastLibrary[i][j] = createImage(filepath);
			}
		}
	}
	
	/*
	 * This initializes the scaling on ALL the Images.
	 * THIS IS HARD-CODED FOR A 600x800 RESOLUTION.
	 * This is the worst method ever, but improved efficiency considerably.
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
			scaled14.add(fullgab.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.fullGabion).setSeq(scaled14);
		
		ArrayList<Image> scaled15 = new ArrayList<Image>();
		for (Image halfgab : library.get(eAnimation.halfGabion).getSeq()) {
			scaled15.add(halfgab.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.halfGabion).setSeq(scaled15);
		
		ArrayList<Image> scaled16 = new ArrayList<Image>();
		for (Image fullwall : library.get(eAnimation.fullWall).getSeq()) {
			scaled16.add(fullwall.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.fullWall).setSeq(scaled16);
		
		ArrayList<Image> scaled17 = new ArrayList<Image>();
		for (Image halfwall : library.get(eAnimation.halfWall).getSeq()) {
			scaled17.add(halfwall.getScaledInstance(Floater.defaultWidth, Floater.defaultHeight, Image.SCALE_SMOOTH));
		}
		library.get(eAnimation.halfWall).setSeq(scaled17);
	}
	
	public void scaleLibrary(double scaleFactor) {
		//TODO: this has to be able to update every image by a factor

		eAnimation[] allAnims = eAnimation.values();
		
		for (eAnimation eAnim: allAnims) {
			ImageSequence sq = this.getLibrary().get(eAnim);
			Image exFrame = sq.getSeq().get(0);
			//Get old dimensions
			//TODO
			//Produce new dimensions
			//TODO
		}
		
	}
	
	public Image draw(eAnimation eAnim) {
		ImageSequence seq = library.get(eAnim);
		return (seq.draw());
	}

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
	
	public Image drawCoast(int thisState, int leftState) {
		try {
			return coastLibrary[thisState][leftState];
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Coast state not found");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return the library
	 */
	public HashMap<eAnimation, ImageSequence> getLibrary() {
		return library;
	}
	
	/**
	 * @return the coastLibrary
	 * Only used for testing
	 */
	public BufferedImage[][] getCoastLibrary() {
		return coastLibrary;
	}

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
