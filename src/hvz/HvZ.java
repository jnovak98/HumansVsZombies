import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class HvZ {
	private TreeMap<ZombiePoint, Zombie> zombies;
	
	public HvZ() {
		zombies = new TreeMap<ZombiePoint, Zombie>();
	}
	
	/* Zombie(x,y)  returns  the  zombie  at  location  (x, y)
	 * on  the  wall or null if there is no such zombie.
	 */
	public Zombie zombie(BigInteger x, BigInteger y){
		//Call search on red-black tree for a key of (x, y) and return value.
		//if no value is found then return null
		return zombies.get(new ZombiePoint(x, y)); //get returns null if no value is found
	}
	
	/*Insert(z, x, y) inserts zombie z at location (x, y) on the wall.  
	 * It returns an error if a zombie is already present at (x, y) or if z is null.
	 */
	public void insert(Zombie z, BigInteger x, BigInteger y){
		Objects.requireNonNull(z);		
		ZombiePoint location = new ZombiePoint(x, y);
		//if Zombie(x, y) doesn't equal null,return an error
		if(zombies.get(location)!=null)
			throw new IllegalArgumentException("There is already a zombie at the inputted values.");
		//else Call put on red-black tree for a key of (x, y) and a value of z
		else
			zombies.put(location, z);			
	}
	
	/*Delete(x, y) deletes and returns the zombie at (x, y).  
	 * It returns an error if there is no zombie at that location.
	 */
	public Zombie delete(BigInteger x, BigInteger y){
		ZombiePoint location = new ZombiePoint(x, y);
		//Z<-Zombie(x, y)
		Zombie toRemove = zombies.get(location);
		//if z=null, return an error
		if(toRemove==null)
			throw new IllegalArgumentException("There is no zombie present at the inputted values ");
		//else
		else
			//Call remove on red-black tree for a key of (x, y)
			zombies.remove(location);
			//return Z
			return toRemove;
	} 
	/*Javelin(xP) returns the (x, y) coordinates of the zombie that would be killed by the javelin, 
	 * or null if there is no zombie.The argument represents the player's position.  The javelin 
	 * kills the zombie whose horizontal position is closest to that of the player, irrespective of height.
	 */
	public ZombiePoint javelin(BigInteger playerX){
		//left<-get the key of a floor function on value (xP)
		ZombiePoint less = zombies.floorKey(new ZombiePoint(playerX, BigInteger.ZERO));
		//right<-get the key of a ceiling function on value (xP)
		ZombiePoint greater = zombies.ceilingKey(new ZombiePoint(playerX, BigInteger.ZERO));
		//if both left and right are null then return null.
		if(less == null && greater == null)
			return null;
		//if one out of left and right is null then return the other
		if(less == null)
			return greater;
		if (greater == null)
			return less;
		//return the closer of left and right
		return ZombiePoint.closerTo(less, greater, playerX);
	}
	
	/*Arrow(direction) returns the (x, y) coordinate of the zombie that would be killed by the arrow.  
	 * The direction is either left or right.  It returns null if there is no zombie.  The arrow kills 
	 * the zombie that is furthest to the left (or the right, at the player's choosing), irrespective of his height.
	 */
	public ZombiePoint arrow(String direction){
		//if direction = left then return furthest left key in tree
		if(direction.toLowerCase().equals("left"))
			return zombies.firstKey();
		//else return furthest right key
		else 
			return zombies.lastKey();
	}
	/*Bomb(xP, r) returns the (x, y) coordinate of the zombie that would be killed by a bomb of 
	 * range r launched by a player at coordinate xP.  It returns null if there is no zombie in range.  
	 * The bomb kills the zombie that is the highest on the wall among all of the zombies within a given horizontal 
	 * distance r from the player's position.  In other words, if the player horizontal position is xP, the 
	 * bomb kills the highest zombie whose horizontal coordinate is between xP-r and xP+r.
	 */
	public ZombiePoint bomb(BigInteger playerX, BigInteger range){
		//call subtree function to get all keys greater than xP-r and less than xP+r in the tree.
		//Convert subtree to a set S than can be iterated through
		Set<ZombiePoint> zombiesInRange = zombies.subMap(new ZombiePoint(playerX.subtract(range),BigInteger.ZERO), 
				new ZombiePoint(playerX.add(range),BigInteger.ZERO)).keySet();
		//H<-null
		ZombiePoint highest = null;
		//for each key K in S
		for(ZombiePoint z: zombiesInRange){
			//if Ky> Hy then H<-K
			if(highest == null || ZombiePoint.higher(z, highest))
				highest = z;
		}
		//return H
		return highest;
	}
}
