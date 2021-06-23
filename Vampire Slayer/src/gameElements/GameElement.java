package gameElements;

import interfaces.IAttack;
import logic.Game;

public abstract class GameElement implements IAttack {
	
	public Game game;
	private int x, y;
	protected int health;
	protected String file_repr;
	protected int damage;
	
	
	public GameElement (Game game, int x, int y, int health, int damage, String file_repr) {
		
		this.game = game;
		this.x = x;
		this.y = y;
		this.health = health;
		this.file_repr = file_repr;
		this.damage = damage;
		
	}
	
	public GameElement() {
		
	}
	
	
	public abstract void update();

	public int getX() {
		return this.x;
	}
	
	public void setX(int i) {
		this.x = i;
	}

	public int getY() {
		return this.y;
	}
	
	public void setY(int i) {
		this.y = i;
	}
	
	public boolean isObjectAlive() {
		boolean live = false;
		
		if(this.health > 0) {
			live = true;
		}		
		
		return live;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public int getHealth() {
		return this.health;
	}

	public abstract void decreaseHealth(int damage); 
	
	public abstract String toString();

	public abstract String SaveGame();	

}
