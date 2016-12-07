package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import controller.GameController;
import controller.MouseController;

public class TitleScreen extends JPanel implements ActionListener{

    JButton btn1 = new JButton("Start");
    JButton btn2 = new JButton("Tutorial");
    EstuaryGame game;
    Tutorial tutorial;
    
    
    public TitleScreen(EstuaryGame g, Tutorial t) {
    	game = g;
    	tutorial = t;
    	this.setLayout(null);
    	btn1.setActionCommand("START");
    	btn1.addActionListener(this);
    	btn1.setBounds(new Rectangle(310,200,200,50));
    	this.add(btn1);
    	
    	btn2.setActionCommand("TUTORIAL");
    	btn2.addActionListener(this);
    	btn2.setBounds(new Rectangle(310,275,200,50));
    	this.add(btn2);
    	
    	this.setBackground(Color.BLACK);
    	repaint();
    }

	
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
        	tutorial.start();
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
	
	@Override
	public void paintComponent(Graphics g) {
		
		
	}
	
	 @Override
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(800, 600));
	    }


}
