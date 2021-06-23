package gameElements;

import interfaces.IAttack;
import logic.Game;
import logic.Level;

public class Slayer extends GameElement {

	public static int COST = 50;	
	private int shootFreq;		
	private int cycle;
	private int vampireX_pos;		




	public Slayer(Game game, int x, int y, int health, int damage, String type) {

		super(game,x,y,3,1,"S");			
		this.vampireX_pos = 0;			
		this.cycle = game.getCurrentCycle();


	}


	public Slayer() {
		// TODO Auto-generated constructor stub
	}


	/*CHECKS IF THERE IS A VAMPIRE IN THE SAME ROW AND SHOOTS*/
	public void update() {	
		
		if (this.game.getCurrentCycle() != this.cycle) {
			attack();			
		}
	}

	
	/*the bool to avoid attack all the elements in the row, just 
	 the first one with a true in the receiveSlayerAttack() */
	@Override
	public void attack() {		

		int x = getX()+1;
		int y = getY();
		boolean hasAttacked = false;
		
		while( x < this.game.getLevel().getDim_x() && !hasAttacked){ 
			IAttack elem = this.game.getObjectInPosition(x, y);

			if(elem != null) {
				if(elem.receiveSlayerAttack(this.damage))
					hasAttacked = true;
			}
			
			x++;
		}
	}

	public boolean receiveVampireAttack(int damage) {

		if(this.health >= 0) {
			decreaseHealth(damage);
			return true;
		}

		else 
			return false;

	}

	public boolean receiveDraculaAttack() {
		this.health = 0;
		game.delObject(getX(), getY());
		return true;
	}


	/*CHECKS SLAYER health*/
	public void decreaseHealth (int damage){
		this.health -= damage;		

		if (!isObjectAlive())
			game.delObject(getX(), getY());

	}

	/*GRAPHIC REPRESENTATION*/
	public String toString (){
		String a;
		a = "<->" + "[" + this.health + "]";
		return a;
	}	

	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getShootFreq() {
		return shootFreq;
	}
	public void setShootFreq(int shootFreq) {
		this.shootFreq = shootFreq;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}	


	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}


	public int getCost() {
		return Slayer.COST;
	}



	@Override
	public String SaveGame() {
		String str = "";
		str += this.file_repr + "; " + getX() + "; " + getY() + "; " + this.health;
		return str;
	}

}
