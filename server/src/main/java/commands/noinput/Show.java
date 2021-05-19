package commands.noinput;

import commands.noinput.AbstractNoInputCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

public class Show extends AbstractNoInputCommand {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "show - a command which shows the collection items";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        return new OutputData("Success", editor.getStringCollection());
    }
}
