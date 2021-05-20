package commands.onearg;

import commands.onearg.AbstractOneArgCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

import java.util.Arrays;

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
        Poop poop = new Poop();
        poop.run(editor, inputData);
        return new OutputData("ExecuteScript result:", poop.cachedResults.toString());
    }
}
