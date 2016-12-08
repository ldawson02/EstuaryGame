package Serialization;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
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

import eNums.eAnimation;
import model.Debris;
import view.EstuaryGame;
import view.ImageLibrary;
import view.ImageSequence;

/**
 * http://stackoverflow.com/questions/28228121/
 * see http://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
 *     http://docs.oracle.com/javase/tutorial/2d/
 */
public class View extends JComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	static int screenWidth = 784;
	static int screenHeight = 561;
	ImageLibrary quicklib;
	int initialWidth;
	int initialHeight;

	Controller gc;

	public View() {

		quicklib = ImageLibrary.loadLibrary();
		gc = new Controller(this);


	}

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
				frame.getContentPane().add(new View());
				frame.setVisible(true);
			}
		});
	}

	protected void bindKeyWith(String name, KeyStroke keyStroke, Action action) {
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();

		im.put(keyStroke, name);
		am.put(name, action);
	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.


		g.setColor(Color.BLACK);

		paintItems(g);

	}

	public void paintItems(Graphics g) {
		for (Debris d: gc.getItems()) {
			quicklib.draw(d);
			g.drawImage(quicklib.draw(d), d.getPosX(), d.getPosY(), this);
		}
	}



}