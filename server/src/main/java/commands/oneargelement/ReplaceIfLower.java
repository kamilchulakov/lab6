package commands.oneargelement;

import commands.oneargelement.AbstractOneArgElement;
import logic.Editor;
import logic.InputData;
import logic.OutputData;
import objects.FabricLabWorks;

public class ReplaceIfLower extends AbstractOneArgElement {
    @Override
    public String getName() {
        return "replace_if_lower";
    }

    @Override
    public String getDescription() {
        return "replace_if_lower <key> - a command which removes element if lower than provided.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        FabricLabWorks fabricLabWorks = new FabricLabWorks();
        if (!editor.getCollection().containsKey(inputData.getCommandArg()))
            return new OutputData("Failure", "Key was not found!");
        editor.removeIfLower(inputData.getCommandArg(),
                    fabricLabWorks.makeLabworkFromInputData(inputData));
        return new OutputData("Undefined","Tried to remove.");
    }
}
