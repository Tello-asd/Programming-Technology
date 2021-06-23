package gameElements;

import java.util.Random;

import interfaces.IAttack;
import logic.Game;

public class Dracula extends Vampire{	

	private static boolean imHere = true;

	public Dracula(Game game, int x, int y) {

		super(game,x,y);
		this.health = 5;
		this.speed = 2;				
		this.cycles = game.getCurrentCycle();		
		this.vampires_left = game.getLevel().getNumberOfVampires();		
		this.rand = new Random(game.getSeed());		
		this.freq = game.getLevel().getVampireFrequency();
		this.file_repr = "D";		
		
	}
	
	public Dracula() {
		
	}

	public void update() {

		boolean move = true;
		boolean empty = true;
		int i = 0;
		
		game.setDracula(imHere);
		
		while (i < game.getObjectCount() && empty) {

			if(this.game.isGarlic()) {
				move = false;
				this.game.setGarlic(false);
			}			

			if (((getX() - 1) == game.getObjectX(i)) && ((getY()) == game.getObjectY(i))) {

				empty = false;				
				attack();	
				move = false;

			}

			++i;

		}

		if (empty) {

			if ((move) && ((game.getCurrentCycle() - this.cycles) % this.speed ) == 0){

				this.move();

			}		

		}

	}

	@Override
	public void attack() {
		IAttack elem = this.game.getObjectInPosition(getX()-1, getY());
		if(elem != null) {
			elem.receiveDraculaAttack();		
			
		}
	}	

	
	public boolean receiveSlayerAttack(int damage) {

		if(this.health >= 0) {
			decreaseHealth(damage);
			return true;
		}

		else 
			return false;

	}

	/*CHECKS VAMPIRE health*/
	public void decreaseHealth (int damage){
		this.health -= damage;

		if(!this.isObjectAlive()) {
			this.game.setVampiresOnBoard(game.getVampiresOnBoard()-1);
			game.delObject(getX(), getY());
			
		}
	}	
		
	public boolean flashLight() {
		return false;
	}
	
	public boolean water() {
		this.game.setVampiresOnBoard(game.getVampiresOnBoard()-1);
		return true;
	}
	
	public boolean garlicPush() {		
				
		if(getX() == this.game.getLevel().getDim_x() - 1) {
			
			this.game.setVampiresOnBoard(game.getVampiresOnBoard()-1);
			this.game.delObject(getX(), getY()); //eliminate Vampire;
			
		}
		else {
			int pos = getX();
			pos++;
			setX(pos);
		}
		
		return true;
		
	}


	/*GRAPHIC REPRESENTATION*/
	public String toString (){
		String a;
		a = "V-V";
		return a;
	}



	/*GETTER Y SETTER*/	

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	
	
	@Override
	public String SaveGame() {
		String str = "";
		str += this.file_repr + "; " + getX() + "; " + getY() + "; " + this.health + "; " + this.cycles;
		return str;
	}
	

}
