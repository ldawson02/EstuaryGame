package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ActiveItems;
import controller.GameController;
import controller.MouseController;
import eNums.eAnimation;
import eNums.eBarrierType;
import eNums.eFloaterState;
import eNums.eDebrisType;

import model.*;

import model.Barriers;
import model.Bin;
import model.Debris;
import model.Gabions;
import model.Floater;
import model.HealthBar;
import model.Player;

public class EstuaryGame extends JComponent {

    private static final long serialVersionUID = 1L;

    private GameController gc;
    private static MouseController mc;
    private JFrame mainFrame;
    private ImageLibrary lib;
    
    //Constant images
    BufferedImage bg;
    BufferedImage clockback;
    BufferedImage gameOver;
    BufferedImage trashBin;
    BufferedImage recycBin;
    
    int screenX = 800;
    int screenY = 600;
    
    int timeElapsed = 0;
	
    private int bWidth = 40;
    private int bHeight = 20;
    
    Rectangle player;
    activeViewItems actives;
    
    //For future collision handling:
    ArrayList<DebrisWrapper> debrisColliders;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Mainframe");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setFocusable(true);
		    
               
		
		    
                Container contentPane = frame.getContentPane();
                contentPane.setBackground(Color.LIGHT_GRAY);
                EstuaryGame mainGame = new EstuaryGame(frame);
                contentPane.add(mainGame);
                frame.setVisible(true);
                
                mc = new MouseController();
        		mainGame.addMouseListener(mc);
        		mainGame.addMouseMotionListener(mc);
            }
        });
    }

    public EstuaryGame(JFrame f) {
    	mainFrame = f;
    	//Initialize a new GameController and connect them
    	this.setDoubleBuffered(true);
    	gc = new GameController(this);
    	//View items matter
    	actives = new activeViewItems();
    	actives.setPlayer(gc.getItems().getPlayer());
    	initImages();
    }

	public void initTitleScreen(){
		//TODO: if we're lucky
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
    	trashBin = lib.draw(eAnimation.trashBin);
    	recycBin = lib.draw(eAnimation.recycleBin);
    	/*
    	try {
    		bg = ImageIO.read(new File("resources/background/babybackground.png"));
    		clockback = ImageIO.read(new File("resources/clockback/clockback.png"));
    	}
    	catch (IOException e) {
    		System.out.println("Background failed to load.");
    	}
    	*/
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        //Paint background
        paintBackground(g);
        
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
        paintScreenTimer(g);
        
        timeElapsed = gc.getTheBigTimer();
        g.drawString(Integer.toString(timeElapsed), 40, 40);
        
    }
    
    private void paintBackground(Graphics g) {
    	//TODO: get a background
    	g.drawImage(bg, 0, 0, this);
    	
        g.setColor(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g.create();
        QuadCurve2D quadLeft = new QuadCurve2D.Double(0, 0, 300, 150, 0, 300);
        QuadCurve2D quadRight = new QuadCurve2D.Double(800, 0, 500, 150, 800, 300);
        g2d.draw(quadLeft);
        g2d.draw(quadRight);
        
        Ellipse2D bounds = new Ellipse2D.Double(50, 150, 700, 300);
        g2d.draw(bounds);
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
        	g.drawImage(gameOver, 220, 200, this);
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
    		
    		if (d.getType() == eDebrisType.TRASH) {
    			//TODO: paint like trash
    			//Calling trash a yellow circle
    			
    			g.setColor(Color.YELLOW);
    			g.fillOval(d.getPosX(), d.getPosY(), d.getWidth(), d.getHeight());
    		}
    		else if (d.getType() == eDebrisType.RECYCLING) {
    			//TODO: paint like recycling
    			//Calling recycling a blue circle
    			g.setColor(Color.BLUE);
    			g.fillOval(d.getPosX(), d.getPosY(), d.getWidth(), d.getHeight());
    		}	
    	}
    }
    private void paintPowers(Graphics g) {
    	ArrayList<Powers> powers = gc.getItems().getAllPowers();
    	for (Powers d : powers) {

    		if (d.getState() == eFloaterState.LIFTED) {
    			//paintArrow(g, d);
    		}
    		
    		if (d instanceof Rebuild) {
    			//TODO: paint like trash
    			//Calling trash a yellow circle
    			
    			g.setColor(Color.CYAN);
    			g.fillOval(d.getPosX(), d.getPosY(), d.getWidth(), d.getHeight());
    		}
    		else if (d instanceof Remove) {
    			//TODO: paint like recycling
    			//Calling recycling a blue circle
    			g.setColor(Color.MAGENTA);
    			g.fillOval(d.getPosX(), d.getPosY(), d.getWidth(), d.getHeight());
    		}	
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
    	
    	
    	g.setColor(Color.BLUE);
    	g.fillOval(trash.getPosX(), trash.getPosY(), trash.getWidth(), trash.getHeight());
    	g.setColor(Color.YELLOW);
    	g.fillOval(recycle.getPosX(), recycle.getPosY(), recycle.getWidth(), recycle.getHeight());
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
    }
    

    
    private void paintPlayer(Graphics g) {
    	Player p = gc.getItems().getMainPlayer();
    	//System.out.println(gc.getItems().getMainPlayer());
    	if (p == null) {
    		return;
    	}
    	/*
    	g.setColor(Color.RED);
    	g.fillRect((int) p.getPosX(), (int) p.getPosY(), (int) p.getWidth(), (int) p.getHeight());
    	*/
    	
    	g.drawImage(lib.draw(p), p.getPosX(), p.getPosY(), this);
    
    }
    

    public void throwChoice(Debris d){
    	Object[] options = {"Trash", "Recycling"};
    	int n = JOptionPane.showOptionDialog(mainFrame, "Where does this belong?", "Estuary Game",
    		    JOptionPane.YES_NO_CANCEL_OPTION,
    		    JOptionPane.QUESTION_MESSAGE,
    		    null,
    		    options,
    		    options[0]);
    	
    	d.throwDebris(n == d.getType().getType());
    	d.setController(gc);
    }
    private void updateWrappers() {
    	//TODO: Making this really inefficient to start
    	//Can be better about it with an integrated controller
    	actives.clearDebris();
    	ActiveItems activeItems = gc.getItems();
    	for (Debris d: activeItems.getAllDebris()) {
    		actives.addDebris(d);
    	}
    }
    
    private void lookForCollisions() {
    	updateDebrisCollisions();
    	//TODO: other collisions
    }
    
    private void updateDebrisCollisions() {
    	Rectangle playerRect = actives.getPlayer().getHitBox();
    	for (DebrisWrapper dw : actives.getDebrisWrappers()) {
    		if (dw.getHitBox().intersects(playerRect)) {
    			//TODO: the player is touching the debris
    			System.out.println("touched the debris!");
    			dw.getDebrisItem().setState(eFloaterState.LIFTED);
    		}
    	}
    }
}
