package logic;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Creation.SlayerCreation;
import Creation.VampireCreation;
import control.*;
import exceptions.*;
import gameElements.*;
import interfaces.IAttack;
import interfaces.IPrintable;

public class Game implements IPrintable {

	private boolean flashlight, dracula;
	private Level level;
	private boolean isGameActive, isFinished, garlic, vampireWins;
	private Random rand;
	private Long seed;	
	private Player player;
	private Vampire manageVampire;
	private int cycles, vampire_counter;
	private Board board;
	private Controller ctrl;
	private GamePrinter printer;



	public Game(long seed, Level level) {

		this.seed = seed;
		this.rand = new Random();
		this.level = level;	



		this.ctrl = new Controller(this);
		this.manageVampire = new Vampire(this,this.level.getDim_x(),this.level.getDim_y());
		this.player = new Player();
		this.board = new Board(this, this.level.getDim_x(), this.level.getDim_y());

		this.cycles = 0;		
		this.vampire_counter = 0;

		this.isGameActive = true;
		this.isFinished = false;
		this.garlic = false;
		this.flashlight = false;
		this.dracula = false;
		this.vampireWins = false;


	}	

	public void update() {

		if(this.getCurrentCycle() >= 1) {			
			this.AddCoins(10);		
		}

		this.board.updateElements();

		this.setFlashlight(false);

		if(this.getCurrentCycle() != 0)
			this.manageVampire.AddVampire();		

		this.printer = new GamePrinter(this, this.level.getDim_x(), this.level.getDim_y());
		printer.encodeGame(this);

		this.endGame();
		this.cycles++;
	}

	public String endGame() {

		String str = "";

		if((this.getVampiresOnBoard() == 0) && (this.manageVampire.getVampires_left() == 0)) {
			this.isGameActive = false;
			this.isFinished = true;
			for(int i = 0; i < 2; i++) {
				System.out.println("\n");
			}

			str += ("GAME OVER!\n");
			str += ("PLAYER WINS");
		}

		else if (this.vampireWins) {
			this.isGameActive = false;
			this.isFinished = true;
			for(int i = 0; i < 2; i++) {
				System.out.println("\n");
			}
			str += ("GAME OVER!\n");
			str += ("VAMPIRES WIN");
		}

		return str;

	}


	/*ADD SLAYER TO THE GAME*/
	public boolean addSlayer(Slayer slayer) throws InvalidPositionException, NotEnoughCoinsException {
		boolean add = true;


		if (slayer != null) {

			if (add) {

				if ((slayer.getX() < 0 || slayer.getX() >= this.level.getDim_x()) || (slayer.getY() < 0 || slayer.getY() >= this.level.getDim_y())) {

					add = false;
					throw new InvalidPositionException("Please, choose a cell in the board range("+ "0" + " - " + (this.level.getDim_x()-1) + ")" + "(" + "0" + " - " + (this.level.getDim_y()-1) + ")");

				}

			}

			for (int i = 0; i < this.board.getObjectCount(); i++) {

				if ((this.board.getObjectX(i) == slayer.getX()) && (this.board.getObjectY(i) == slayer.getY())) {

					add = false;
					throw new InvalidPositionException("Please, choose an empty cell.\n");

				}

			}



			if (add) {

				if (this.player.getCoins() >= slayer.getCost()) {

					this.DecreaseCoins(slayer.getCost());
					this.board.addGameElement(slayer);

				}
				else {
					add = false;
					throw new NotEnoughCoinsException("Not enought coins.\n");

				}

			}		

		}

		return add;
	}

	/*ADD A VAMPIRE TO THE GAME*/
	public void AddVampire() {
		int y = this.rand.nextInt(this.level.getDim_y()-1); //IT CAN APPEAR IN ANY OF THE COLS
		int x = this.level.getDim_x()-1; // MUST APPEAR IN THE RIGHTMOST ROW
		boolean addVampire = true;
		Vampire vampire;
		int vampireID;



		for(int i=0; i<this.board.getObjectCount(); i++) {
			if((this.board.getObjectX(i) == x) && (this.board.getObjectY(i) == y)) {
				addVampire = false;
			}
		}	

		if(addVampire) {

			vampireID = (this.rand.nextInt(VampireCreation.getAvailableVampiresToAdd()));			
			vampire = VampireCreation.getVampire(vampireID, this, x, y);

			if(vampireID == 1 && !this.isDraculaOnBoard()) {

				this.board.addGameElement(vampire); //solo añade a dracula

			}

			else {

				vampireID = (this.rand.nextInt(VampireCreation.getAvailableVampiresToAdd()));			
				vampire = VampireCreation.getVampire(vampireID, this, x, y);

				while(vampireID == 1) { //Dracula
					vampireID = (this.rand.nextInt(VampireCreation.getAvailableVampiresToAdd()));			
					vampire = VampireCreation.getVampire(vampireID, this, x, y);
				}
				this.board.addGameElement(vampire);//explosivos y normales

			}



		}

	}


