package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

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
                frame.getContentPane().setBackground(Color.WHITE);
                frame.getContentPane().add(new EstuaryGame());
                frame.setVisible(true);
            }
        });
    }

    public EstuaryGame() {
    	//Initialize a new GameController and connect them
    	gc = new GameController();
    	gc.setMainGame(this);
    	gc.setup();
 
    }

    public void bindKeyWith(String name, KeyStroke keyStroke, Action action) {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(keyStroke, name);
        am.put(name, action);
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
        
        //Paint player
        paintPlayer(g);
        
        //Paint health bar
        paintHealthBar(g);
        
        
        g.drawString(Integer.toString(timeElapsed), 40, 40);
        
 
    }
    
    private void paintBackground(Graphics g) {
    	//TODO: get a background
    }
    
    private void paintBarriers(Graphics g) {
    	ArrayList<Barriers> barriers = gc.getItems().getAllBarriers();
    	for (Barriers b : barriers) {
    		if (b.getType() == eBarrierType.Gabion) {
    			//TODO: paint like a gabion
    		}
    		else if (b.getType() == eBarrierType.Wall) {
    			//TODO: paint like a wall
    		}
    	}
    }
    
    private void paintDebris(Graphics g) {
    	ArrayList<Debris> debris = gc.getItems().getAllDebris();
    	for (Debris d : debris) {
    		if (d.getType() == eDebrisType.TRASH) {
    			//TODO: paint like trash
    		}
    		if (d.getType() == eDebrisType.RECYCLING) {
    			//TODO: paint like recycling
    		}
    	}
    }
    
    private void paintHealthBar(Graphics g) {
    	HealthBar hb = gc.getItems().getHealthBar();
    	int currHealth = hb.getHealth();
    	int maxHealth = hb.getMaxHealth();
    	//TODO: paint the health bar based on health / max health
    }
    
    private void paintPlayer(Graphics g) {
    	g.fillRect((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());
    }

    private class DebrisWrapper {
    	
    	Ellipse2D.Double hitBox;
    	Debris debrisItem;
    	
    	DebrisWrapper(Debris item, Ellipse2D.Double shape) {
    		debrisItem = item;
    		hitBox = shape;
    	}
    	
    }
    
}