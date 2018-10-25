import java.math.BigInteger;
import java.util.Objects;

public class ZombiePoint implements Comparable<ZombiePoint>{
	private BigInteger x;
	private BigInteger y;
	
	public ZombiePoint(BigInteger x, BigInteger y){
		Objects.requireNonNull(x);
		Objects.requireNonNull(y);
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x coordinate of the point
	 */
	public final BigInteger getX() {
		return x;
	}

	/**
	 * @return the y coordinate of the point
	 */
	public final BigInteger getY() {
		return y;
	}

	@Override
	public int compareTo(ZombiePoint o) {
		Objects.requireNonNull(o);
		int compareX = this.x.compareTo(o.getX());
		int compareY = this.y.compareTo(o.getY());
		//if Ax = Bx then return whichever has the higher y value
		//else return which has the higher x value
		return compareX==0 ? compareY : compareX;
	}
	
	//helper function which returns the ZombiePoint closer to the inputed x-value
	static ZombiePoint closerTo(ZombiePoint a, ZombiePoint b, BigInteger x){
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		Objects.requireNonNull(x);
		int closerValue = x.subtract(a.getX()).abs().compareTo(x.subtract(b.getX()).abs());
		return (closerValue < 0) ? a : b;
	}
	
	public static ZombiePoint testCloserTo(ZombiePoint a, ZombiePoint b, BigInteger x){
		return closerTo(a,b,x);
	}
	
	//helper function which returns whether the first ZombiePoint is bigger than the second
	static boolean higher(ZombiePoint a, ZombiePoint b){
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		return (a.getY().compareTo(b.getY()) > 0);
	}
	
	public static boolean testHigher(ZombiePoint a, ZombiePoint b){
		return higher(a,b);
	}
}
