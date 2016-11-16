package view.test;

import static org.junit.Assert.*;

import org.junit.Test;

import eNums.eAnimation;
import view.ImageSequence;

public class ImageSequenceTest {

	@Test
	public void test() {
		ImageSequence seq = new ImageSequence(eAnimation.playerIdle);
	}

}
