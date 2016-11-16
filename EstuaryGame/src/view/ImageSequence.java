package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
