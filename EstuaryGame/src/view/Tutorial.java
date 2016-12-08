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
import model.Item;

public class Tutorial extends EstuaryGame implements ActionListener{

	JButton btn1 = new JButton("Go to main game ->");
	private eTutorialState state = eTutorialState.DEBRIS;
	private eTutorialState lastState = eTutorialState.DEBRIS;
	public boolean spotlight = false;
	public boolean spotlightSwitched = false; //true when spotlight is forced off for round
	public boolean exclamation = false;
	public boolean wrongThrow = false;
	private Item spotlightItem;
	public TutorialController tc; 

	public boolean getSpotlightSwitched(){
		return spotlightSwitched;
	}

	public void setSpotlight(boolean t){
		spotlight = t;
		spotlightSwitched = true;
	}
	
	public void setSpotlightItem(Item i){
		spotlightItem = i;
	}
	
	public Item getSpotlightItem(){
		return spotlightItem;
	}

	public void setExclamation(boolean t){
		exclamation = t;
	}
	
	public eTutorialState getLastState(){
		return lastState;
	}
	public Tutorial(){
		super();
		btn1.setActionCommand("START GAME");
		btn1.addActionListener(this);
		btn1.setBounds(new Rectangle(310,550,200,30));
		this.add(btn1);
	}
	
	public void start(){
		tc = new TutorialController(this);
		this.mc = new MouseController();
		this.mc.setGC(tc);
		this.gc = tc;
	}

	public void wrongBin(){
		//TODO: create animation, words on screen, something like that if they try to throw to wrong bin
		wrongThrow = true;
	}
	
	public eTutorialState getState(){
		return state;
	}
	public void setState(eTutorialState s){
		state = s;
	}
	public void nextState(){
		lastState = state;
		state = state.nextState();
	}

	public void debrisCaught(){
		spotlight = false;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		paintFocus(g);
		
		switch(state){
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
			//paintRebuild(g);
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
			//paintIdle(g);
		}
	}
	
	public void paintDebris(Graphics g){
		if(wrongThrow){
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.BOLD, 18));
	    	g.drawString("Which is the right bin to throw the Debris?",getScreenX()/3, getScreenY()/2);
		}
	}
	
	public void paintGabion(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
	    g.drawString("Place a gabion.",getScreenX()/3, getScreenY()/2);
	}
	public void paintWall(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
	    g.drawString("Place a seawall.", getScreenX()/3, getScreenY()/2);
	}
	public void paintErosionChoice(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
    	g.drawString("Notice how fast the barriers erode. Now you pick.", getScreenX()/4, getScreenY()/2);
	}
	public void paintRemove(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
    	g.drawString("Here's a powerup!", getScreenX()/4, getScreenY()/2);
	}
	public void paintRebuild(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
    	g.drawString("Here's another powerup!", getScreenX()/4, getScreenY()/2);
	}
	
	public void paintHealthTutorial(Graphics g){
    	g.setColor(Color.WHITE);
    	g.setFont(new Font("TimesRoman", Font.BOLD, 18));
    	g.drawString("The health bar tells you how healthy your estuary is", 150, 450);
	}
	
	public void paintTimerTutorial(Graphics g){
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
    	g.drawString("The timer shows you how much time you have left to save the estuary", 150, 450);
	
	}
	
	public void paintDone(Graphics g){
    	g.setColor(new Color(255, 255, 255, 120));
    	g.fillRect(0, 0, screenX, screenY);
    	
    	g.setColor(Color.BLACK);
    	g.setFont(new Font("TimesRoman", Font.PLAIN, 35));
    	g.drawString("Ready to play?", 300, 290);
	}
	
	public void paintIdle(Graphics g){
    	g.setColor(new Color(255, 255, 255, 120));
    	g.fillRect(0, 0, screenX, screenY);
    	
    	g.setColor(Color.BLACK);
    	g.setFont(new Font("TimesRoman", Font.PLAIN, 35));
    	g.drawString("Are you still there?", 275, 290);
    	
    	g.setFont(new Font("TimesRoman", Font.PLAIN, 26));
    	g.drawString("Press enter to continue with tutorial", 245, 330);
	}
	
    public void paintFocus(Graphics g) {
    	if(spotlightItem == null || !spotlight){
    		return;
    	}
    	paintSpotlight(g, spotlightItem);
    	if(spotlightItem.equals(tc.getItems().getHealthBar())){
    		paintHealthArrow(g, tc.getItems().getHealthBar());
    	}
    	else if(spotlightItem.equals(tc.getItems().getScreenTimer())){
    		paintClockArrow(g, tc.getItems().getScreenTimer());
    	}
    }
    
    public void paintClockArrow(Graphics g, Item i) {
    	
    	g.drawImage(tutClockArrow, i.getPosX()+i.getWidth()+25, i.getPosY()+i.getHeight() + 50, this);
    }
    
    public void paintHealthArrow(Graphics g, Item i) {
    	int paintX = i.getPosX() + i.getWidth();
    	int paintY = i.getPosY() - 73;
    	//g.fillOval(x, y, 3, 3);
    	g.drawImage(tutHealthArrow, paintX, paintY, this);
    }
    
    public void paintSpotlight(Graphics g, Item i) {
    	int paintX = i.getPosX() - 800 + (i.getWidth()/2);
    	int paintY = i.getPosY() - 600 + (i.getHeight()/2);
    	g.drawImage(spotlightImage, paintX, paintY, this);
    }
    
    public void paintSpotlight(Graphics g, int x, int y) {
    	int paintX = x - 800;
    	int paintY = y - 600;
    	g.drawImage(spotlightImage, paintX, paintY, this);
    }
    
    
	@Override
	public void actionPerformed(ActionEvent e) {
		CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
		String cmd = e.getActionCommand();
		if(cmd.equals("START GAME")){
			tc.gameOver();
			c1.show(EstuaryGame.getCards(), "MainGame");
			//EstuaryGame.mainGame = new EstuaryGame();
			EstuaryGame.gc = new GameController(EstuaryGame.mainGame);
			EstuaryGame.mc = new MouseController();
			EstuaryGame.mc.setGC(EstuaryGame.gc);

			EstuaryGame.mainGame.addMouseListener(EstuaryGame.mc);
			EstuaryGame.mainGame.addMouseMotionListener(EstuaryGame.mc);
			 
		}

	}

}
