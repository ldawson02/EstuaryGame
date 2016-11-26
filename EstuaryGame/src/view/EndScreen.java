package view;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JComponent;

public class EndScreen extends JComponent implements ActionListener{
	JButton btn3 = new JButton("Replay");
    
    public EndScreen(){
    	btn3.setActionCommand("REPLAY");
    	btn3.addActionListener(this);
    	btn3.setBounds(new Rectangle(400,300,100,30));
    	this.add(btn3);
    }
	
	public void actionPerformed(ActionEvent e){
		CardLayout c1 = (CardLayout) (EstuaryGame.cards.getLayout());
		String cmd = e.getActionCommand();
        if(cmd.equals("REPLAY")){
    		c1.first(EstuaryGame.cards);
        }
    }


}
