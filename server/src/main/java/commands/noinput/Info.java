package commands.noinput;

import commands.noinput.AbstractNoInputCommand;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

import java.util.Calendar;

public class Info extends AbstractNoInputCommand {
    private Calendar calendar;
    public Info() {
        calendar = Calendar.getInstance();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "info - a command to get info about the collection:\n type, time, etc";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
            return new OutputData("Success",
                    String.format("Collection info:\nType: %s\nDate: %s\nSize: %s",
                editor.getCollection().getClass().getName(), calendar.getTime().toString(), editor.getCollection().size()));
    }
}
