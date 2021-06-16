package commands.noinput;

import commands.noinput.AbstractNoInputCommand;
import henchmen.PropertiesGetter;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

import java.io.IOException;

public class Save extends AbstractNoInputCommand {
    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "save - a command which saves the collection into collection.json";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        editor.save();
        return new OutputData("Success", "Saved into db!");
    }
}
