package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import controller.GameController;
import controller.MouseController;

public class TitleScreen extends JComponent implements ActionListener{

    JButton btn1 = new JButton("Start");
    
    
    
    public TitleScreen(){
    	btn1.setActionCommand("START");
    	btn1.addActionListener(this);
    	btn1.setBounds(new Rectangle(400,300,100,30));
    	this.add(btn1);
    }

	
	
	public void actionPerformed(ActionEvent e){
		CardLayout c1 = (CardLayout) (EstuaryGame.cards.getLayout());
		String cmd = e.getActionCommand();
        if(cmd.equals("START")){
    		c1.next(EstuaryGame.cards);
    		
        }
    }
	
	 @Override
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(800, 600));
	    }


}
