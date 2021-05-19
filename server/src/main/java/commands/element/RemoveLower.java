package commands.element;

import commands.element.AbstractElementCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;
import objects.FabricLabWorks;
import objects.LabWork;

public class RemoveLower extends AbstractElementCommand {
    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public String getDescription() {
        return "remove_lower <key> - a command which removes the elements\nwhich are lower than <key> one.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        try {
            FabricLabWorks fabricLabWorks = new FabricLabWorks();
            LabWork labwork = fabricLabWorks.makeLabworkFromInputData(inputData);
            editor.removeAllLowerByLabwork(labwork);
        } catch (Exception e) {
            e.printStackTrace();
            return new OutputData("Failure", "Some problems with input data.");
        }
        return new OutputData("Success", "Removed.");
    }
}
