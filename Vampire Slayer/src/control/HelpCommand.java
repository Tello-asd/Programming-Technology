package control;

import logic.Game;

public class HelpCommand extends NoParamsCommand{
	public static final String shortName = "h";
	public static final String name = "help";
	public static final String helpText = "Displays this help message.";
	public static final String commandText = "[h]elp";

	public HelpCommand() {
		
		super(HelpCommand.shortName, HelpCommand.name, HelpCommand.commandText, HelpCommand.helpText);
		

	}

	public boolean execute(Game game) {			
		
		game.help();	
		return false;
		
	}

}
