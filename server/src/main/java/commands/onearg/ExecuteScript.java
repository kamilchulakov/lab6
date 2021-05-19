package commands.onearg;

import commands.onearg.AbstractOneArgCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

public class ExecuteScript extends AbstractOneArgCommand {
    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "execute_script <filename> - a command which executes a script.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        return new OutputData("Error", "It should not be here.");
    }
}
