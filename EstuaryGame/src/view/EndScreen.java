package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import controller.GameController;
import controller.MouseController;

public class EndScreen extends JComponent implements ActionListener{
	JButton btn3 = new JButton("Replay");
    
    public EndScreen(){
    	btn3.setActionCommand("REPLAY");
    	btn3.addActionListener(this);
    	btn3.setBounds(new Rectangle(310,300,200,50));
    	
    	this.add(btn3, BorderLayout.CENTER);
    	

    	 this.addComponentListener ( new ComponentAdapter ()
         {
             public void componentShown ( ComponentEvent e )
             {
                 System.out.println ( "End Screen shown" );
                 if(EndScreen.this.isShowing()){
             		System.out.println ( "Component hidden" );
             		/*int endHealth = EstuaryGame.gc.getItems().getHealthBar().getHealth();
                    JLabel label1 = new JLabel("Overall Health of Estuary: " + endHealth);
                    label1.setBounds(new Rectangle(200,150,600,30));
                    label1.setFont(label1.getFont().deriveFont(20.0f));
                    JLabel label2 = new JLabel();
                    if(endHealth == 0){
                    	label2.setText("Sorry the estuary was not saved! Try again next time");
                    }
                    else if(endHealth > 0 && endHealth < 50){
                    	label2.setText("Nice try! But look out the estuary is still at risk.");
                    }
                    else if(endHealth > 50){
                    	label2.setText("Good work on keeping the estuary safe!");
                    }
                    
                    label2.setBounds(new Rectangle(200,50,800,100));
                    label2.setFont(label2.getFont().deriveFont(30.0f));
             

                    EndScreen.this.add(label1);
                    EndScreen.this.add(label2);
                    EndScreen.this.repaint();*/
             	}
                 
         		
             }

             public void componentHidden ( ComponentEvent e )
             {
                System.out.println ( "Component hidden" );

             }
         } );
    }
	
	public void actionPerformed(ActionEvent e){
		CardLayout c1 = (CardLayout) (EstuaryGame.getCards().getLayout());
		String cmd = e.getActionCommand();
        if(cmd.equals("REPLAY")){
        	EstuaryGame.cards.remove(EstuaryGame.mainGame);
        	EstuaryGame.mainGame = new EstuaryGame();
        	EstuaryGame.cards.add(EstuaryGame.mainGame, 1);
    		c1.first(EstuaryGame.getCards());
        }
    }


}