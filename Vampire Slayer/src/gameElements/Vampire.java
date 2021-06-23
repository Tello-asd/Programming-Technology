package gameElements;
import java.util.Random;

import interfaces.IAttack;
import logic.Game;

public class Vampire extends GameElement {

	
	protected Random rand;	
	protected int vampires_left;	
	protected double freq;	
	protected int cycles;
	protected int speed;


	public Vampire(Game game, int x, int y) {			

		super(game,x,y,5,1,"V");
		this.speed = 2;				
		this.cycles = game.getCurrentCycle();		
		this.vampires_left = game.getLevel().getNumberOfVampires();			
		this.rand = new Random(game.getSeed());		
		this.freq = game.getLevel().getVampireFrequency();
		
	}

	public Vampire() {
		
	}

	public void update() {

		boolean move = true;
		boolean empty = true;
		int i = 0;

		while (i < game.getObjectCount() && empty) {

			if(this.game.isGarlic()) {
				move = false;				//they cannot move 1 turn
				this.game.setGarlic(false);
			}

			if (((getX() - 1) == game.getObjectX(i)) && ((getY()) == game.getObjectY(i))) {

				empty = false;
				move = false;
				attack();	


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

	public void AddVampire() {
		
		
		if((this.rand.nextDouble() < this.freq) && (this.vampires_left > 0)) {
			this.game.AddVampire();
			this.vampires_left--;			
			this.game.setVampiresOnBoard(game.getVampiresOnBoard()+1);
		}
	}

	public void move() {
		int pos = getX();
		pos--;
		setX(pos);
		
		if(getX() == 0) {
			this.game.setVampireWins(true);
		}
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
		this.game.setVampiresOnBoard(game.getVampiresOnBoard()-1);
		return true;
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
		a = "V^V" + "[" + this.health + "]";
		return a;
	}



	/*GETTERS & SETTERS*/		

	public double getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCycles() {
		return cycles;
	}


	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	public int getVampires_left() {
		return vampires_left;
	}


	public void setVampires_left(int vampires_left) {
		this.vampires_left = vampires_left;
	}	

	@Override
	public String SaveGame() {
		String str = "";
		str += this.file_repr + "; " + getX() + "; " + getY() + "; " + this.health + "; " + this.cycles;
		return str;
		
	}




}
