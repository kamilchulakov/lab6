package commands.noinput;

import commands.noinput.AbstractNoInputCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

public class AverageMinimalPoint extends AbstractNoInputCommand {
    @Override
    public String getName() {
        return "average_of_minimal_point";
    }

    @Override
    public String getDescription() {
        return "average_of_minimal_point - a command which show an average\n minimal point field value of collection items.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        return new OutputData("Success", editor.getAverageMinimalPoint());
    }
}
