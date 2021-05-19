package commands.discipline;

import commands.discipline.AbstractDisciplineCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;
import objects.Discipline;

public class RemoveAnyByDiscipline extends AbstractDisciplineCommand {
    @Override
    public String getName() {
        return "remove_any_by_discipline";
    }

    @Override
    public String getDescription() {
        return "remove_any_by_discipline <discipline> - a command to remove by disc.";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        OutputData result;
        try {
            String name = inputData.getDisciplineName();
            Long hours = inputData.getSelfStudyHours();
            Discipline discipline = new Discipline(name, hours);
            result = new OutputData("Success", editor.removeByDiscipline(discipline));
        } catch (NumberFormatException numberFormatException) {
            result = new OutputData("Failure", "Invalid number. Try again.");
        }
        return result;
    }
}
