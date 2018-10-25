import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

/**
 * @author Jeremy Novak
 *
 */
public class ZombiePointTest {

	@Test
	public void testCompareToNominal() {
		//covers good data, structural basis and boundaries
		ZombiePoint a = new ZombiePoint(big(0), big(0));
		ZombiePoint b = new ZombiePoint(big(0), big(1));
		ZombiePoint c = new ZombiePoint(big(1), big(0));
		ZombiePoint d = new ZombiePoint(big(1), big(1));
		ZombiePoint e = new ZombiePoint(big(0), big(0));
		assertTrue("left < right regardless of height",
				b.compareTo(c)==-1 && c.compareTo(b)==1);
		assertTrue("if x is equal, compare heights",
				a.compareTo(b)==-1 && b.compareTo(a)==1);
		assertTrue("testing compound boundaries",
				a.compareTo(d)==-1 && d.compareTo(a)==1);
		assertTrue("if x and y are equal, returns 0",
				a.compareTo(e)==0 && e.compareTo(a)==0);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCompareToNull(){
		//covers bad data
		ZombiePoint a = new ZombiePoint(big(0), big(0));
		a.compareTo(null);
		fail();
	}

	@Test
	public void testCloserToNominal() {
		//covers good data, structural basis and boundaries
		ZombiePoint a = new ZombiePoint(big(0), big(0));
		ZombiePoint b = new ZombiePoint(big(100), big(0));
		assertTrue("closerTo works when the closer point is less",
				ZombiePoint.closerTo(a, b, big(25))==a);
		assertTrue("closerTo works when the closer point is greater",
				ZombiePoint.closerTo(a, b, big(75))==b);
	}
	
	@Test
	public void testCloserToBoundaries() {
		//covers good data and compound boundaries
		ZombiePoint a = new ZombiePoint(big(0), big(0));
		ZombiePoint b = new ZombiePoint(big(100), big(0));
		assertTrue("closerTo returns one of the correct options when they are equidistant",
				ZombiePoint.closerTo(a, b, big(50))==a ||ZombiePoint.closerTo(a, b, big(50))==b );
	}
	
	@Test(expected = NullPointerException.class)
	public void testCloserToNullArguements() {
		//covers bad data
		ZombiePoint a = new ZombiePoint(big(0), big(0));
		ZombiePoint b = new ZombiePoint(big(100), big(0));
		ZombiePoint.closerTo(a, b, null);
		fail("should have thrown an error for the null arguement");
	}

	@Test
	public void testHigherNominal() {
		//covers good data, structural basis and boundaries
		ZombiePoint a = new ZombiePoint(big(0), big(0));
		ZombiePoint b = new ZombiePoint(big(0), big(1));
		assertTrue("returns true when the higher point is first",
				ZombiePoint.higher(b,a));
		ZombiePoint c = new ZombiePoint(big(1), big(0));
		assertFalse("returns false if they are equal in height",
				ZombiePoint.higher(a,c));
	}	
	
	@Test(expected = NullPointerException.class)
	public void testHigherNullArguements() {
		//covers bad data
		ZombiePoint a = new ZombiePoint(big(0), big(0));
		ZombiePoint.higher(a,null);
		fail("should have thrown an error for the null arguement");
	}
	
	public BigInteger big(int i){
		return new BigInteger(""+i);
	}

}
