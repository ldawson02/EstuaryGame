package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import controller.GameController;
import controller.MouseController;
import controller.TutorialController;
import eNums.eTutorialState;
import model.DrawableItem;
import model.Item;

public class Tutorial extends EstuaryGame implements ActionListener {

	JButton btn1 = new JButton("Go to main game ->");
	private eTutorialState state = eTutorialState.DEBRIS;
	private eTutorialState lastState = eTutorialState.DEBRIS;
	public boolean spotlight = false;
	public boolean spotlightSwitched = false; // true when spotlight is forced
												// off for round
	public boolean wrongThrow = false;
	private Item spotlightItem;
	public TutorialController tc;
	public DrawableItem arrow;

	/**
	 * Get spotlight switched
	 * 
	 * @return boolean
	 */
	public boolean getSpotlightSwitched() {
		return spotlightSwitched;
	}

	/**
	 * Set spotlight on or off
	 * 
	 * @param boolean
	 *            t
	 */
	public void setSpotlight(boolean t) {
		spotlight = t;
		spotlightSwitched = true;
	}

	/**
	 * Set spotlight item
	 * 
	 * @param Item
	 *            i
	 */
	public void setSpotlightItem(Item i) {
		spotlightItem = i;
	}

	/**
	 * Get spotlight item
	 * 
	 * @return Item i
	 */
	public Item getSpotlightItem() {
		return spotlightItem;
	}

	/**
	 * Get eTutorialState
	 * 
	 * @return eTutorialState
	 */
	public eTutorialState getLastState() {
		return lastState;
	}

	/**
	 * Constructs the Tutorial, adds the progression button
	 */
	public Tutorial() {
		super();
		btn1.setActionCommand("START GAME");
		btn1.addActionListener(this);
		btn1.setBounds(new Rectangle(310, 550, 200, 30));
		this.add(btn1);
	}

	/**
	 * Begins the tutorial by adding the controller and setting up the mouse
	 * controller.
	 */
	public void start() {
		tc = new TutorialController(this);
		this.mc = new MouseController();
		this.mc.setGC(tc);
		this.gc = tc;
	}

	/**
	 * Sets wrongThrow to true, so as to display the next instruction
	 */
	public void wrongBin() {
		wrongThrow = true;
	}

	/**
	 * Get state
	 * 
	 * @return eTutorialState
	 */
	public eTutorialState getState() {
		return state;
	}

	/**
	 * Set State
	 * 
	 * @param eTutorialState
	 *            s
	 */
	public void setState(eTutorialState s) {
		state = s;
	}

	/**
	 * Update the state
	 */
	public void nextState() {
		lastState = state;
		state = state.nextState();
	}

	/**
	 * Run if the debris is caught, turns off the spotlight
	 */
	public void debrisCaught() {
		spotlight = false;
	}

	/**
	 * Set the arrow so that it can be painted
	 * 
	 * @param DrawableItem
	 *            a
	 */
	public void setArrow(DrawableItem a) {
		arrow = a;
	}

