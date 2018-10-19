import java.math.BigInteger;

class ZombiePoint implements Comparable<ZombiePoint>{
	private BigInteger x;
	private BigInteger y;
	
	public ZombiePoint(BigInteger x, BigInteger y){
		this.x = x;
		this.y = y;
	}

	
	BigInteger getX() {
		return x;
	}


	BigInteger getY() {
		return y;
	}

	@Override
	public int compareTo(ZombiePoint o) {
		int compareX = this.x.compareTo(o.getX());
		int compareY = this.y.compareTo(o.getY());
		//if Ax = Bx then return whichever has the higher y value
		//else return which has the higher x value
		return compareX==0 ? compareY : compareX;
	}
	
	//helper function which returns the ZombiePoint closer to the inputed x-value
	static ZombiePoint closerTo(ZombiePoint a, ZombiePoint b, BigInteger x){
		int closerValue = x.subtract(a.getX()).abs().compareTo(x.subtract(b.getX()).abs());
		return (closerValue < 0) ? a : b;
	}
	
	//helper function which returns whether the first ZombiePoint is bigger than the second
	static boolean higher(ZombiePoint a, ZombiePoint b){
		return (a.getY().compareTo(b.getY()) > 0);
	}
	
}
