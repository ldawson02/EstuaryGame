package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

import eNums.eAnimation;
import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.ePlayerState;
import model.Debris;
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
 * @version 1.2
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
		
		lib.scaleLibrary(1.0);
		
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
	 */
	private void initScaleLibrary() {
		//TODO: this has to scale the images initially
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
