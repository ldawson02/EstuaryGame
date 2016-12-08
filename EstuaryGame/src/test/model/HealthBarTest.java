package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameController;
import eNums.eDebrisType;
import eNums.eFloaterState;
import eNums.eHealthChanges;
import model.Coast;
import model.Debris;
import model.HealthBar;
import view.EstuaryGame;

public class HealthBarTest {
	
	static HealthBar healthBar;
	static GameController gc;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gc = new GameController(new EstuaryGame());
		healthBar = new HealthBar();
	}
	
	
	@Test
	public void maxHealthTest() {
		//Max health is 100
		healthBar.setHealth(0);
		healthBar.update(50);
		healthBar.update(-5);
		healthBar.update(70);
		
		assertEquals(healthBar.getHealth(), 100);
		assertEquals(healthBar.getMaxHealth(), 100);
	}
	@Test
	public void minHealthTest(){
		//Min health is 0
		healthBar.setHealth(0);
		healthBar.update(12);
		healthBar.update(-20);
		
		assertEquals(healthBar.getHealth(), 0);
		assertEquals(healthBar.getMinHealth(), 0);
	}
	@Test
	public void updateHealthBarTest() {
		healthBar.setHealth(0);
		healthBar.update(20);
		healthBar.update(-15);
		
		assertEquals(healthBar.getHealth(),5);
	}
	
	@Test
	public void gameHealthBarTest(){
		int initialHealth1 = gc.getItems().getHealthBar().getHealth();
		gc.getItems().getHealthBar().update(eHealthChanges.IncorrectBin.getDelta());
		assertEquals(gc.getItems().getHealthBar().getHealth(), initialHealth1 + eHealthChanges.IncorrectBin.getDelta());
		
		int initialHealth2 = gc.getItems().getHealthBar().getHealth();
		gc.getItems().getHealthBar().update(eHealthChanges.CoastEroded.getDelta());
		assertEquals(gc.getItems().getHealthBar().getHealth(), initialHealth2 + eHealthChanges.CoastEroded.getDelta());
	}
	
	@Test
	public void getHeightandWidth(){
		assertEquals(healthBar.getHeight(),25);
		assertEquals(healthBar.getWidth(),160);
	}
	

}
