package gameElements;

import java.util.Random;

import interfaces.IAttack;
import logic.Game;

public class ExplosiveVampire extends Vampire {	

	public ExplosiveVampire(Game game, int x, int y) {

		super(game,x,y);
		this.health = 5;
		this.damage = 1;
		this.file_repr = "EV";
		this.speed = 2;					
		this.cycles = game.getCurrentCycle();		
		this.vampires_left = game.getLevel().getNumberOfVampires();		
		this.rand = new Random(game.getSeed());		
		this.freq = game.getLevel().getVampireFrequency();
	}	

	public ExplosiveVampire() {
	}

	public void update() {

		boolean move = true;
		boolean empty = true;
		int i = 0;

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
			elem.receiveVampireAttack(this.damage);
			
			
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

	/*CHECKS Explosive vampire health*/
	public void decreaseHealth (int damage){
		this.health -= damage;

		if(!this.isObjectAlive()) {			
				if(this.game.isFlashlight()) {
					
				}
				else{
					this.game.setVampiresOnBoard(game.getVampiresOnBoard()-1);
					game.delObject(getX(), getY());
					
				}					

			if(!this.game.isFlashlight()) {	//only explode if there is no flashlight		
				//EXPLOSION CREATED
				game.attackPositions(getX()-1, getY(), this.damage);
				game.attackPositions(getX()-1, getY()-1, this.damage);
				game.attackPositions(getX()-1, getY()+1, this.damage);
				game.attackPositions(getX()+1, getY(), this.damage);
				game.attackPositions(getX()+1, getY()-1, this.damage);
				game.attackPositions(getX()+1, getY()+1, this.damage);
				game.attackPositions(getX(), getY()+1, this.damage);
				game.attackPositions(getX(), getY()-1, this.damage);
			}
		}
	}	

	
	public boolean garlicPush() {		
			
		if(getX() == this.game.getLevel().getDim_x() - 1) {
			this.game.setVampiresOnBoard(1);
			this.game.delObject(getX(), getY()); //eliminate Vampire;
			return true;
		}
		else {
			int pos = getX();
			pos++;
			setX(pos);
		}
		
		return false;
		
	}
	
	public boolean water() {
		this.game.setVampiresOnBoard(game.getVampiresOnBoard()-1);
		return true;
	}


	/*GRAPHIC REPRESENTATION*/
	public String toString (){
		String a;
		a = "V*V" + "[" + this.health + "]";
		return a;
	}

	
	/*GETTERS & SETTERS*/
	


	@Override
	public String SaveGame() {
		String str = "";
		str += this.file_repr + "; " + getX() + "; " + getY() + "; " + this.health + "; " + this.cycles;
		return str;
	}

}
