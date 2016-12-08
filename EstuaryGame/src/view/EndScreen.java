package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameController;
import controller.MouseController;

public class EndScreen extends JPanel implements ActionListener{
	JButton btn3 = new JButton("Replay");
    
    Image background;
	
    public static final int defaultScreenX = 800;
    public static final int defaultScreenY = 600;
	
	public EndScreen(){
		this.setLayout(null);
		btn3.setActionCommand("REPLAY");
		btn3.addActionListener(this);
		btn3.setBounds(new Rectangle(310,300,200,50));

		

		this.addComponentListener ( new ComponentAdapter ()
		{
			public void componentShown ( ComponentEvent e )
			{
				System.out.println ( "End Screen shown" );
				if(EndScreen.this.isShowing()){
					System.out.println ( "Component hidden" );
					int endHealth = EstuaryGame.gc.getItems().getHealthBar().getHealth();
					JLabel label1 = new JLabel("Overall Health of Estuary: " + endHealth);
					label1.setBounds(new Rectangle(200,150,600,30));
					label1.setFont(label1.getFont().deriveFont(20.0f));
					JLabel label2 = new JLabel();
					if(endHealth == 0){
						String srcpath = "resources" + File.separator + "endBackgroundBad" + File.separator + "badend.png";
						background = createImage(srcpath).getScaledInstance(defaultScreenX, defaultScreenY, Image.SCALE_SMOOTH);
						label2.setText("Sorry, the estuary was not saved! Try again next time!");
					}
					else if(endHealth > 0 && endHealth < 50){
						String srcpath = "resources" + File.separator + "endBackgroundOkay" + File.separator + "okayend.png";
						background = createImage(srcpath).getScaledInstance(defaultScreenX, defaultScreenY, Image.SCALE_SMOOTH);
						label2.setText("Good work! But it looks like the estuary is still at risk.");
					}
					else if(endHealth > 50){
						String srcpath = "resources" + File.separator + "endBackgroundGood" + File.separator + "goodend.png";
						background = createImage(srcpath).getScaledInstance(defaultScreenX, defaultScreenY, Image.SCALE_SMOOTH);
						label2.setText("Great job! The estuary is safe!");
					}

					label2.setBounds(new Rectangle(50,50,800,100));
					label2.setFont(label2.getFont().deriveFont(30.0f));

					
					EndScreen.this.removeAll();
					EndScreen.this.add(btn3, BorderLayout.CENTER);
					EndScreen.this.add(label1);
					EndScreen.this.add(label2);
					EndScreen.this.repaint();
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
			
			EstuaryGame.cards.remove(EstuaryGame.tutorial);
			EstuaryGame.tutorial = new Tutorial();
			EstuaryGame.cards.add(EstuaryGame.tutorial, 2);
			c1.show(EstuaryGame.getCards(), "TitleScreen");
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this);
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
