package view;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import controller.GameController;
import controller.MouseController;
import eNums.eTutorialState;
import model.Item;

public class Tutorial extends JComponent implements ActionListener{

	JButton btn1 = new JButton("Go to main game ->");
	private eTutorialState state;
	public boolean spotlight;
	public int timeInStage;

	public void setSpotlight(boolean t){
		spotlight = t;
	}

		public Tutorial(){
	    	btn1.setActionCommand("START GAME");
	    	btn1.addActionListener(this);
	    	btn1.setBounds(new Rectangle(310,500,200,50));
	    	this.add(btn1);
	    	
	    	
	    }
	
		public eTutorialState getState(){
			return state;
		}
		
		public void nextState(){
			state = state.nextState();
		}
		
		public void debrisCaught(){
			spotlight = false;
		}
		
		public void stageComplete(){
			timeInStage = 0;
			nextState();
		}
		
		public int getTimeInStage(){
			return timeInStage;
		}
		
		public void addTime(int t){
			timeInStage+=t;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
			String cmd = e.getActionCommand();
	        if(cmd.equals("START GAME")){
	    		c1.show(EstuaryGame.getCards(), "MainGame");
	    		EstuaryGame.gc = new GameController(EstuaryGame.mainGame);
	    		EstuaryGame.mc = new MouseController();
	    		EstuaryGame.mc.setGC(EstuaryGame.gc);
	            
	    		EstuaryGame.mainGame.addMouseListener(EstuaryGame.mc);
	    		EstuaryGame.mainGame.addMouseMotionListener(EstuaryGame.mc);
	        	
	        }
			
		}
	
}
