package commands.onearg;

import commands.onearg.AbstractOneArgCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

public class RemoveByKey extends AbstractOneArgCommand {

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescription() {
        return "remove_key <key> - a command which removes an element by key.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        if (!editor.getCollection().containsKey(inputData.getCommandArg()))
            return new OutputData("Failure", "Key was not found!");
        editor.removeElementByKey(inputData.getCommandArg());
        return new OutputData("Success", "Successfully removed the element.");
    }
}
