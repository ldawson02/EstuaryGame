package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import eNums.eAnimation;

/**
 * Acts as a holder for an animation.
 * Automatically iterates through the frames with consecutive calls of draw().
 * 
 * @author Ian
 * @version 1.1
 * @since 11/16/2016
 *
 */
public class ImageSequence {

	private eAnimation animID;
	private ArrayList<Image> seq;
	private int curFrame;
	private int numFrames;
	private int frameDelay = 10;
	private int defaultHeight;
	private int defaultWidth;
	
	private ImageSequence() {};
	
	public ImageSequence(eAnimation ID) {
		this.animID = ID;
		seq = new ArrayList<Image>();
		curFrame = 0;
		loadSequence(ID.getPath());
	}
	
	private void loadSequence(String filepath) {
		//TODO: load in the images using paths <3

		String srcpath = "resources" + File.separator;
		String fullpath = srcpath + filepath;
		System.out.println("Loading: " + fullpath);
		numFrames = 0;
		
		try {
			Stream<Path> paths = Files.list(Paths.get(fullpath));
			Iterator<Path> itpath = paths.iterator();
			
			while (itpath.hasNext()) {
				Path next = itpath.next();
				//Make sure the path is an image, not another folder
				if (Files.isDirectory(next)) {
					throw new ResourceException(next);
				}
				
				BufferedImage toAdd = createImage(next.toString());
				if (seq.isEmpty()) {
					defaultHeight = toAdd.getHeight();
					defaultWidth = toAdd.getWidth();
				}
				seq.add(toAdd);
				
				numFrames++;
			}
			
			paths.close();
		}
		catch (IOException e) {
			System.out.println("Error loading " + fullpath);
			e.printStackTrace();
		}
		catch (ResourceException e) {
			System.out.println("Resource error: " + e.errorPath.toString());
			e.printStackTrace();
		}
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

	public Image draw() {
		Image toReturn = seq.get(curFrame / (1 + frameDelay));
		curFrame += 1;
		if (curFrame == numFrames*(1 + frameDelay)) {
			curFrame = 0;
		}
		return toReturn;
	}
	
	/**
	 * Resets current frame to 0
	 */
	public void reset() {
		curFrame = 0;
	}
	
	/**
	 * @return the curFrame
	 */
	public int getCurFrame() {
		return curFrame;
	}

	/**
	 * @param curFrame the curFrame to set
	 */
	public void setCurFrame(int curFrame) {
		this.curFrame = curFrame;
	}

	/**
	 * @return the frameDelay
	 */
	public int getFrameDelay() {
		return frameDelay;
	}

	/**
	 * @param frameDelay the frameDelay to set
	 */
	public void setFrameDelay(int frameDelay) {
		this.frameDelay = frameDelay;
	}

	/**
	 * @return the animID
	 */
	public eAnimation getAnimID() {
		return animID;
	}

	/**
	 * @return the seq
	 */
	public ArrayList<Image> getSeq() {
		return seq;
	}

	/**
	 * @return the numFrames
	 */
	public int getNumFrames() {
		return numFrames;
	}
}
