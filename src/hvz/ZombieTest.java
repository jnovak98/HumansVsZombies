import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.math.BigInteger;

import org.junit.Test;
import org.junit.Before;

public class ZombieTest {
	HvZ game;
	Zombie a;
	Zombie b;
	Zombie c;
	
	@Before
	public void initialize(){
		game = new HvZ();
		a = new Zombie("a");
		b = new Zombie("b");
		c = new Zombie("c");
	}
	
	@Test
	public void testZombieNominal(){
		//covers structured basis and good data
		game.insert(a, big(0), big(0));
		game.insert(b, big(100), big(100));
		game.insert(c, big(-1000), big(-1000));
		assertTrue(game.zombie(big(0),big(0))==a);
		assertTrue(game.zombie(big(100),big(100))==b);
		assertTrue(game.zombie(big(-1000),big(-1000))==c);
	}
	
	//there are no boundaries to test with zombie()
	
	@Test(expected = NullPointerException.class)
	public void testZombieNull(){
		game.insert(null, big(0), big(0));
		game.zombie(big(0), big(0));
		//null should not be allowed in table to begin with, so throws error
		fail();
	}
	
	@Test
	public void testInsertNominal(){
		//covers good data and structural basis
		game.insert(a, big(0), big(0));
		game.insert(b, big(1), big(1));
		assertTrue(game.zombie(big(0),big(0))==a);
		assertTrue(game.zombie(big(1),big(1))==b);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertOnExisting(){
		//covers bad data and structural basis
		game.insert(a, big(0), big(0));
		game.insert(b, big(0), big(0));
		fail();
	}
	
	@Test(expected = NullPointerException.class)
	public void testInsertNull(){
		//covers bad data and structural basis
		game.insert(null, big(0), big(0));
		fail();
	}
	
	//No boundaries can be tested in insert()
	
	@Test
	public void testDeleteNominal() {
		//covers structural basis and good data
		game.insert(a, big(0), big(0));
		assertTrue(game.delete(big(0),big(0))==a);
		assertTrue(game.zombie(big(0),big(0))==null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNonExisting(){
		game.insert(a, big(0), big(0));
		game.delete(big(1), big(0));
		fail();
	}
	
	//no boundaries can be tested in delete()
	
	@Test
	public void testJavelinNominal() {
		//covers some of structural basis and good data
		game.insert(a, big(0), big(0));
		game.insert(b, big(3), big(0));
		game.insert(c, big(-1), big(0));
		ZombiePoint p1 = game.javelin(big(1));
		ZombiePoint p2 = game.javelin(big(-1000));
		assertTrue(game.zombie(p1.getX(), p1.getY())==a);
		assertTrue(game.zombie(p2.getX(), p2.getY())==c);
	}
	
	@Test
	public void testJavelinZombieInOneDirection() {
		//covers other part of structural basis and good data
		game.insert(a, big(0), big(0));
		game.insert(b, big(5), big(0));
		ZombiePoint p1 = game.javelin(big(-100));
		ZombiePoint p2 = game.javelin(big(100));
		assertTrue(game.zombie(p1.getX(), p1.getY())==a);
		assertTrue(game.zombie(p2.getX(), p2.getY())==b);
	}

	@Test
	public void testJavelinNoZombies() {
		//covers rest of structural basis and bad data
		assertTrue(game.javelin(big(0))==null);
	}
	
	@Test
	public void testJavelinBoundaries() {
		game.insert(a, big(0), big(0));
		game.insert(b, big(2), big(0));
		ZombiePoint p1 = game.javelin(big(0));
		assertTrue(game.zombie(p1.getX(), p1.getY())==a); //on top a zombie should return that zombie
		
		ZombiePoint p2 = game.javelin(big(1)); //right in between zombies should return either zombie
		assertTrue(game.zombie(p2.getX(), p2.getY())==a || game.zombie(p2.getX(), p2.getY())==b);
	}
	
	@Test(expected = NullPointerException.class)
	public void testJavelinNull(){
		//covers bad data and structural basis
		game.insert(a, big(0), big(0));
		game.javelin(null);
		fail();
	}
	
	@Test
	public void testArrowNominal() {
		//covers good data and structural basis
		game.insert(a, big(0), big(0));
		game.insert(b, big(100), big(0));
		ZombiePoint p1 = game.arrow("left");
		ZombiePoint p2 = game.arrow("right");
		assertTrue(game.zombie(p1.getX(), p1.getY())==a);
		assertTrue(game.zombie(p2.getX(), p2.getY())==b);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testArrowInvalidString() {
		//covers bad data and structural basis
		game.insert(a, big(0), big(0));
		game.insert(b, big(100), big(0));
		game.arrow("test");
		fail();
	}
	
	@Test(expected = NullPointerException.class)
	public void testArrowNullString() {
		//covers bad data
		game.insert(a, big(0), big(0));
		game.insert(b, big(100), big(0));
		game.arrow(null);
		fail();
	}
	
	@Test
	public void testArrowBoundaries() {
		game.insert(a, big(0), big(0));
		game.insert(b, big(0), big(1));
		ZombiePoint p = game.arrow("left");
		assertTrue(game.zombie(p.getX(), p.getY())==a || game.zombie(p.getX(), p.getY())==b);
	}
	
	public BigInteger big(int i){
		return new BigInteger(""+i);
	}
}
