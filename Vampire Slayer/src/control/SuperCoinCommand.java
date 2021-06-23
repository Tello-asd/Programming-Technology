package control;

import logic.Game;

public class SuperCoinCommand extends NoParamsCommand{
	
	public static final String symbol = "c";
	public static final String name = "coins";
	public static final String help = "Adds 1000 coins to the player";
	public static final String commandText = "[c]oins";

	public SuperCoinCommand() {
		super(SuperCoinCommand.symbol, SuperCoinCommand.name, SuperCoinCommand.commandText, SuperCoinCommand.help);
		
	}

	@Override
	public boolean execute(Game game) {
		
		game.addSuperCoins();
		return true;
	}	

}
