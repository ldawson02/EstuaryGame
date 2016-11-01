package view;

import java.awt.Color;
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
import eNums.eBarrierType;
import eNums.eFloaterState;
import eNums.eDebrisType;
import model.Barriers;
import model.Bin;
import model.Debris;
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
                frame.getContentPane().setBackground(Color.LIGHT_GRAY);
                frame.getContentPane().add(new EstuaryGame(frame));
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

    public void bindKeyWith(String name, KeyStroke keyStroke, Action action) {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(keyStroke, name);
        am.put(name, action);
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
       
        //These paint the barrier spaces as they are currently
        g.drawRect(20, 450, 40, 40);
        g.drawRect(70, 450, 40, 40);
        g.drawRect(120, 450, 40, 40);
        g.drawRect(170, 450, 40, 40);
        g.drawRect(220, 450, 40, 40);
        
        g.drawRect(740, 450, 40, 40);
        g.drawRect(690, 450, 40, 40);
        g.drawRect(640, 450, 40, 40);
        g.drawRect(590, 450, 40, 40);
        g.drawRect(540, 450, 40, 40);
        
        //Paint ScreenTimer
        
       
        //Paint barriers
        paintBarriers(g);
        
        //Paint debris
        paintDebris(g);
       
        paintBins(g);
        
        //Paint player
        paintPlayer(g);
        /**
        //Paint health bar
        paintHealthBar(g);
        
        g.drawString(Integer.toString(timeElapsed), 40, 40);
        */
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
    
    private void paintBarriers(Graphics g) {
    	ArrayList<Barriers> barriers = gc.getItems().getAllBarriers();
    	for (Barriers b : barriers) {
    		if (b.getType() == eBarrierType.Gabion) {
    			//For now, calling a gabion a green rectangle
    			g.setColor(Color.GREEN);
    			g.fillRect(b.getPosX(), b.getPosY(), b.getWidth(), b.getHeight());
    		}
    		else if (b.getType() == eBarrierType.Wall) {
    			//Calling a wall a dark gray wall
    			g.setColor(Color.DARK_GRAY);
    			g.fillRect(b.getPosX(), b.getPosY(), b.getWidth(), b.getHeight());
    		}
    	}
    }
    
    private void paintDebris(Graphics g) {
    	ArrayList<Debris> debris = gc.getItems().getAllDebris();
    	for (Debris d : debris) {

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
    		
    		/*
    		if (d.getState() == eFloaterState.LIFTED) {
    			g.fillOval(actives.getPlayer().getPlayer().getPosX(), actives.getPlayer().getPlayer().getPosY() - d.getHeight(), 
    					d.getWidth(), d.getHeight());
    		}
    		*/
    	}
    }
    
    private void paintBins(Graphics g){
    	Bin trash = gc.getItems().getTrashBin();
    	Bin recycle = gc.getItems().getRecycleBin();
    	g.setColor(Color.BLACK);
    	g.fillOval(trash.getPosX(), trash.getPosY(), trash.getWidth(), trash.getHeight());
    	g.setColor(Color.BLUE);
    	g.fillOval(recycle.getPosX(), recycle.getPosY(), recycle.getWidth(), recycle.getHeight());
    }
    
    private void paintHealthBar(Graphics g) {
    	HealthBar hb = gc.getItems().getHealthBar();
    	double currHealth = hb.getHealth();
    	double maxHealth = hb.getMaxHealth();
    	double barY = hb.getPosY();
    	double barX = hb.getPosX();
    	double barWidth = hb.getWidth();
    	double barHeight = hb.getHeight();
    	
    	//Concept: painting two rectangles: One that is the outline
    	//of the health bar, one that is the current health
    	//Backing
    	g.setColor(Color.WHITE);
    	g.fillRect((int) barY,(int) barX,(int) barWidth,(int) barHeight);
    	//Health
    	double currHealthHeight = (currHealth / maxHealth)*barHeight;
    	double currHealthY = (barHeight - currHealthHeight) + barY;
        g.setColor(Color.RED);
    	g.fillRect((int) barX,(int) currHealthY,(int) barWidth,(int) currHealthHeight);
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