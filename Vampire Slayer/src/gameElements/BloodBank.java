package gameElements;

import logic.Game;

public class BloodBank extends GameElement {

	
	
	private int percentCost;	
	
	public BloodBank(Game game, int x, int y, int percentCost) {
		
		super(game,x,y,3,0,"B");
		
		this.game = game;			
		this.percentCost = percentCost;	
		
	}	
	
	@Override
	public void update() {
		attack();		
	}	
	
	@Override
	public void attack() {
		double percentage = this.percentCost*0.1;
		this.game.AddBloodBankCoins((int) percentage);				
	}
	
	public boolean receivevampireAttack(int damage) {
		this.health = 0;
		decreaseHealth(damage);
		return true;
		
	}
	public boolean receiveDraculaAttack() {
		this.health = 0;
		game.delObject(getX(), getY());
		return true;
	}
	
	@Override
	public void decreaseHealth(int damage) {
		this.game.delObject(getX(), getY());		
	}
	
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	

	@Override
	public String toString() {
		String a;
		a = "B-B" + "[" + this.percentCost + "]";
		return a;
	}

	@Override
	public String SaveGame() {
		String str = "";
		str += this.file_repr + "; " + getX() + "; " + getY() + "; " + this.health + "; " + this.percentCost;
		return str;
	}

}
