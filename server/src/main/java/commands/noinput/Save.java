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
        try {
            PropertiesGetter propertiesGetter = new PropertiesGetter();
            editor.save(propertiesGetter.getOutputFilename());
            return new OutputData("Success", String.format("Saved into %s!", propertiesGetter.getOutputFilename()));
        } catch (IOException e) {
            e.printStackTrace();
            return new OutputData("Failure", "It is not cool: IOException.");
        }
    }
}
