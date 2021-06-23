package control;

import exceptions.*;
import logic.Game;

public class FlashLightCommand extends NoParamsCommand {

	public static final String symbol = "l";
	public static final String name = "light";
	public static final String help = "Kills all vampires except Dracula. Cost 50 coins";
	public static final String commandText = "[l]ight";	

	public FlashLightCommand() {
		super(FlashLightCommand.symbol, FlashLightCommand.name, FlashLightCommand.commandText, FlashLightCommand.help);
		
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {	

		try {
			return game.executeFlashlight();

		} catch(NotEnoughCoinsException ex) {
			throw new CommandExecuteException("[ERROR]:" + ex.getMessage());
		}

	}

}