	/*ONLY FOR THE EXPLOSIONS*/
	public void attackPositions(int x, int y, int damage) {
		board.Explosion(x,y,damage);		
	}

	/*DELETS EITHER A SLAYER OR A VAMPIRE*/
	public void delObject(int x, int y) {
		this.board.deleteObjectAt(x, y);
	}



	/*COMMAND*/
	public boolean addVampireByCommand(Vampire vampire) throws DraculaHasArisenException, InvalidPositionException, NoMoreVampiresException {

		boolean addVampire = true;
		int y = vampire.getY(); 
		int x = vampire.getX(); 

		for(int i=0; i<this.board.getObjectCount(); i++) {
			if((this.board.getObjectX(i) == x) && (this.board.getObjectY(i) == y)) {
				addVampire = false;
				throw new InvalidPositionException("Choose an empty cell\n");
			}

		}

		if ((vampire.getX() < 0 || vampire.getX() >= this.level.getDim_x()) || (vampire.getY() < 0 || vampire.getY() >= this.level.getDim_y())) {
			addVampire = false;
			throw new InvalidPositionException("Please, choose a cell in the board range("+ "0" + " - " + (this.level.getDim_x()-1) + ")" + "(" + "0" + " - " + (this.level.getDim_y()-1) + ")");

		}		

		if(this.getVampiresRemaining() == 0) {
			addVampire = false;
			throw new NoMoreVampiresException("No Vampires avaliable");
		}

		if(addVampire) {
			if(this.isDraculaOnBoard()) {
				addVampire = false;
				throw new DraculaHasArisenException("Dracula already on the board\n");

			}

			else {
				this.board.addGameElement(vampire);

				int vampires = this.manageVampire.getVampires_left()-1;
				this.manageVampire.setVampires_left(vampires);

				int VonBoard = this.getVampiresOnBoard()+1;
				this.setVampiresOnBoard(VonBoard);

				addVampire = true;
			}
		}

		return addVampire;
	}

	public boolean addBloodBank(BloodBank blood) throws InvalidPositionException {

		boolean addBloodBank = true;
		int x= blood.getX();		

		if(x < this.getLevel().getDim_x()-1 && x >= 0) {
			this.board.addGameElement(blood);
			this.player.DeleteCoins(20);
		}
		else {
			addBloodBank = false;
			throw new InvalidPositionException("Invalid position\n");
		}


		return addBloodBank;
	}

	public void exitGame() {
		this.isFinished = true;
	}

	public void help() {		
		System.out.println(CommandGenerator.commandHelp());
	}	

	public void reset() {		
		this.cycles = 0;
		this.player = new Player();
		new Game(this.getSeed(), this.getLevel());
		this.board = new Board(this, this.level.getDim_x(), this.level.getDim_y());
		this.manageVampire = new Vampire(this, this.level.getDim_x(),this.level.getDim_y());		

	}

	public void noAction() {}

	public boolean executeFlashlight() throws NotEnoughCoinsException {	

		if(this.player.getCoins() >= this.player.getFlashlightCost()) {
			this.player.DeleteCoins(this.player.getFlashlightCost());
			this.flashlight = true;
			this.board.flashlightexecuted();

		}
		else {
			this.flashlight = false;
			throw new NotEnoughCoinsException("Not enought coins.\n");

		}

		return this.flashlight;
	}

	public boolean throwWater(String side) throws NotEnoughCoinsException {

		boolean found = false;


		if(this.player.getCoins() >= this.player.getWaterCost()) {
			this.player.DeleteCoins(this.player.getWaterCost());

			switch(side) {
			case "left":
				int x = 0;
				int y;

				for(y = 0; y <= this.level.getDim_y(); y++) {
					while (x <= this.level.getDim_x() && !found) {

						if(!this.board.waterthrow(x, y)) {
							x++;
						}

						else {
							found = true;
						}
					}
					found = false;
					x = 0;
				}
				
				break;
			}

			return true;
		}

		else 			
			return false;




	}


