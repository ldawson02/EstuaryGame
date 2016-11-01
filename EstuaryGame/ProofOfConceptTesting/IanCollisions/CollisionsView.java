package IanCollisions;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * http://stackoverflow.com/questions/28228121/
 * see http://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
 *     http://docs.oracle.com/javase/tutorial/2d/
 */
public class CollisionsView extends JComponent {

    private static final long serialVersionUID = 1L;

    int x = 350;
    int y = 250;
    
    int timeElapsed = 0;
    
    public ArrayList<DebrisView> debris;
    Rectangle player;
    double currHealth = 45;
    Timer ticker;
    int ticktime = 30;
    
    final int speed = 10;
    final int fallrate = 2;

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
                frame.getContentPane().add(new CollisionsView());
                frame.setVisible(true);
            }
        });
    }

    public CollisionsView() {
        bindKeyWith("y.up", KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), new VerticalAction(-speed));
        bindKeyWith("y.down", KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), new VerticalAction(speed));
        bindKeyWith("x.left", KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), new HorizontalAction(-speed));
        bindKeyWith("x.right", KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), new HorizontalAction(speed));
    
        initShapes();
        
        ticker = new Timer(ticktime, new Ticker());
        ticker.start();
        
        
    }

    protected void bindKeyWith(String name, KeyStroke keyStroke, Action action) {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(keyStroke, name);
        am.put(name, action);
    }

    private void initShapes() {
    	debris = new ArrayList<DebrisView>();
    	player = new Rectangle(x, y, 50, 50);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.BLACK);
        g.fillRect((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());
        
    	double maxHealth = 100;
    	double barY = 100;
    	int barX = 100;
    	int barWidth = 30;
    	double barHeight = 100;
    	
    	//Concept: painting two rectangles: One that is the outline
    	//of the health bar, one that is the current health
    	g.setColor(Color.WHITE);
    	g.fillRect((int)barX,(int) barY, barWidth,(int) barHeight);
    	
    	double currHealthHeight = (currHealth / maxHealth)*barHeight;
    	double currHealthY = (barHeight - currHealthHeight) + barY;
    	System.out.println(currHealthHeight + "" + currHealthY);
        g.setColor(Color.RED);
    	g.fillRect(barX+1,(int) currHealthY, barWidth-1,(int) currHealthHeight);
    	
        g.drawString(Integer.toString(timeElapsed), 40, 40);

    	g.setColor(Color.BLACK);
    	g.drawRect(barX, (int)barY, barWidth-1, (int)barHeight);
        
        paintDebris(g);
        
        //Fuck witta timer
        
        
    }

    private void paintDebris(Graphics g) {
    	for (DebrisView d : debris) {
    		switch (d.getState()) {
    			
    		}
    	}
    }
    
    private void customPaintEllipse(Graphics g, Ellipse2D.Double ell) {
    	g.fillOval((int) ell.getX(), (int) ell.getY(), (int) ell.getWidth(), (int) ell.getHeight());
    }
    
    private void updatePlayer() {
    	player.x = x;
    	player.y = y;
    }
    
    private void updateDebris() {
    	updateDebrisStates();
    	moveDebris();
    }
    
    private void updateDebrisStates() {
    	for (DebrisView d : debris) {
    		if (d.getEllipse().intersects(player)) {
    			d.setState(eShapeState.TOUCHED);
    		}
    		else if (d.getEllipse().y > 400) {
    			d.setState(eShapeState.REST);
    		}
    		else {
    			d.setState(eShapeState.FREE);
    		}
    	}
    }
    
    private void moveDebris() {
    	for (DebrisView d : debris) {
    		if (d.getState() == eShapeState.FREE) {
    			d.getEllipse().y += fallrate;
    		}
    	}
    }
    
    public abstract class MoveAction extends AbstractAction {

        private int delta;

        public MoveAction(int delta) {
            this.delta = delta;
        }

        public int getDelta() {
            return delta;
        }

        protected abstract void applyDelta();

        @Override
        public void actionPerformed(ActionEvent e) {
            applyDelta();
        }

    }

    public class VerticalAction extends MoveAction {

        public VerticalAction(int delta) {
            super(delta);
        }

        @Override
        protected void applyDelta() {
            int delta = getDelta();
            y += delta;
            if (y < 0) {
                y = 0;
            } else if (y + 100 > getHeight()) {
                y = getHeight() - 100;
            }
            updatePlayer();
        }
    }
    
    public class HorizontalAction extends MoveAction {

        public HorizontalAction(int delta) {
            super(delta);
        }

        @Override
        protected void applyDelta() {
            int delta = getDelta();
            x += delta;
            if (x < 0) {
                x = 0;
            } else if (x + 100 > getWidth()) {
                x = getWidth() - 100;
            }
            updatePlayer();
        }

    }
    
    public class Ticker implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (debris.size() == 0) {
				debris.add(new DebrisView(new Ellipse2D.Double(300, 0, 50, 50)));
			}
			
			updateDebris();
			
			timeElapsed += ticktime;
			repaint();
		}

		
	}
}