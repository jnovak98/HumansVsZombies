
public class Zombie {
	private String name;
	
	public Zombie(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean equals(Zombie z){
		return this.name.equals(z.getName());
	}
}
