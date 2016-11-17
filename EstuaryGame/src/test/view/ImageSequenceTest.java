package test.view;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.Test;

import eNums.eAnimation;
import view.ImageSequence;

public class ImageSequenceTest {

	@Test
	public void testSeq() {
		ImageSequence seq = new ImageSequence(eAnimation.playerIdle);
		assertEquals(seq.getSeq().size(), 3);
		assertEquals(seq.getNumFrames(), 3);
	}
	
	@Test
	public void testSeqOneFrame() {
		ImageSequence seq = new ImageSequence(eAnimation.playerLift);
		assertEquals(seq.getSeq().size(), 1);
		assertEquals(seq.getNumFrames(), 1);
	}
	
	@Test
	public void testDraw() {
		ImageSequence seq = new ImageSequence(eAnimation.playerIdle);
		ArrayList<BufferedImage> frames = seq.getSeq();
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(1));
		assertEquals(seq.draw(), frames.get(2));
		assertEquals(seq.draw(), frames.get(0));
	}
	
	@Test
	public void testDrawWithDelay() {
		ImageSequence seq = new ImageSequence(eAnimation.playerIdle);
		seq.setFrameDelay(1);
		ArrayList<BufferedImage> frames = seq.getSeq();
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(1));
		assertEquals(seq.draw(), frames.get(1));
		assertEquals(seq.draw(), frames.get(2));
		assertEquals(seq.draw(), frames.get(2));
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(1));
		
		seq.reset();
		seq.setFrameDelay(2);
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(1));
		assertEquals(seq.draw(), frames.get(1));
		assertEquals(seq.draw(), frames.get(1));
		assertEquals(seq.draw(), frames.get(2));
		assertEquals(seq.draw(), frames.get(2));
		assertEquals(seq.draw(), frames.get(2));
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(0));
		assertEquals(seq.draw(), frames.get(1));
	}
	
}
