
package model;

public class HorseshoeCrab extends Friend{

	/**
	 * author:megan chen
	 * The horseshoe should have a xloc, yloc, current location and destination. 
	 * @since 10/25/16 
     * @version 1.0
	 * @param int x
	 * @param int y
	 * @param Location destination
	 * @param Location current
	 * return nothing cause we just created the parameter so far         
	 */
	public int xloc;
	public int yloc;
	//public Location current;
	//public Location destination;
	/**
	 * construct a horseshoe crab
	 * @param x
	 * @param y
	 */
	public HorseshoeCrab(int x, int y /*Location destination, Location current*/) {
		this.xloc = x;
		this.yloc = y;
		//this.destination = destination;
		//this.current = current;
	}
	/**
	 * xloc getter and setter
	 * @return
	 */
	public int getXloc() {
		return xloc;
	}

	public void setXloc(int xloc) {
		this.xloc = xloc;
	}

	/**
	 * yloc getter and setter
	 * @return
	 */
	public int getYloc() {
		return yloc;
	}

	public void setYloc(int yloc) {
		this.yloc = yloc;
	}

	@Override
	public void appear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disappear() {
		// TODO Auto-generated method stub
		
	}


}