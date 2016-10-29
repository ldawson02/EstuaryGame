package IanCollisions;
import java.awt.geom.Ellipse2D;

public class DebrisView {

	private Ellipse2D.Double ellipse;
	private eShapeState state;
	
	private DebrisView() {
		
	}
	
	public DebrisView(Ellipse2D.Double ellipse) {
		this.ellipse = ellipse;
		state = eShapeState.FREE;
	}

	/**
	 * @return the ellipse
	 */
	public Ellipse2D.Double getEllipse() {
		return ellipse;
	}

	/**
	 * @param ellipse the ellipse to set
	 */
	public void setEllipse(Ellipse2D.Double ellipse) {
		this.ellipse = ellipse;
	}

	/**
	 * @return the state
	 */
	public eShapeState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(eShapeState state) {
		this.state = state;
	}
	
}
