package view;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import controller.GameController;
import controller.MouseController;
import controller.TutorialController;
import eNums.eTutorialState;
import model.Item;

public class Tutorial extends EstuaryGame implements ActionListener{

	JButton btn1 = new JButton("Go to main game ->");
	private eTutorialState state = eTutorialState.DEBRIS;
	public boolean spotlight = false;
	public boolean spotlightSwitched = false;
	public TutorialController tc;

	public boolean getSpotlightSwitched(){
		return spotlightSwitched;
	}

	public void setSpotlight(boolean t){
		spotlight = t;
		spotlightSwitched = true;
	}

	public Tutorial(){
		super();
		btn1.setActionCommand("START GAME");
		btn1.addActionListener(this);
		btn1.setBounds(new Rectangle(310,500,200,50));
		this.add(btn1);
	}
	
	public void start(){
		tc = new TutorialController(this);
		this.mc = new MouseController();
		this.mc.setGC(tc);
		this.gc = tc;
		
		this.mainGame.addMouseListener(this.mc);
		this.mainGame.addMouseMotionListener(this.mc);
	}

	public void wrongBin(){
		//TODO: create animation, words on screen, something like that if they try to throw to wrong bin
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


	@Override
	public void actionPerformed(ActionEvent e) {
		CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
		String cmd = e.getActionCommand();
		if(cmd.equals("START GAME")){
			c1.show(EstuaryGame.getCards(), "MainGame");
			/**
			EstuaryGame.gc.tutorialOff();
			*/
			EstuaryGame.gc = new GameController(EstuaryGame.mainGame);
			EstuaryGame.mc = new MouseController();
			EstuaryGame.mc.setGC(EstuaryGame.gc);

			EstuaryGame.mainGame.addMouseListener(EstuaryGame.mc);
			EstuaryGame.mainGame.addMouseMotionListener(EstuaryGame.mc);
			 
		}

	}

}
