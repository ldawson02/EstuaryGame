package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.GameController;
import controller.MouseController;
/**
 * when goes to the titale scree, we will have a start game and tutorial option
 *
 */
public class TitleScreen extends JPanel implements ActionListener{

    JButton btn1 = new JButton("Start");
    JButton btn2 = new JButton("Tutorial");
    EstuaryGame game;
    Tutorial tutorial;
    Image background;
    int time = 30;
    double scaleFactor = 1.0;
    
    public static final int defaultScreenX = 800;
    public static final int defaultScreenY = 600;
    
    /**
     * construct a title screen using action listener
     */
    public TitleScreen() {
    	this.setLayout(null);
    	btn1.setActionCommand("START");
    	btn1.addActionListener(this);
    	btn1.setBounds(new Rectangle(310,315,200,50));
    	this.add(btn1);
    	
    	btn2.setActionCommand("TUTORIAL");
    	btn2.addActionListener(this);
    	btn2.setBounds(new Rectangle(310,375,200,50));
    	this.add(btn2);
    	
    	this.setBackground(Color.BLACK);
    	loadTitleBG();
    	repaint();
    }
/**
 * load the background pic
 */
	public void loadTitleBG() {
		String srcpath = "resources" + File.separator + "titleBackground" + File.separator + "titlebackground.png";
		background = createImage(srcpath).getScaledInstance(800, 600, Image.SCALE_SMOOTH);
	}
    
	/**
	 * when click the start button, it goes to the game controller and main game,
	 *  if goes to tutorial, goes to tutorial screen
	 */
	public void actionPerformed(ActionEvent e){
		CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
		String cmd = e.getActionCommand();
        if(cmd.equals("START")){
    		c1.next(EstuaryGame.getCards());
    		EstuaryGame.gc = new GameController(EstuaryGame.mainGame);
    		EstuaryGame.mc = new MouseController();
    		EstuaryGame.mc.setGC(EstuaryGame.gc);
            
    		EstuaryGame.mainGame.addMouseListener(EstuaryGame.mc);
    		EstuaryGame.mainGame.addMouseMotionListener(EstuaryGame.mc);
        	
        }
        
        if(cmd.equals("TUTORIAL")){
        	c1.show(EstuaryGame.getCards(), "Tutorial");
        	//Any set up for tutorial to run
        	//tutorial = new Tutorial();
        	EstuaryGame.tutorial.start();
        	
        	/**
    		EstuaryGame.gc = new GameController(EstuaryGame.mainGame);
    		EstuaryGame.mc = new MouseController();
    		EstuaryGame.mc.setGC(EstuaryGame.gc);
            
    		EstuaryGame.gc.setTutorial(tutorial);
    		EstuaryGame.mainGame.addMouseListener(EstuaryGame.mc);
    		EstuaryGame.mainGame.addMouseMotionListener(EstuaryGame.mc);
			*/
        }
    }
	
	/**
	 * paint the component in title screen
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this);
		
	}
	
	 @Override
	 /**
	  * get the dimension preferred size
	  */
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(800, 600));
	    }

	 private void checkWindowDimensionChange() {
	    	if ((this.getWidth() != TitleScreen.defaultScreenX) || (this.getHeight() != TitleScreen.defaultScreenY)) {
	    		
	    		System.out.println("Window size changed!");
	    		System.out.println("From " + this.getWidth() + "x" + this.getHeight());
	    		double scaleX = (double)(this.getWidth()) / (double)(TitleScreen.defaultScreenX);
	    		double scaleY = (double)(this.getHeight()) / (double)(TitleScreen.defaultScreenY);
	    		
	    		
	    		//Scale according to the smaller (maintains overall 3:4 ratio)
	    		if (scaleX > scaleY) {
	    			scaleFactor = scaleY;
	    		}
	    		else {
	    			scaleFactor = scaleX;
	    		}
	    		
	    	}
	    }
	 
	 public class TitleTimer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		 
	 }
	 
	 private BufferedImage createImage(String filename){
			BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(new File(filename));
				return bufferedImage;
			} catch (IOException e) {
				System.out.println("Couldn't create image from " + filename);
				e.printStackTrace();
			}
			return null;
		}
}