	public boolean garlicThrow() throws NotEnoughCoinsException {		


		if(this.player.getCoins() >= this.player.getGarlicCost()) {
			this.player.DeleteCoins(this.player.getGarlicCost());

			this.board.GarlicPush();			
			garlic = true;

		}
		else {
			garlic = false;
			throw new NotEnoughCoinsException("Not enought coins.\n");


		}

		return garlic;
	}

	/*STRINGIFY*/
	public String stringify(String str) {

		String elemstr = "";

		str += "Buffy The Vampire Slayer v3.0 \n";
		str += "cycle: " + this.cycles + "\n";
		str += "Coins: " + this.getCoins() + "\n";
		str += "level: " + this.level.getName() + "\n";
		str += "Remaining Vampires: " + this.getVampiresRemaining() + "\n";
		str += "Vampires on Board: " + this.getVampiresOnBoard() + "\n\n";
		str += "Game Object List: \n";

		str += this.board.elementsToString(elemstr);


		return str;
	}

	/*SAVE GAME*/	
	public boolean saveGame(String fileName) throws IOException {

		boolean saved = false;
		String str = "";
		String mystr = stringify(str);

		FileWriter fw = new FileWriter(fileName + ".dat");

		/*Clase que nos permite escribir texto en un Outputstream,
		 *  utilizando un buffer para proporcionar una escritura
		 *   eficiente de caracteres, arrays y strings.*/
		BufferedWriter bw = new BufferedWriter(fw);
		try {

			bw.write(mystr);

		} finally {

			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();

		}		

		System.out.print("Game successfully saved in file: " + fileName + "\n");

		return saved;
	}

	/*CHECK IF THE GAME IS ACTIVE AND IF THE GAME IS FINISHED*/
	public boolean isGameActive() {
		return isGameActive;
	}
	public void setGameActive(boolean isGameActive) {
		this.isGameActive = isGameActive;
	}


	public boolean isFinished() {		
		return this.isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}


	public long getSeed() {
		return this.seed;
	}


	/*ADD OR REMOVE COINS*/
	public void AddCoins(int coins) {
		this.player.AddCoinsToPlayer(coins);
	}

	public void AddBloodBankCoins(int percentage) {
		this.player.AddBloodBankCoins(percentage);

	}

	public void DecreaseCoins(int coins) {
		this.player.DeleteCoins(coins);
	}

	public void addSuperCoins() {
		this.player.AddCoinsToPlayer(this.player.getSuperCoin());

	}

	public int getCoins() {
		return this.player.getCoins();
	}

	public int getCost() {
		return Slayer.COST;

	}


	/*SLAYER*/
	/*VAMPIRE*/	
	/*CHECK OBJECT POSITION*/	
	public IAttack getObjectInPosition(int x, int y) {		
		return board.getObjectPosition(x, y); //return object in front
	}
	public String getObjectToString(int i) {
		return this.board.objectToString(i);
	}

	public int getVampiresOnBoard() {
		return this.vampire_counter;
	}

	public int setVampiresOnBoard(int value) {
		return this.vampire_counter = value;
	}

	public int getVampiresRemaining() {
		return this.manageVampire.getVampires_left();
	}



	/*GETTERS & SETTERS*/
	public int getCurrentCycle() {
		return this.cycles;
	}

	public int getObjectCount() {

		return this.board.getObjectCount();

	}

	public int getObjectX(int i) {

		return this.board.getObjectX(i);

	}

	public int getObjectY(int i) {

		return this.board.getObjectY(i);

	}	

	public boolean isDraculaOnBoard() {
		return dracula;
	}

	public void setDracula(boolean dracula) {
		this.dracula = dracula;
	}

	public boolean isGarlic() {
		return garlic;
	}

	public void setGarlic(boolean garlic) {
		this.garlic = garlic;
	}	

	public boolean isFlashlight() {
		return flashlight;
	}

	public void setFlashlight(boolean flashlight) {
		this.flashlight = flashlight;
	}	

	public boolean isVampireWins() {
		return vampireWins;
	}

	public void setVampireWins(boolean vampireWins) {
		this.vampireWins = vampireWins;
	}

