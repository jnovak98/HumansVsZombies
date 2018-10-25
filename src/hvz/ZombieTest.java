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
		assertTrue("Can find the added Zombie", game.zombie(big(0),big(0))==a);
		assertTrue("Can find the added Zombie", game.zombie(big(100),big(100))==b);
		assertTrue("Can find the added Zombie", game.zombie(big(-1000),big(-1000))==c);
	}
	
	//there are no boundaries to test with zombie()
	
	@Test(expected = NullPointerException.class)
	public void testZombieNull(){
		game.insert(null, big(0), big(0));
		game.zombie(big(0), big(0));
		//null should not be allowed in table to begin with, so throws error
		fail("should have thrown an error when trying to find null");
	}
	
	@Test
	public void testInsertNominal(){
		//covers good data and structural basis
		game.insert(a, big(0), big(0));
		game.insert(b, big(1), big(1));
		assertTrue("Can add a zombie", game.zombie(big(0),big(0))==a);
		assertTrue("Can add a zombie",game.zombie(big(1),big(1))==b);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertOnExisting(){
		//covers bad data and structural basis
		game.insert(a, big(0), big(0));
		game.insert(b, big(0), big(0));
		fail("Should have thrown error when trying to add a zombie where there already is one");
	}
	
	@Test(expected = NullPointerException.class)
	public void testInsertNull(){
		//covers bad data and structural basis
		game.insert(null, big(0), big(0));
		fail("Should have thrown error when inserting null");
	}
	
	//No boundaries can be tested in insert()
	
	@Test
	public void testDeleteNominal() {
		//covers structural basis and good data
		game.insert(a, big(0), big(0));
		assertTrue("Can delete zombie", game.delete(big(0),big(0))==a);
		assertTrue("Deleted zombie is gone", game.zombie(big(0),big(0))==null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNonExisting(){
		game.insert(a, big(0), big(0));
		game.delete(big(1), big(0));
		fail("Should have deleted non-existant zombie");
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
		assertTrue("Gets closest zombie to left", game.zombie(p1.getX(), p1.getY())==a);
		assertTrue("Gets closest zombie to right", game.zombie(p2.getX(), p2.getY())==c);
	}
	
	@Test
	public void testJavelinZombieInOneDirection() {
		//covers other part of structural basis and good data
		game.insert(a, big(0), big(0));
		game.insert(b, big(5), big(0));
		ZombiePoint p1 = game.javelin(big(-100));
		ZombiePoint p2 = game.javelin(big(100));
		assertTrue("Gets closest zombie to right in one direction", game.zombie(p1.getX(), p1.getY())==a);
		assertTrue("Gets closest zombie to left in one direction", game.zombie(p2.getX(), p2.getY())==b);
	}

	@Test
	public void testJavelinNoZombies() {
		//covers rest of structural basis and bad data
		assertTrue("with no zombies, returns null", game.javelin(big(0))==null);
	}
	
	@Test
	public void testJavelinBoundaries() {
		game.insert(a, big(0), big(0));
		game.insert(b, big(2), big(0));
		ZombiePoint p1 = game.javelin(big(0));
		assertTrue("On top of a zombie should return that zombie", game.zombie(p1.getX(), p1.getY())==a); 
		
		ZombiePoint p2 = game.javelin(big(1)); 
		assertTrue("Right in between zombies should return either zombie",
				game.zombie(p2.getX(), p2.getY())==a || game.zombie(p2.getX(), p2.getY())==b);
	}
	
	@Test(expected = NullPointerException.class)
	public void testJavelinNull(){
		//covers bad data and structural basis
		game.insert(a, big(0), big(0));
		game.javelin(null);
		fail("Should have thrown an error for a null arguement");
	}
	
	@Test
	public void testArrowNominal() {
		//covers good data and structural basis
		game.insert(a, big(0), big(0));
		game.insert(b, big(100), big(0));
		game.insert(c, big(50), big(0));
		ZombiePoint p1 = game.arrow("left");
		ZombiePoint p2 = game.arrow("right");
		assertTrue("Arrow finds furthest left", game.zombie(p1.getX(), p1.getY())==a);
		assertTrue("Arrow finds furthest right", game.zombie(p2.getX(), p2.getY())==b);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testArrowInvalidString() {
		//covers bad data and structural basis
		game.insert(a, big(0), big(0));
		game.insert(b, big(100), big(0));
		game.arrow("test");
		fail("Should throw an error for an invalid input");
	}
	
	@Test(expected = NullPointerException.class)
	public void testArrowNullString() {
		//covers bad data
		game.insert(a, big(0), big(0));
		game.insert(b, big(100), big(0));
		game.arrow(null);
		fail("Should throw an error for no input");
	}
	
	@Test
	public void testArrowBoundaries() {
		game.insert(a, big(0), big(0));
		game.insert(b, big(0), big(1));
		ZombiePoint p = game.arrow("left");
		assertTrue("Chooses one of the correct zombies",
				game.zombie(p.getX(), p.getY())==a || game.zombie(p.getX(), p.getY())==b);
	}
	
	@Test
	public void testBombNominalAscending() {
		//covers good data and structural basis
		game.insert(new Zombie("out of range"), big(0), big(100));
		game.insert(a, big(5), big(5));
		game.insert(b, big(6), big(6));
		game.insert(c, big(7), big(7));
		game.insert(new Zombie("out of range"), big(10), big(100));
		ZombiePoint p = game.bomb(big(6), big(1));
		assertTrue("Should choose c, the highest in range",game.zombie(p.getX(), p.getY())==c);
	}
	
	@Test
	public void testBombNominalDescending() {
		//covers good data and structural basis
		game.insert(new Zombie("out of range"), big(0), big(100));
		game.insert(a, big(5), big(7));
		game.insert(b, big(6), big(6));
		game.insert(c, big(7), big(5));
		game.insert(new Zombie("out of range"), big(10), big(100));
		ZombiePoint p = game.bomb(big(6), big(1));
		assertTrue("Should choose a, the highest in range",game.zombie(p.getX(), p.getY())==a);
	}
	
	@Test(expected = NullPointerException.class)
	public void testBombNullInputs(){
		//covers bad data
		game.bomb(null, null);
		fail("Should have thrown an error for null inputs");
	}
	
	@Test
	public void testBombEmptyRange(){
		//covers bad data
		ZombiePoint p1 = game.bomb(big(0), big(1));
		assertTrue("if there are no zombies, should return null",p1==null);
		game.insert(a, big(0), big(0));
		ZombiePoint p2 = game.bomb(big(0), big(0));
		assertTrue("if player is over a zombie, with range = 0, should return that zombie",
				game.zombie(p2.getX(), p2.getY())==a);
		
	}
	
	@Test
	public void testBombBoundaries() {
		//both sides of range should be inclusive boundaries
		game.insert(a, big(-5), big(1));
		game.insert(b, big(0), big(2));
		game.insert(c, big(5), big(1));
		ZombiePoint p1 = game.bomb(big(-10), big(10));
		ZombiePoint p2 = game.bomb(big(10), big(10));
		ZombiePoint p3 = game.bomb(big(-11), big(10));
		ZombiePoint p4 = game.bomb(big(11), big(10));
		assertTrue("Should include a zombie on the boundary of the range",
				game.zombie(p1.getX(), p1.getY())==b);
		assertTrue("Should include a zombie on the boundary of the range",
				game.zombie(p2.getX(), p2.getY())==b);
		assertTrue("Should not include a zombie outside of range",
				game.zombie(p3.getX(), p3.getY())==a);
		assertTrue("Should not include a zombie outside of range",
				game.zombie(p4.getX(), p4.getY())==c);
	}
	
	public BigInteger big(int i){
		return new BigInteger(""+i);
	}
}
