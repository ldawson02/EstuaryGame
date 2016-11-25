package view;

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
	private ArrayList<BufferedImage> seq;
	private int curFrame;
	private int numFrames;
	private int frameDelay = 10;
	
	private ImageSequence() {};
	
	public ImageSequence(eAnimation ID) {
		this.animID = ID;
		seq = new ArrayList<BufferedImage>();
		curFrame = 0;
		loadSequence(ID.getPath());
	}
	
	private void loadSequence(String filepath) {
		//TODO: load in the images using paths <3
		String srcpath = "resources/";
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
				
				seq.add(createImage(next.toString()));
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

	public BufferedImage draw() {
		BufferedImage toReturn = seq.get(curFrame / (1 + frameDelay));
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
	public ArrayList<BufferedImage> getSeq() {
		return seq;
	}

	/**
	 * @return the numFrames
	 */
	public int getNumFrames() {
		return numFrames;
	}
	
	/* The old implementation
	public int SeqID;
	private ArrayList<BufferedImage> seq;
	private int curFrame;
	private int numFrames;
	
	
	private ImageSequence() {
		//Hidden no-arg
	}
	
	public ImageSequence(int SeqID, String filepath, int frames, int imageWidth, int imageHeight) {
		seq = new ArrayList<BufferedImage>();
		//TODO: how are filepaths going to work?? tentatively:
		loadImages(filepath, frames, imageWidth, imageHeight);
		numFrames = frames;
		curFrame = 0;
	}
	
	public void reset() {
		curFrame = 0;
	}
	
	public BufferedImage draw() {
		BufferedImage toReturn = seq.get(curFrame);
		curFrame += 1;
		if (curFrame == numFrames) {
			curFrame = 0;
		}
		return toReturn;
	}
	
	private void loadImages(String filepath, int frames, int imageWidth, int imageHeight) {
		String srcpath = "resources/";
		BufferedImage sheet = createImage(srcpath + filepath);
		for (int i = 0; i < frames; i++) {
			seq.add(sheet.getSubimage(imageWidth*i, 0, imageWidth, imageHeight));
		}
	}
	
	private BufferedImage createImage(String filename){
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(filename));
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int compareTo(ImageSequence seq2) {
		if (SeqID < seq2.SeqID) {
			return -1;
		}
		else if (SeqID == seq2.SeqID) {
			return 0;
		}
		else {
			return 1;
		}
	}
	*/
}
