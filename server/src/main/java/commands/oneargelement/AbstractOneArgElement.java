package commands.oneargelement;

import commands.Command;
import commands.CommandType;

public abstract class AbstractOneArgElement implements Command {
    @Override
    public CommandType[] getCommandType() {
        CommandType[] commandTypes = new CommandType[2];
        commandTypes[0] = CommandType.ONEARG;
        commandTypes[1] = CommandType.ELEMENT;
        return commandTypes;
    }
}
