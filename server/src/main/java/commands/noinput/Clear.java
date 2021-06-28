package commands.noinput;

import commands.noinput.AbstractNoInputCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

import java.sql.SQLException;

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
        if (inputData.getAuth().equals("admin")) editor.clear();
        else {
            try {
                editor.clear(inputData.getAuth());
                return new OutputData("Success", "Successfully cleared your collection.");
            } catch (SQLException exception) {
                return new OutputData("Error", "Database problems....");
            }
        }
        return new OutputData("Success", "Successfully cleared the collection.");
    }
}
