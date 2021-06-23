package control;

import exceptions.CommandParseException;

public class CommandGenerator {
	
private static Command[] avaliableCommands = {
			
		new AddCommand(),
		new HelpCommand(),
		new ResetCommand(),
		new ExitCommand(),
		new UpdateCommand(),
		new SuperCoinCommand(),
		new FlashLightCommand(),
		new GarlicCommand(),
		new AddvampireCommand(),
		new AddBloodBank(),
		new SaveCommand(),
		new StringifyCommand(),
		new WaterCommand(),
		new LoadCommand()
		
		};
	
	/** Receives the user input and creates the new command by using the function parse on each of them
	 * 	returns "NULL" if there wasn't any corresponding command to the input
	 * @throws CommandParseException 
	 *  */
	
	public static Command parseCommand (String[] commandWords, Controller controller) throws CommandParseException {
		
		Command command;
		int i = 0;
		
		do {
			
			command = avaliableCommands[i].parse(commandWords, controller);
			++i;
			
		} while (i < avaliableCommands.length && command == null);
		
		return command;
		
	}
	
	/** Appends all the helpText of the commands to be printed by HelpCommand */
	
	public static String commandHelp() {
		
		String text = "Avaliable commands: \n";
		int i = 0;
		
		do {
			
			text += avaliableCommands[i].helpText();
			++i;
			
		} while (i < avaliableCommands.length);
		
		return text;
		
	}

}
