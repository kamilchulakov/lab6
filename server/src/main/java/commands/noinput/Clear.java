package commands.noinput;

import commands.noinput.AbstractNoInputCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

public class Clear extends AbstractNoInputCommand {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear - a command to clear the collection.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        editor.clear();
        return new OutputData("Success", "Successfully cleared the collection.");
    }
}
