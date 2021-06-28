package commands.oneargelement;

import commands.oneargelement.AbstractOneArgElement;
import logic.Editor;
import logic.InputData;
import logic.OutputData;
import objects.FabricLabWorks;
import objects.LabWork;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class InsertKey extends AbstractOneArgElement {
    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "insert <key> - a command which asks to give element info and inserts it by key.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        try {
            FabricLabWorks fabricLabWorks = new FabricLabWorks();
            LabWork labwork = fabricLabWorks.makeLabworkFromInputData(inputData);
            editor.insert(inputData.getCommandArg(), labwork);
        } catch (SQLException e) {
            return new OutputData("Failure", "Database problems...");
        } catch (NoSuchElementException e) {
            return new OutputData("Failure", "Key already in use");
        }
        return new OutputData("Success", "Inserted.");
    }
}
