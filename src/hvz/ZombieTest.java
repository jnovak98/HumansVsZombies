import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.math.BigInteger;

import org.junit.Test;

public class ZombieTest {

	@Test
	public void testZombieInsertDelete() {
		Zombie a = new Zombie("a");
		Zombie b = new Zombie("b");
		HvZ game = new HvZ();
		game.insert(a, new BigInteger("54"), new BigInteger("126"));
		game.insert(b, new BigInteger("55"), new BigInteger("123"));
		assertTrue(game.zombie(new BigInteger("1"), new BigInteger("12")) == null);
		assertTrue(game.zombie(new BigInteger("54"), new BigInteger("126")).equals(a));
		assertTrue(game.zombie(new BigInteger("55"), new BigInteger("123")).equals(b));
		
		assertTrue(game.delete(new BigInteger("54"), new BigInteger("126")).equals(a));
		assertTrue(game.zombie(new BigInteger("54"), new BigInteger("126"))==null);
		assertTrue(game.delete(new BigInteger("55"), new BigInteger("123")).equals(b));
		assertTrue(game.zombie(new BigInteger("55"), new BigInteger("123"))==null);
	}
	
	@Test
	public void testJavelin(){
		Zombie a = new Zombie("a");
		Zombie b = new Zombie("b");
		HvZ game = new HvZ();
		game.insert(a, new BigInteger("23"), new BigInteger("34"));
		game.insert(b,new BigInteger("75"), new BigInteger("111"));
		ZombiePoint pointA = game.javelin(new BigInteger("40"));
		ZombiePoint pointB = game.javelin(new BigInteger("50"));
		assertTrue(game.zombie(pointA.getX(),pointA.getY()).equals(a));
		assertTrue(game.zombie(pointB.getX(),pointB.getY()).equals(b));
	}
	
	@Test
	public void testArrow(){
		Zombie a = new Zombie("a");
		Zombie b = new Zombie("b");
		HvZ game = new HvZ();
		game.insert(a, new BigInteger("20"), new BigInteger("34"));
		game.insert(b, new BigInteger("28"), new BigInteger("34"));
		ZombiePoint pointA = game.arrow("left");
		ZombiePoint pointB = game.arrow("right");
		assertTrue(game.zombie(pointA.getX(),pointA.getY()).equals(a));
		assertTrue(game.zombie(pointB.getX(),pointB.getY()).equals(b));
	}
	
	@Test
	public void testBomb(){
		Zombie a = new Zombie("a");
		Zombie b = new Zombie("b");
		Zombie c = new Zombie("c");
		HvZ game = new HvZ();
		game.insert(a, new BigInteger("34"), new BigInteger("100"));
		game.insert(b, new BigInteger("38"), new BigInteger("90"));
		game.insert(c, new BigInteger("36"), new BigInteger("80"));
		ZombiePoint point = game.bomb(new BigInteger("37"), new BigInteger("2"));
		assertTrue(game.zombie(point.getX(),point.getY()).equals(b));
	}

}
