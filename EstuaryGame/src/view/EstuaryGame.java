package view; 

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ActiveItems;
import controller.GameController;
import controller.MouseController;
import controller.ScoreController;
import eNums.eAnimation;
import eNums.eBarrierType;
import eNums.eFloaterState;
import eNums.ePlayerState;
import eNums.eDebrisType;

import model.*;

import model.Barriers;
import model.Bin;
import model.Debris;
import model.Floater;
import model.HealthBar;
import model.Player;


public class EstuaryGame extends JComponent{

    private static final long serialVersionUID = 1L;

    static GameController gc;
   	public static MouseController mc;
    private JPanel mainFrame;
    private ImageLibrary lib;
    
    //Constant images
    Image bg;
    Image clockback;
    Image gameOver;
    Image trashBin;
    Image recycBin;
    Image tutClockArrow;
    Image tutHealthArrow;
    Image spotlightImage;
    
    int screenX = 800;
    int screenY = 600;
    
    int timeElapsed = 0;
	
    private int bWidth = 40;
    private int bHeight = 20;
    
    Rectangle player;
    activeViewItems actives;
    
    boolean gameFinished = false;
    
    //For future collision handling:
    ArrayList<DebrisWrapper> debrisColliders;
    
    static JPanel cards = new JPanel(new CardLayout());
    static TitleScreen titleScreen;
    static EndScreen endScreen;
    static EstuaryGame mainGame;
    static Tutorial tutorial;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
               
                final String TitleScreen = "TitleScreen";
                final String MainGame = "MainGame";
                final String EndScreen = "EndScreen";
                final String Tutorial = "Tutorial";
                
             
                mainGame = new EstuaryGame();
                tutorial = new Tutorial();
                titleScreen = new TitleScreen(mainGame, tutorial);
                endScreen = new EndScreen();
                
                mainGame.addComponentListener ( new ComponentAdapter ()
                {
                    public void componentShown ( ComponentEvent e )
                    {
                    	
                        System.out.println ( "Component shown" );

                    }

                    public void componentHidden ( ComponentEvent e )
                    {
                    	
                    }
                } );
                
                cards.add(titleScreen, TitleScreen);
                cards.add(mainGame, MainGame);
                cards.add(tutorial, Tutorial);
                cards.add(endScreen, EndScreen);
                
