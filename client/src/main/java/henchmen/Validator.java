package henchmen;

import java.util.ArrayList;

public class Validator {

    public boolean validate(String command) {
        if (command == null) System.exit(0);
        String commandName = command.toLowerCase().trim().split(" ")[0];
        return ifContainsInDisciplineCommands(commandName) || ifContainsInElementCommands(commandName)
                || ifContainsInNoInputCommands(commandName) || ifContainsInOneArgCommands(commandName);
    }
    public boolean needsArgs(String command) {
        return command.equals("remove_any_by_discipline") | command.equals("insert") | command.equals("update") |
                command.equals("remove_lower") | command.equals("replace_if_lower");
    }

    public boolean isExecuteScript(String input) {
        return input.split(" ")[0].equals("execute_script");
    }

    public ArrayList<String> getArgsForStringCommand(String command) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (command.equals("remove_any_by_discipline")) {
            arrayList.add("discipline_name");
            arrayList.add("discipline_hours");
        } else if (command.equals("insert") | command.equals("update") |
        command.equals("remove_lower") | command.equals("replace_if_lower")) {
            arrayList.add("labwork_name");
            arrayList.add("coordinate_x");
            arrayList.add("coordinate_y");
            arrayList.add("minimal_point");
            arrayList.add("difficulty_level");
            arrayList.add("discipline_name");
            arrayList.add("discipline_hours");
        }
        return arrayList;
    }
    public boolean[] getInputDataFlagsForCommand(String command) {
        boolean[] flags = new boolean[8];
        flags[0] = false;
        flags[1] = false;
        flags[2] = false;
        flags[3] = false;
        flags[4] = false;
        flags[5] = false;
        flags[6] = false;
        flags[7] = false;

        if (ifContainsInNoInputCommands(command)) return flags;
        if (ifContainsInOneArgCommands(command)) flags[0] = true;
        if (ifContainsInDisciplineCommands(command)) {
            flags[7] = true;
            flags[6] = true;
        }
        if (ifContainsInElementCommands(command)) {
            flags[1] = true;
            flags[2] = true;
            flags[3] = true;
            flags[4] = true;
            flags[5] = true;
            flags[6] = true;
            flags[7] = true;
        }
        return flags;
    }

    private boolean ifContainsInElementCommands(String command) {
        return command.equals("update") || command.equals("remove_lower") || command.equals("insert_key") || command.equals("replace_if_lower");
    }

    private boolean ifContainsInDisciplineCommands(String command) {
        return command.equals("remove_any_by_discipline");
    }

    private boolean ifContainsInOneArgCommands(String command) {
        return command.equals("update") || command.equals("execute_script") || command.equals("remove_by_key")
                || command.equals("insert") || command.equals("replace_if_lower");
    }

    private boolean ifContainsInNoInputCommands(String command) {
        return command.equals("help") || command.equals("connect") || command.equals("show") ||
                command.equals("history") || command.equals("info") || command.equals("exit") ||
                command.equals("print_field_descending_difficulty") || command.equals("save") ||
                command.equals("average_of_minimal_point");
    }
}
