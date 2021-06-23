package Creation;

import gameElements.Slayer;
import logic.Game;

public class SlayerCreation {

	private static Slayer[] avaliableSlayers = {
			new Slayer()


			// Add here any new plant
	};

	public static Slayer loadPlant(String plantName, int x, int y, int hp, int damage, Game game) {

		Slayer plant = null;

		switch (plantName.toLowerCase()) {

		case "slayer": case "s": plant = new Slayer(game, x, y, hp, damage, plantName); break;
		
		}

		return plant;

	}

	public static String listAvaliablePlants() {

		String text = "Avaliable plants: \n";
		int i = 0;

		do {

			text += avaliableSlayers[i].SaveGame();
			++i;

		} while (i < avaliableSlayers.length);

		return text;

	}
	
	public static int getavaliableSlayersToAdd() {
		return avaliableSlayers.length;
	}

}