                JFrame frame = new JFrame("Mainframe");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.add(cards, BorderLayout.CENTER);
                frame.pack();
                frame.setFocusable(true);
                frame.setVisible(true);
                
                
            }
        });
    }
    
    public EstuaryGame() {
    	//Initialize a new GameController and connect them
    	this.setDoubleBuffered(true);
    	//gc = new GameController(this);
    	//View items matter
    	actives = new activeViewItems();
    	actives.setPlayer(gc.getItems().getPlayer());
    	initImages();
    	
    }

    public EstuaryGame(JPanel f) {
    	mainFrame = f;
    	//Initialize a new GameController and connect them
    	this.setDoubleBuffered(true);
    	gc = new GameController(this);
    	//View items matter
    	actives = new activeViewItems();
    	actives.setPlayer(gc.getItems().getPlayer());
    	initImages();
    	
    }
 
    public void bindKeyWith(String name, KeyStroke keyStroke, Action action) {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(keyStroke, name);
        am.put(name, action);
    }
    
    public void unbindKeyWith(String name, KeyStroke keyStroke) {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.remove(keyStroke);
        am.remove(name);
    }

    private void initImages() {
    	lib = ImageLibrary.loadLibrary();
    	//Grab the constant ones
    	bg = lib.draw(eAnimation.background);
    	clockback = lib.draw(eAnimation.clockback);
    	gameOver = lib.draw(eAnimation.gameOver);
    	tutClockArrow = lib.draw(eAnimation.clockArrow);
    	tutHealthArrow = lib.draw(eAnimation.healthArrow);
    	spotlightImage = lib.draw(eAnimation.spotlight);
    	recycBin = lib.draw(eAnimation.recycleBin);
    	trashBin = lib.draw(eAnimation.trashBin);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        //Paint background
        paintBackground(g);
        
        paintCoast(g);
        
        //Paint barriers
        paintBarriers(g);
        
        //Paint debris
        paintDebris(g);
       
        //Paint Powers
        paintPowers(g);
        
        //Paint Bins
        paintBins(g);
        
        //Paint player
        paintPlayer(g);
        
        //Paint health bar
        paintHealthBar(g);
        
        //Paint ScreenTimer
        if (!gameFinished)
        	paintScreenTimer(g);
        
        paintScore(g);
        
        timeElapsed = gc.getTheBigTimer();
        g.drawString(Integer.toString(timeElapsed), 40, 40);
        
    }
    
    private void paintBackground(Graphics g) {
    	g.drawImage(bg, 0, 0, this);
    }
    
    private void paintScreenTimer(Graphics g) {
    	//Getting all the variables
    	ScreenTimer sc = gc.getItems().getScreenTimer();
    	double maxTime = sc.getMaxTime(); //ms
    	double timeElapsed = sc.getElapsedTime();
        double fractionPassed = timeElapsed / maxTime;
        double size = sc.getSize();
        double maxX = sc.getDoublePosX();
        double maxY = sc.getDoublePosY();
        
        //Painting the timer as a small circle growing into the frame
        double timerX = (maxX + (size*0.5) + 1) - (fractionPassed * size)/2;
        if (timerX < maxX)
        	timerX = maxX;
        double timerY = (maxY + (size*0.5) + 1) - (fractionPassed * size)/2;
        if (timerY < maxY)
        	timerY = maxY;
        double timerSize = fractionPassed * size;
        if (timerSize > size)
        	timerSize = size;
        g.drawImage(clockback, (int)maxX,(int) maxY, this);
        g.setColor(new Color(255, 90, 90, 150));
        g.fillOval((int) timerX,(int) timerY,(int) timerSize,(int) timerSize);
        //outline
        g.setColor(Color.BLACK);
        g.drawOval((int)maxX,(int)maxY,(int) size,(int) size);
        
        if (timeElapsed >= maxTime) {
        	paintGameOver(g);
        	endGameMotion();
        }
    }
    
    private void paintCoast(Graphics g) {
		Coast prevCoast = null;
    	for(Coast c : gc.getItems().getCoast()){
    		Image coast;
    		
    		switch (c.getCoastID()) {
    		case 1:
    			prevCoast = c;
    			coast = lib.drawCoast(c.getState().getHits(), 3);
    			break;
    		case 2:
    		case 3:
    		case 4:
    		case 7:
    		case 8:
    		case 9:
    			int prevCoastState = prevCoast.getState().getHits();
    			coast = lib.drawCoast(c.getState().getHits(), prevCoastState);
    			prevCoast = c;
    			break;
    		case 5:
    			prevCoastState = prevCoast.getState().getHits();
    			coast = lib.drawCoast(3, prevCoastState);
    			prevCoast = c;
    			break;
    		case 6: 
    			prevCoast = c;
    			coast = lib.drawCoast(c.getState().getHits(), 3);
    			break;
    		case 10:
    			prevCoastState = prevCoast.getState().getHits();
    			coast = lib.drawCoast(3, prevCoastState);
    			prevCoast = c;
    			break;
    		default:
    			coast = lib.drawCoast(0, 1);
    		}
    		
    		/*
    		switch (c.getState()){
    		case NO_HIT:
    			coast = lib.drawCoast(0, 0);
    			break;
    		case ONE_HIT:
    			coast = lib.drawCoast(1, 0);
    			break;
    		case TWO_HIT:
    			coast = lib.drawCoast(2, 0);
    			break;
    		case ERODED:
    			coast = lib.drawCoast(3, 0);
    			break;
    		default: 
    			coast = lib.drawCoast(3, 0);
    		}
    		*/
    		
    		g.drawImage(coast, c.getPosX(), c.getPosY(), this);
    		
    		//g.fillRect(c.getPosX(), c.getPosY(), c.getWidth(), c.getHeight());
    	}
    }
    
    private void paintBarriers(Graphics g) {
    	ArrayList<Barriers> barriers = gc.getItems().getAllBarriers();
    	for (Barriers b : barriers) {
    		paintOneBarrier(g, b);
    	}
    	paintOneBarrier(g, mc.getWallSpawn());
    	paintOneBarrier(g, mc.getGabionsSpawn());
    	
    	Barriers tempDragged = mc.getDragged();
    	if (tempDragged != null) {
    		paintOneBarrier(g, mc.getDragged());
    	}
    }
    
    private void paintOneBarrier(Graphics g, Barriers b) {
    	if (b.getType() == eBarrierType.Gabion) {
    		//For now, calling a gabion a green rectangle
    		g.setColor(Color.GREEN);
    		g.fillRect(b.getPosX(), b.getPosY(), bWidth, bHeight);
    	}
    	else if (b.getType() == eBarrierType.Wall) {
    		//Calling a wall a dark gray wall
    		g.setColor(Color.DARK_GRAY);
    		g.fillRect(b.getPosX(), b.getPosY(), bWidth, bHeight);
    	}
    	else if (b.getType() == eBarrierType.EMPTY) {
    		g.setColor(Color.BLACK);
    		g.drawRect(b.getPosX(), b.getPosY(), bWidth, bHeight);
    	}
    }
    
    private void paintDebris(Graphics g) {
    	ArrayList<Debris> debris = gc.getItems().getAllDebris();
    	for (Debris d : debris) {

    		if (d.getState() == eFloaterState.LIFTED) {
    			paintArrow(g, d);
    		}
    		
    		Image debrisImg = lib.draw(d);
    		g.drawImage(debrisImg, d.getPosX(), d.getPosY(), this);
    	}
    }
    
    private void paintPowers(Graphics g) {
    	ArrayList<Powers> powers = gc.getItems().getAllPowers();
    	for (Powers p : powers) {

    		Image power = lib.draw(p);
    		
    		g.drawImage(power, p.getPosX(), p.getPosY(), this);
    	}
    }
    
    private void paintArrow(Graphics g, Debris d){
    	g.setColor(Color.BLACK);
    	int x1 = d.getPosX()+d.getWidth()/2;
    	int y1 = d.getPosY()+d.getHeight()/2;
    	int finalx = d.getBin().getPosX()+d.getBin().getWidth()/2;
    	int finaly = d.getBin().getPosY()+d.getBin().getHeight()/2;
    	double angle = Math.atan2(finaly-y1, x1-finalx);
    	int r = 90;
    	int deltax = (int) (r*Math.cos(angle));
    	int deltay = (int) (r*Math.sin(angle));
    	
    	g.drawLine(x1, y1, x1-deltax, y1+deltay);
    }
    
    private void paintBins(Graphics g){
    	//TODO: These need to be switched!! BUT I can't touch the controller now
    	Bin recycle = gc.getItems().getTrashBin();
    	Bin trash = gc.getItems().getRecycleBin();
    	
    	//Painting real images
    	g.drawImage(trashBin, trash.getPosX(), trash.getPosY(), this);
    	g.drawImage(recycBin, recycle.getPosX(), recycle.getPosY(), this);
    	
    }
    
    private void paintHealthBar(Graphics g) {
    	HealthBar hb = gc.getItems().getHealthBar();
    	if (hb == null) {
    		System.out.println("null healthbar");
    	}
    	
    	double currHealth = hb.getHealth();
    	double maxHealth = hb.getMaxHealth();
    	double barY = hb.getPosY();
    	double barX = hb.getPosX();
    	double barWidth = hb.getWidth();
    	double barHeight = hb.getHeight();
    	
    	//Backing
        g.setColor(new Color(255, 255, 255, 150));
    	g.fillRect((int) barX,(int) barY,(int) barWidth,(int) barHeight);
    	
    	//Health
    	double currHealthWidth = (currHealth / maxHealth)*barWidth;
    	//double currHealthY = (barHeight - currHealthHeight) + barY;
        g.setColor(new Color(255, 90, 90, 200));
    	g.fillRect((int) barX,(int) barY,(int) currHealthWidth,(int) barHeight);
    	//Outline
    	g.setColor(Color.BLACK);
    	g.drawRect((int)barX, (int)barY,(int) barWidth,(int) barHeight);
    	
        if (currHealth <= 0) {
        	paintGameOver(g);
        	endGameMotion();
        	gameFinished = true;
        }
    }
    
    private void paintPlayer(Graphics g) {
    	Player p = gc.getItems().getMainPlayer();
    	//System.out.println(gc.getItems().getMainPlayer());
    	if (p == null) {
    		return;
    	}
    	
    	g.drawImage(lib.draw(p), p.getPosX(), p.getPosY(), this);
    }
    
    private void paintScore(Graphics g) {
    	g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    	g.drawString("Score: " + Integer.toString(ScoreController.getScore()), 150, 40);
    }
    
    private void paintGameOver(Graphics g) {
    	g.setColor(new Color(255, 255, 255, 120));
    	g.fillRect(0, 0, screenX, screenY);
    	
    	g.drawImage(gameOver, 220, 200, this);
    	
    	g.setColor(Color.BLACK);
    	g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
    	g.drawString("Press Enter to See Score...", 275, 370);
    }
   
    
    public void paintFocus(Graphics g){}
    
    private void endGameMotion() {
    	//Stop player motion
    	gc.getItems().getPlayer().setState(ePlayerState.Lifting);
    	//Stop floater motion
    	for (Debris d: gc.getItems().getAllDebris()) {
    		d.setState(eFloaterState.RESTING);
    	}
    	for (Powers p: gc.getItems().getAllPowers()) {
    		p.setState(eFloaterState.RESTING);
    	}
    	//Disable controls
    	unbindKeyWith("x.up", KeyStroke.getKeyStroke("UP"));
		unbindKeyWith("x.down", KeyStroke.getKeyStroke("DOWN"));
		unbindKeyWith("x.left", KeyStroke.getKeyStroke("LEFT"));
		unbindKeyWith("x.right", KeyStroke.getKeyStroke("RIGHT"));
    }
    
    public int getScreenX() {
		return screenX;
	}

	public int getScreenY() {
		return screenY;
	}

    public static JPanel getCards(){
    	return cards;
    }
    
    public static void setMainGame(){
    	mainGame = new EstuaryGame();
    }
    public static EstuaryGame getMainGame(){
    	return mainGame;
    }
}
