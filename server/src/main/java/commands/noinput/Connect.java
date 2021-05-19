package commands.noinput;

import logic.Editor;
import logic.InputData;
import logic.OutputData;

public class Connect extends AbstractNoInputCommand {
    @Override
    public String getName() {
        return "connect";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        return new OutputData("Success", "Успешно законнектился!");
    }
}
