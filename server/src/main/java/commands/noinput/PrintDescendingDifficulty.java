package commands.noinput;

import commands.noinput.AbstractNoInputCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

public class PrintDescendingDifficulty extends AbstractNoInputCommand {
    @Override
    public String getName() {
        return "print_field_descending_difficulty";
    }

    @Override
    public String getDescription() {
        return "print_field_descending_difficulty - a command which prints descending difficulty.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        return new OutputData("Success", editor.getDescendingDifficulty());
    }
}
