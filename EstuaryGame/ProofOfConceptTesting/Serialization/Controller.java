package Serialization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import eNums.eDebrisType;
import model.Debris;

public class Controller implements Serializable {

	int timeElapsed = 0;
	Timer ticker;
	int ticktime = 30;

	public ArrayList<Debris> items = new ArrayList<Debris>();

	View mainView;

	public Controller(View mv) {
		mainView = mv;
		ticker = new Timer(ticktime, new Ticker());
		ticker.start();
		setUp();
	}

	public void setUp() {
		Debris d1 = new Debris(eDebrisType.RECYCLING);
		d1.updatePos(50, 100);
		Debris d2 = new Debris(eDebrisType.TRASH);
		d2.updatePos(150, 300);
		Debris d3 = new Debris(eDebrisType.RECYCLING);
		d3.updatePos(300, 50);
		items.add(d1);
		items.add(d2);
		items.add(d3);

		mainView.bindKeyWith("serialize", KeyStroke.getKeyStroke('1'), new SerializeAction());
		mainView.bindKeyWith("readserialized", KeyStroke.getKeyStroke('2'), new ReadSerializeAction());
		mainView.bindKeyWith("cleanupserialized", KeyStroke.getKeyStroke('3'), new CleanUpSerializeAction());
	}

	public void moveItems() {
		for (Debris d: items) {
			d.updatePosX(d.getPosX() + 1);
			d.updatePosY(d.getPosY() + 2);
			if (d.getPosX() > 800) {
				d.updatePosX(0);
			}
			if (d.getPosY() > 600) {
				d.updatePosY(0);
			}
		}
	}

	/**
	 * @return the items
	 */
	public ArrayList<Debris> getItems() {
		return items;
	}

	public class Ticker implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			timeElapsed += ticktime;
			moveItems();
			mainView.repaint();
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

	public class SerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Serialize to file
			try
			{
				System.out.print("writing");
				FileOutputStream fos = new FileOutputStream("testSerialize.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(items);
				oos.close();
			}
			catch (Exception ex)
			{
				System.out.println("Exception thrown during test: " + ex.toString());
			}

		}


	}

	public class ReadSerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Serialize to file
			try
			{
				System.out.print("reading");
				FileInputStream fis = new FileInputStream("testSerialize.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);

				ArrayList<Debris> readObject = (ArrayList<Debris>) ois.readObject();
				ArrayList<Debris> readIn = readObject;
				items = readIn;
				

				ois.close();
			}
			catch (Exception ex)
			{
				System.out.println("Exception thrown during test: " + ex.toString());
			}
		}


	}
	
	public class CleanUpSerializeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Serialize to file
			try
			{
				//Clean up the file
				new File("testSerialize.ser").delete();
			}
			catch (Exception ex)
			{
				System.out.println("Exception thrown during test: " + ex.toString());
			}
		}


	}

}
