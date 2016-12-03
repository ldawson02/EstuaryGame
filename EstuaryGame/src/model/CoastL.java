package model;

/**
 * the constructor of the coast l
 * @author megan
 *
 */
public class CoastL extends Coast {

	public CoastL() {
		super();
		setBarrierSpaces(Barriers.setUpLeftCoast());
	}
	
	public void updateCoords() {
		// TODO This will likely be in view tbh
	}

}