	@Override
	public String getInfo() {
		StringBuilder info = new StringBuilder();

		if(this.getCurrentCycle() != 0)
			info.append("Seed : " + this.seed + "\n");
		info.append("Cycles: " + this.getCurrentCycle() + "\n");
		info.append("Coins: " + getCoins() + "\n");
		info.append("Remaining vampires: " + getVampiresRemaining() + "\n");
		info.append("Vampires on the board: " + getVampiresOnBoard() + "\n");

		if(this.isDraculaOnBoard()) {
			info.append("Dracula has risen");
			this.setDracula(false);
		}

		return info.toString();
	}




	/*LEVEL*/
	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}

	public void loadState(String fileName) throws Exception{
		FileReader fr = new FileReader(fileName + ".dat");
		BufferedReader br = new BufferedReader(fr);

		int newGameCycle = 0;
		int newGameSuncoins = 0;
		Level newGameLevel = null;
		int newGameRemZombies;

		ArrayList myList = new ArrayList<GameElement>();		

		int newGameObjectCount = 0;

		String str;
		String[] aux;

		try {

			str = br.readLine();
			str = br.readLine();

			aux = null;
			str = br.readLine();
			if (str != null) {

				aux =  str.trim().split("\\s+");
				if (aux[1].chars().allMatch(Character::isDigit)) {

					if (Integer.valueOf(aux[1]) >= 0)
						newGameCycle = Integer.valueOf(aux[1]);						
				} else {

					throw new NumberFormatException("[ERROR]: Cycle value in the file" + fileName + ".dat is corrupted.\n");

				}

			}				

			aux = null;
			str = br.readLine();
			if (str != null) {

				aux =  str.trim().split("\\s+");
				if (aux[1].chars().allMatch(Character::isDigit)) {

					newGameSuncoins = Integer.valueOf(aux[1]);

				} else {

					throw new NumberFormatException("[ERROR]: Suncoins value in the file " + fileName + ".dat is corrupted.\n");

				}

			} 

			aux = null;
			str = br.readLine();
			if (str != null) {

				aux =  str.trim().split("\\s+");
				newGameLevel = Level.fromParam(aux[1]);

			} 

			aux = null;
			str = br.readLine();
			if (str != null) {

				aux =  str.trim().split("\\s+");
				if (aux[1].chars().allMatch(Character::isDigit)) {

					newGameRemZombies = Integer.valueOf(aux[1]);

				} else {

					throw new NumberFormatException("[ERROR]: remZombies value in the file " + fileName + ".dat is corrupted.\n");

				}

			} 

			aux = null;
			str = br.readLine();
			if (str != null) {

				aux =  str.trim().split("\\s+");
				String[] objectInfo = null;
				for (int i = 1; i < aux.length; i++) {

					objectInfo = aux[i].trim().replaceAll(",", "").split(":");

					if (objectInfo[1].chars().allMatch(Character::isDigit) || (objectInfo[2].chars().allMatch(Character::isDigit)) || (objectInfo[3].chars().allMatch(Character::isDigit)) || (objectInfo[4].chars().allMatch(Character::isDigit))) {


						switch (objectInfo[0]) {

						case "S": 

							Object plant = SlayerCreation.loadPlant(objectInfo[0], Integer.valueOf(objectInfo[2]), Integer.valueOf(objectInfo[3]), Integer.valueOf(objectInfo[1]), Integer.valueOf(objectInfo[4]), this);
							if(plant != null) {
								plant = myList.get(i-1);
								newGameObjectCount++;	
							}

							break;

						case "V": case "D": case "EV":

							Object zombie = VampireCreation.loadZombie(objectInfo[0], Integer.valueOf(objectInfo[2]), Integer.valueOf(objectInfo[3]), Integer.valueOf(objectInfo[1]), Integer.valueOf(objectInfo[4]), this);
							if(zombie != null) {
								zombie = myList.get(i-1);
								newGameObjectCount++;	
							}

							break;


						}

					} else {

						throw new NumberFormatException("[ERROR]: objectList values in the file " + fileName + ".dat are corrupted (objectPosition is not an Integer).\n");

					}

				}

			}

		} finally {

			if (br != null)
				br.close();

			if (fr != null)
				fr.close();

		}

		this.cycles = newGameCycle;
		this.board = new Board(this, this.level.getDim_x(), this.level.getDim_y(), myList);

		//this.objectList.loadObjects(newGameObjectList, newGameObjectCount);

		this.AddCoins(newGameSuncoins);
		this.level = newGameLevel;

		//this.ZManager = new ZombieManager(this, level, this.seed, newGameRemZombies);
	}


























}
