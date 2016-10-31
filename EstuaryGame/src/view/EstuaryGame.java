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
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.GameController;
import eNums.eBarrierType;
import eNums.eDebrisType;
import model.Barriers;
import model.Debris;
import model.HealthBar;

public class EstuaryGame extends JComponent {

    private static final long serialVersionUID = 1L;

    private GameController gc;
    
    BufferedImage bg;
    
    int x = 350;
    int y = 250;
    
    int timeElapsed = 0;
    
    Rectangle player;
    
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
                frame.getContentPane().add(new EstuaryGame());
                frame.setVisible(true);
            }
        });
    }

    public EstuaryGame() {
    	//Initialize a new GameController and connect them
    	//gc = new GameController(this);
    	
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
    		//yikes
    	}
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        //Handle collisions, probably
        
        //Paint background
        paintBackground(g);
        
        g.setColor(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g.create();
        QuadCurve2D quadLeft = new QuadCurve2D.Double(0, 0, 300, 150, 0, 300);
        QuadCurve2D quadRight = new QuadCurve2D.Double(800, 0, 500, 150, 800, 300);
        g2d.draw(quadLeft);
        g2d.draw(quadRight);
        
        /**
        //Paint barriers
        paintBarriers(g);
        
        //Paint debris
        paintDebris(g);
        
        //Paint player
        paintPlayer(g);
        
        //Paint health bar
        paintHealthBar(g);
        
        g.drawString(Integer.toString(timeElapsed), 40, 40);
        */
    }
    
    private void paintBackground(Graphics g) {
    	//TODO: get a background
    	g.drawImage(bg, 0, 0, this);
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
    		if (d.getType() == eDebrisType.RECYCLING) {
    			//TODO: paint like recycling
    			//Calling recycling a blue circle
    			g.setColor(Color.BLUE);
    			g.fillOval(d.getPosX(), d.getPosY(), d.getWidth(), d.getHeight());
    		}
    	}
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
    	g.fillRect((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());
    }

    private void updateWrappers() {
    	
    }
    
}