	@Override
	/**
	 * Paint function for the Tutorial. Only paints one aspect at a time. Same
	 * functionality as the super class in EstuaryGame, but also has new paint
	 * classes for each stage.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintFocus(g);

		switch (state) {
		case DEBRIS:
			paintDebris(g);
			break;
		case EROSION_GABION:
			paintGabion(g);
			break;
		case EROSION_WALL:
			paintWall(g);
			break;
		case EROSION_CHOICE:
			paintErosionChoice(g);
			break;
		case POWERS_REMOVE:
			paintRemove(g);
			break;
		case POWERS_REBUILD:
			paintRebuild(g);
			break;
		case HEALTH:
			paintHealthTutorial(g);
			break;
		case TIMER:
			paintTimerTutorial(g);
			break;
		case DONE:
			paintDone(g);
			break;
		case IDLE:
			// paintIdle(g);
		}
	}

	/**
	 * Paints a message if the wrong bin is chosen.
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintDebris(Graphics g) {
		if (wrongThrow) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.BOLD, 18));
			g.drawString("Which is the right bin to throw the Debris?", getScreenX() / 3, getScreenY() / 2);
		}
	}

	/**
	 * Tells the player to place a Gabion.
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintGabion(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("Place a gabion.", getScreenX() / 3, getScreenY() / 2);
		paintMouseArrow(g, arrow);
	}

	/**
	 * Tells the player to place a Wall.
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintWall(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("Place a seawall.", getScreenX() / 3, getScreenY() / 2);
		paintMouseArrow(g, arrow);
	}

	/**
	 * Tells the player where to look.
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintErosionChoice(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("Notice how fast the barriers erode. Now you pick!", getScreenX() / 4, getScreenY() / 2);
	}

	/**
	 * Tells the player about the remove powerup.
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintRemove(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("Here's a powerup that helps clean up the coast!", getScreenX() / 4, getScreenY() / 2);
	}

	/**
	 * Tells the player about the rebuild powerup.
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintRebuild(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("Here's another powerup that helps keep your coast safe!", getScreenX() / 4, getScreenY() / 2);
	}

	/**
	 * Tells the player about the health bar
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintHealthTutorial(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("The health bar tells you how healthy your estuary is", getScreenX() / 4, getScreenY() / 2);
	}

	/**
	 * Tells the player about the timer
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintTimerTutorial(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		g.drawString("The timer shows you how much time you have left to save the estuary", getScreenX() / 5,
				getScreenY() / 2);

	}

	/**
	 * Tells the player the tutorial is complete
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintDone(Graphics g) {
		g.setColor(new Color(255, 255, 255, 120));
		g.fillRect(0, 0, screenX, screenY);

		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 35));
		g.drawString("Ready to play?", 300, 290);
	}

	/**
	 * Prompts the user if they are idle
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintIdle(Graphics g) {
		g.setColor(new Color(255, 255, 255, 120));
		g.fillRect(0, 0, screenX, screenY);

		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 35));
		g.drawString("Are you still there?", 275, 290);

		g.setFont(new Font("TimesRoman", Font.PLAIN, 26));
		g.drawString("Press start game button below to continue with tutorial", 245, 330);
	}

	/**
	 * Paints the spotlight onto key items
	 * 
	 * @param Graphics
	 *            g
	 */
	public void paintFocus(Graphics g) {
		if (spotlightItem == null || !spotlight) {
			return;
		}
		paintSpotlight(g, spotlightItem);
		if (spotlightItem.equals(tc.getItems().getHealthBar())) {
			paintHealthArrow(g, tc.getItems().getHealthBar());
		} else if (spotlightItem.equals(tc.getItems().getScreenTimer())) {
			paintClockArrow(g, tc.getItems().getScreenTimer());
		}
	}

	/**
	 * Paints the arrow to the clock
	 * 
	 * @param Graphics
	 *            g
	 * @param Item
	 *            i
	 */
	public void paintClockArrow(Graphics g, Item i) {

		g.drawImage(tutClockArrow, i.getPosX() + i.getWidth() + 25, i.getPosY() + i.getHeight() + 50, this);
	}

	/**
	 * Paints the arrow to the health bar
	 * 
	 * @param Graphics
	 *            g
	 * @param Item
	 *            i
	 */
	public void paintHealthArrow(Graphics g, Item i) {
		int paintX = i.getPosX() + i.getWidth();
		int paintY = i.getPosY() - 73;
		// g.fillOval(x, y, 3, 3);
		g.drawImage(tutHealthArrow, paintX, paintY, this);
	}

	/**
	 * Paints the spotlight given an item
	 * 
	 * @param Graphics
	 *            g
	 * @param Item
	 *            i
	 */
	public void paintSpotlight(Graphics g, Item i) {
		int paintX = i.getPosX() - 800 + (i.getWidth() / 2);
		int paintY = i.getPosY() - 600 + (i.getHeight() / 2);
		g.drawImage(spotlightImage, paintX, paintY, this);
	}

	/**
	 * Paints the spotlight given coordinates
	 * 
	 * @param Graphics
	 *            g
	 * @param x
	 * @param y
	 */
	public void paintSpotlight(Graphics g, int x, int y) {
		int paintX = x - 800;
		int paintY = y - 600;
		g.drawImage(spotlightImage, paintX, paintY, this);
	}

	/**
	 * Paints the mouse arrow, drags from one item to another
	 * 
	 * @param Graphics
	 *            g
	 * @param DrawableItem
	 *            i
	 */
	public void paintMouseArrow(Graphics g, DrawableItem i) {
		g.drawImage(tutMouseArrow, i.getPosX(), i.getPosY(), this);
		i.move();
	}

	@Override
	/**
	 * Handles the movement from the tutorial to the game.
	 */
	public void actionPerformed(ActionEvent e) {
		CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
		String cmd = e.getActionCommand();
		if (cmd.equals("START GAME")) {
			tc.gameOver();
			c1.show(EstuaryGame.getCards(), "MainGame");
			// EstuaryGame.mainGame = new EstuaryGame();
			EstuaryGame.gc = new GameController(EstuaryGame.mainGame);
			EstuaryGame.mc = new MouseController();
			EstuaryGame.mc.setGC(EstuaryGame.gc);

			EstuaryGame.mainGame.addMouseListener(EstuaryGame.mc);
			EstuaryGame.mainGame.addMouseMotionListener(EstuaryGame.mc);

		}

	}

}
