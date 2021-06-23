package logic;

import java.util.ArrayList;
import gameElements.*;
import interfaces.IAttack;

public class Board {

	private Game game;	
	private ArrayList<GameElement> listElem;	
	private int width, height;


	public Board(Game game, int width, int height) {

		this.game = game;
		this.listElem = new ArrayList<GameElement>();		
		this.width = width;
		this.height = height;


	}
	
	public Board(Game game, int width, int height, ArrayList<GameElement> listElement) {

		this.game = game;
		this.listElem = listElement;		
		this.width = width;
		this.height = height;
	}

	/*DOES AN UPDATE OF ALL THE ELEMENTS ON THE BOARD*/
	public void updateElements() {
		for(int i = 0; i < this.listElem.size(); i++) {
			this.listElem.get(i).update();
		}
	}

	/*CHECKS IF ANY OBJECT IN THE BOARD NEEDS TO DECREAS HP*/
	public void Explosion(int x, int y, int damage) {
		int i = 0;
		boolean found = false;

		while(i < this.listElem.size() && !found) {

			if((x == this.listElem.get(i).getX()) && (y == this.listElem.get(i).getY())) {
				found = true;
			}
			else 
				i++;
		}

		if(found) {
			this.listElem.get(i).decreaseHealth(damage);
		}
	}


	public void deleteObjectAt(int x, int y) {
		int i = 0;
		boolean found = false;

		while(i < this.listElem.size() && !found) {

			if((x == this.listElem.get(i).getX()) && (y == this.listElem.get(i).getY())) {
				found = true;
			}
			else 
				i++;
		}

		this.listElem.remove(i);
	}

	public void GarlicPush() {
		for(int i = 0; i < this.listElem.size(); i++) {	
			this.listElem.get(i).garlicPush();
		}	
	}

	public boolean waterthrow(int x, int y) {
		
		boolean ok = false;
		for(int i = 0; i < this.listElem.size(); i++) {		
			if((x == this.listElem.get(i).getX() && y == this.listElem.get(i).getY())) {
				if(this.listElem.get(i).water()) {
					//this.listElem.remove(i);
					this.listElem.get(i).decreaseHealth(1);
					return ok = true;
				}
			}
			
			else 
				return ok = false;
		}
		
		return ok;

	}

	public void flashlightexecuted() {		
		for(int i = 0; i < this.listElem.size(); i++) {	
			if(this.listElem.get(i).flashLight()) {//checks if the element is affected by the flashlight				
				this.listElem.remove(i);				
			}
		}

	}


	public void addGameElement(GameElement elem) {
		this.listElem.add(elem);		

	}

	public IAttack getObjectPosition(int x, int y) {
		int i=0;
		IAttack elemToAttack = null;
		GameElement elem = null;
		boolean found= false;

		while(!found && i<this.listElem.size()) {
			elem = this.listElem.get(i);
			if(x == elem.getX() && y == elem.getY()) {
				found = true;
				elemToAttack = elem;
			}
			i++;
		}	


		return elemToAttack;

	}


	public String objectToString(int i) {
		return this.listElem.get(i).toString(); 
	}



	/*GETTERS*/

	public int getObjectCount() {
		return this.listElem.size(); //Elements left on list
	}

	public int getObjectX(int i) {
		return this.listElem.get(i).getX();
	}

	public int getObjectY(int i) {

		return this.listElem.get(i).getY();

	}	

	public String getSaveGame(int i) {		
		return this.listElem.get(i).SaveGame();
	}

	public String elementsToString(String str) {

		for (int i  = 0; i < this.getObjectCount(); i++) {

			str += this.getSaveGame(i);
			str += "\n";

		}

		return str;
	}


}
