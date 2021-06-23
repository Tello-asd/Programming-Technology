package Creation;

import gameElements.*;
import logic.Game;

public class VampireCreation {


	private static GameElement[] availableVampiresToAdd = {
			new Vampire(),
			new Dracula(),
			new ExplosiveVampire()
	};

	public static Vampire getVampire(int vampireID, Game game, int x, int y) {
		Vampire vampire = null;

		switch(vampireID) {

		case 0:
			vampire = new Vampire(game,x,y);
			break;

		case 1:
			vampire = new Dracula(game,x,y);
			break;

		case 2:
			vampire = new ExplosiveVampire(game,x,y);
			break;	

		}

		return vampire;


	}
/**//////////////////////////////////////
	public static Vampire loadZombie(String zombieSymbol, int x, int y, int hp, int freq, Game game) {

		Vampire zombie = null;

		switch (zombieSymbol.toUpperCase()) {

		case "V": zombie = new Vampire(game,x, y); break;
		case "D": zombie = new Dracula(game, x, y); break;
		case "EV": zombie = new ExplosiveVampire(game, x, y); break;

		}

		return zombie;

	}

	public static Vampire getVampireByType(String vampireID, Game game, int x, int y) {
		Vampire vampire = null;

		switch(vampireID) {	


		case "D":case "d":
			vampire = new Dracula(game,x,y);
			break;

		case "EV": case "e":
			vampire = new ExplosiveVampire(game,x,y);
			break;

		default:
			vampire = new Vampire(game,x,y);
			break;
		}

		return vampire;


	}

	//in order to decide which vampire to add, we use a nextInt in game, but we need to define the length in order to obtain a correct value (1-3)
	public static int getAvailableVampiresToAdd() {
		return availableVampiresToAdd.length;
	}

}
