package test.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.Gabions;
import model.Storm;

public class StormTest {

	public Storm s;
	
	@Test
	public void appearTest() {
		fail("Not yet implemented");
	}

	
	/*
	 * should be able to get rid of gabions from the coasts by a certain amount-
	 * either make them decay faster or get rid of a couple entirely
	 */
	@Test
	public void destroyGabionsTest() {
		ArrayList<Gabions> gabs = new ArrayList<Gabions>();
		for (int i = 0; i <= 10; i++) {
			gabs.add(new Gabions(1,1));
		}
		s.destroyGabions();
		assertEquals(gabs.size(), 5);
		//should not get below 0
		ArrayList<Gabions> gab2 = new ArrayList<Gabions>();
		for (int i = 0; i < 3; i++) {
			gab2.add(new Gabions(1,1));
		}
		s.destroyGabions();
		assertEquals(gab2.size(), 0);
	}
	
	/*
	 * create an AllDebris class which will have an added # of debris, and the types 
	 * can be random
	 */
	
	@Test
	public void addTrashTest() {
		fail("Not yet implemented");
	}
}
