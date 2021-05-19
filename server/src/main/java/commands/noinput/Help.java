package commands.noinput;

import commands.Command;
import commands.noinput.AbstractNoInputCommand;
import henchmen.FabricForCommands;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

public class Help extends AbstractNoInputCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "help - a command which gives an information about\n" +
                "every single command which is supported in this app.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        FabricForCommands fabric = new FabricForCommands();
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command: fabric.getAllCommandsArrayList())
            stringBuilder.append(command.getDescription()).append("\n");
        return new OutputData("Success", stringBuilder.toString());
    }
}
