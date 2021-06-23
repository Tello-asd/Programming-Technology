package gameElements;

import java.util.Random;

public class Player {
	
	private int coins = 50;
	private int superCoin = 1000;
	private int flashlightCost = 50;
	private int garlicCost = 10;
	private int waterCost = 20;
	
	public Random rand;
	
	public Player() {		
		this.rand = new Random();
	}
	
	
	public void AddCoinsToPlayer(int coins) {
		
		if(coins == this.superCoin) {
			this.coins += coins;
		}
		
		else if(rand.nextBoolean()) 
			this.coins += coins;
	}
	
	public void AddBloodBankCoins(int percentage) {
		this.coins+= percentage;		
	}	
	
	public void DeleteCoins(int coins) {
		this.coins -= coins;
	}
	
	
	
	
	/*GETTERS & SETTERS*/
	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}


	public int getSuperCoin() {
		return superCoin;
	}


	public  void setSuperCoin(int superCoin) {
		this.superCoin = superCoin;
	}


	public int getFlashlightCost() {
		return flashlightCost;
	}


	public void setFlashlightCost(int flashlightCost) {
		this.flashlightCost = flashlightCost;
	}


	public int getGarlicCost() {
		return garlicCost;
	}


	public void setGarlicCost(int garlicCost) {
		this.garlicCost = garlicCost;
	}


	public int getWaterCost() {
		// TODO Auto-generated method stub
		return waterCost;
	}


	
	
	
	
	
	
	

}
