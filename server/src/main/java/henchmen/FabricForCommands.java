package henchmen;

import commands.*;
import commands.discipline.RemoveAnyByDiscipline;
import commands.element.RemoveLower;
import commands.noinput.*;
import commands.onearg.ExecuteScript;
import commands.onearg.Login;
import commands.onearg.Register;
import commands.onearg.RemoveByKey;
import commands.oneargelement.InsertKey;
import commands.oneargelement.Update;

import java.util.ArrayList;
import java.util.List;

public class FabricForCommands {
    private ArrayList<Command> arrayList = new ArrayList<>();
    private ArrayList<String> noInputCommands = new ArrayList<>();
    private ArrayList<String> oneArgCommands = new ArrayList<>();
    private ArrayList<String> elementCommands = new ArrayList<>();
    private ArrayList<String> disciplineCommands = new ArrayList<>();

    public FabricForCommands() {
        addCommandsToList(arrayList);
        addCommandToTypedLists();
    }

    public ArrayList<Command> getAllCommandsArrayList() {
        return arrayList;
    }

    private void addCommandsToList(List<Command> list) {
        list.add(new Help());
        list.add(new History());
        list.add(new Exit());
        list.add(new Show());
        list.add(new Info());
        list.add(new RemoveByKey());
        list.add(new Clear());
        list.add(new RemoveLower());
        list.add(new ExecuteScript());
        list.add(new AverageMinimalPoint());
        list.add(new PrintDescendingDifficulty());
        list.add(new RemoveAnyByDiscipline());
        list.add(new InsertKey());
        list.add(new Update());
        list.add(new Save());
        list.add(new Connect());
        list.add(new Login());
        list.add(new Register());
    }

    private void addCommandToTypedLists() {
        for (Command command: arrayList) {
            for (CommandType commandType: command.getCommandType()) {
                if (commandType == CommandType.NOINPUT) noInputCommands.add(command.getName());
                else if (commandType == CommandType.ONEARG) oneArgCommands.add(command.getName());
                else if (commandType == CommandType.DISCIPLINE) disciplineCommands.add(command.getName());
                else if (commandType == CommandType.ELEMENT) elementCommands.add(command.getName());
            }
        }
    }

    public ArrayList<String> getNoInputCommands() {
        return noInputCommands;
    }

    public ArrayList<String> getOneArgCommands() {
        return oneArgCommands;
    }

    public ArrayList<String> getElementCommands() {
        return elementCommands;
    }

    public ArrayList<String> getDisciplineCommands() {
        return disciplineCommands;
    }

    public boolean ifContainsInNoInputCommands(String command) {
        return noInputCommands.contains(command);
    }
    public boolean ifContainsInElementCommands(String command) {
        return elementCommands.contains(command);
    }
    public boolean ifContainsInDisciplineCommands(String command) {
        return disciplineCommands.contains(command);
    }
    public boolean ifContainsInOneArgCommands(String command) {
        return oneArgCommands.contains(command);
    }
}
