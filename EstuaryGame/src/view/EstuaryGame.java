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
import eNums.eBarrierType;
import eNums.eFloaterState;
import eNums.eDebrisType;
import model.Barriers;
import model.Bin;
import model.Debris;
import model.Gabions;
import model.HealthBar;
import model.Player;

public class EstuaryGame extends JComponent {

    private static final long serialVersionUID = 1L;

    private GameController gc;
    private JFrame mainFrame;
    BufferedImage bg;
    
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
                
        		MouseController mouse = new MouseController();
        		frame.addMouseListener(mouse);
                
                Container contentPane = frame.getContentPane();
                contentPane.setBackground(Color.LIGHT_GRAY);
                contentPane.add(new EstuaryGame(frame));
                frame.setVisible(true);
                
        		
                
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
		
		//this actually should probably be in the VIEW
	
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
    	try {
    		bg = ImageIO.read(new File("resources/background/babybackground.png"));
    	}
    	catch (IOException e) {
    		System.out.println("Background failed to load.");
    	}
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        //Handle collisions, probably
        
        //Paint background
        paintBackground(g);
       /*
        //These paint the barrier spaces as they are currently
        g.drawRect(20, barrierY, bWidth, bHeight);
        g.drawRect(70, barrierY, bWidth, bHeight);
        g.drawRect(120, barrierY, bWidth, bHeight);
        g.drawRect(170, barrierY, bWidth, bHeight);
        g.drawRect(220, barrierY, bWidth, bHeight);
        
        g.drawRect(740, barrierY, bWidth, bHeight);
        g.drawRect(690, barrierY, bWidth, bHeight);
        g.drawRect(640, barrierY, bWidth, bHeight);
        g.drawRect(590, barrierY, bWidth, bHeight);
        g.drawRect(540, barrierY, bWidth, bHeight);*/
        
        /*
    	g.setColor(Color.GREEN);
		g.fillRect(Barriers.getRightEdge(), 390, bWidth, bHeight); //the gabion spawn
    	g.setColor(Color.DARK_GRAY);
		g.fillRect(Barriers.getLeftEdge(), 390, bWidth, bHeight); //the wall spawn
        
        */
    	g.setColor(Color.GREEN);
		g.fillRect(GameController.gabionsSpawn.x, GameController.gabionsSpawn.y, 
				GameController.gabionsSpawn.width, GameController.gabionsSpawn.height); //the gabion spawn
    	g.setColor(Color.DARK_GRAY);
		g.fillRect(GameController.wallSpawn.x, GameController.wallSpawn.y, 
				GameController.wallSpawn.width, GameController.wallSpawn.height); //the wall spawn
		
        
        
        //Paint ScreenTimer
        paintScreenTimer(g);
        
        //Paint healthBar
        paintHealthBar(g);
       
        //Paint barriers
        paintBarriers(g);
        
        //Paint debris
        paintDebris(g);
       
        paintBins(g);
        
        //Paint player
        paintPlayer(g);
        
        //Paint health bar
        paintHealthBar(g);
        //in here twice?
        
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
    	double maxTime = 20000; //ms
    	double timeElapsed = gc.getTheBigTimer();
        double fractionPassed = timeElapsed / maxTime;
        double size = 50;
        double maxX = 375;
        double maxY = 10;
        double timerX = 401 - (fractionPassed * size)/2;
        if (timerX < maxX)
        	timerX = maxX;
        double timerY = 36 - (fractionPassed * size)/2;
        if (timerY < maxY)
        	timerY = maxY;
        double timerSize = fractionPassed * size;
        if (timerSize > size)
        	timerSize = size;
        g.setColor(Color.RED);
        g.fillOval((int) timerX,(int) timerY,(int) timerSize,(int) timerSize);
        //outline
        g.setColor(Color.BLACK);
        g.drawOval(375, 10, 50, 50);
        g.fillOval(398, 33, 5, 5);
    }
    
    private void paintBarriers(Graphics g) {
    	ArrayList<Barriers> barriers = gc.getItems().getAllBarriers();
    	for (Barriers b : barriers) {
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
    		else if (b.getType() == eBarrierType.Empty) {
    			g.setColor(Color.BLACK);
    	        g.drawRect(b.getPosX(), b.getPosY(), bWidth, bHeight);
    		}
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
    
    private void paintBins(Graphics g){
    	Bin trash = gc.getItems().getTrashBin();
    	Bin recycle = gc.getItems().getRecycleBin();
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
    	double currHealthHeight = (currHealth / maxHealth)*barHeight;
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
    	g.setColor(Color.RED);
    	g.fillRect((int) p.getPosX(), (int) p.getPosY(), (int) p.getWidth(), (int) p.getHeight());
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