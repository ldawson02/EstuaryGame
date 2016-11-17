package view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import eNums.eAnimation;
import eNums.ePlayerState;
import model.Player;

/**
 * The big bad index of images.
 * All of the images involved in the game are loaded into this one class, which resides in
 * the view.
 * Contains an instance of ItemSequences for each type of viewable object.
 * Every animation will be mapped out to an enum.
 * 
 * @author Ian
 * @version 1.1
 * @since 11/16/16
 */

public class ImageLibrary {

	private HashMap<eAnimation, ImageSequence> library;
	
	private ImageLibrary() {
		library = new HashMap<eAnimation, ImageSequence>();
	}
	
	public static ImageLibrary loadLibrary() {
		ImageLibrary lib = new ImageLibrary();

		eAnimation[] allAnims = eAnimation.values();
		
		for (eAnimation eAnim: allAnims) {
			lib.getLibrary().put(eAnim, new ImageSequence(eAnim));
		}
		
		return lib;
	}
	
	public BufferedImage draw(eAnimation eAnim) {
		ImageSequence seq = library.get(eAnim);
		return (seq.draw());
	}

	public BufferedImage draw(Player p) {
		if (p.getState() == ePlayerState.Idle) {
			return draw(eAnimation.playerIdle);
		}
		else if (p.getState() == ePlayerState.Lifting) {
			return draw(eAnimation.playerIdle);
		}
		else {
			return draw(eAnimation.error);
		}
	}
	
	/**
	 * @return the library
	 */
	public HashMap<eAnimation, ImageSequence> getLibrary() {
		return library;
	}
	
	/* The old implementation
	ArrayList<ItemSequences> library;
	
	private ImageLibrary() {
		library = new ArrayList<ItemSequences>();
	}
	
	private void setLibrary(ArrayList<ItemSequences> lib) {
		this.library = lib;
	}
	
	/**
	 * The biggest nightmare of a function, handles the loading of every image used in the game.
	 * Splits up by the type of item.
	 * 
	 * @return the loaded library
	 
	public static ImageLibrary loadLibrary() {
		ImageLibrary ImageLib = new ImageLibrary();
		ArrayList<ItemSequences> lib = new ArrayList<ItemSequences>();
		
		
		//TODO: yeah the majority here can't be done yet
		//This is gonna be virtually all hard-coded
		//Just let this be my burden
		
		
		Collections.sort(lib);
		ImageLib.setLibrary(lib);
		return ImageLib;
	}
	
	public BufferedImage draw() {
		//TODO: gotta figure out what exactly is passed in
		return(library.get(0).draw(0));
	}
	
	*/
}
