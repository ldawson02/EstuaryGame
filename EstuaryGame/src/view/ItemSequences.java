package view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 * For each type of item (ex: Player, Wave), contains all of the animations as
 * ImageSequence instances. The sequences are identified by SeqIDs, which will be
 * painstakingly mapped out as enums. The list is sorted by the SeqIDs of the
 * ImageSequence instances (which implement Comparable for this sorting).
 * 
 * Required: A completely loaded ItemSequences object must have ImageSequences of
 * continuous SeqIDs from 0 to (numSeqs - 1).
 * 
 * @author Ian
 *
 */
public class ItemSequences {

	private ArrayList<ImageSequence> anims;
	private int numSeqs = 0;
	private int curSeq = 0;
	
	public ItemSequences() {
		anims = new ArrayList<ImageSequence>();
	}
	
	/**
	 * Method to add image sequences to the list, because it's difficult to add
	 * them all at once, due to differences in frame count. Easiest this way.
	 */
	public void addSequence(int SeqID, String filepath, int frames, int imageWidth, int imageHeight) {
		ImageSequence newSeq = new ImageSequence(SeqID, filepath, frames, imageWidth, imageHeight);
		anims.add(newSeq);
		//Makes sure the animations keep their place in accordance with their IDs.
		Collections.sort(anims);
		numSeqs = anims.size();
	}
	
	public BufferedImage draw(int SeqID) {
		if (SeqID != curSeq) {
			anims.get(curSeq).reset();
			curSeq = SeqID;
		}
		return (anims.get(curSeq).draw());
	}
	
}
