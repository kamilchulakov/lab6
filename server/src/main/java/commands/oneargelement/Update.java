package commands.oneargelement;

import commands.oneargelement.AbstractOneArgElement;
import logic.Editor;
import logic.InputData;
import logic.OutputData;
import objects.FabricLabWorks;
import objects.LabWork;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Update extends AbstractOneArgElement {
    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "update <id> - a command which updates element values by id.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        try {
            FabricLabWorks fabricLabWorks = new FabricLabWorks();
            LabWork labwork = fabricLabWorks.makeLabworkFromInputData(inputData);
            editor.update(Integer.parseInt(inputData.getCommandArg()), labwork);
        } catch (NoSuchElementException e) {
            return new OutputData("Error", "Invalid id: no such element");
        } catch (SQLException e) {
            return new OutputData("Error", "Not an author!");
        } catch (Exception e) {
            e.printStackTrace();
            return new OutputData("Error", "Invalid InputData.");
        } return new OutputData("Success", "Updated.");
    }
}
