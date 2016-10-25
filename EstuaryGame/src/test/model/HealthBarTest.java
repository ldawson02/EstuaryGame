package test.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.HealthBar;

public class HealthBarTest {

		HealthBar healthBar = new HealthBar();
	
	
	@Test
	public void maxHealthtest() {
		//Max health is 100
		healthBar.update(50);
		healthBar.update(70);
		healthBar.update(-5);
		
		assertEquals(healthBar.gethealth(), 100);
	}
	@Test
	public void minHealthTest(){
		//Min health is 0
		healthBar.update(12);
		healthBar.update(-20);
		
		assertEquals(healthBar.gethealth(), 0);
	}
	@Test
	public void updatehealthBar() {
		healthBar.update(20);
		healthBar.update(-15);
		
		assertEquals(healthBar.gethealth(),5);
	}

}
