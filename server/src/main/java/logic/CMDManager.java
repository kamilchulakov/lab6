package logic;

import commands.Command;
import henchmen.CommandHistory;
import henchmen.FabricForCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CMDManager {
    private CommandHistory commandHistory = new CommandHistory();
    private final Logger logger = LogManager.getLogger(CMDManager.class);
    private final ArrayList<Command> commands = new ArrayList<>();
    private Editor editor;
    public CMDManager() {
        FabricForCommands fabricForCommands = new FabricForCommands();
        commands.addAll(fabricForCommands.getAllCommandsArrayList());
    }
    public CMDManager(String path) {
        this();
        editor = new Editor(path);
    }
    private String getHistory(int number) {
        return commandHistory.getPureHistory(number);
    }
    public OutputData execute(Editor editor, String justCommand, InputData inputData) {
        return getOutputData(justCommand, inputData, editor);
    }
    public OutputData execute(String justCommand, InputData inputData) {
        return getOutputData(justCommand, inputData, editor);
    }
    public OutputData execute(InputData inputData) {
        return getOutputData(inputData.getCommandName(), inputData, editor);
    }

    private OutputData getOutputData(String justCommand, InputData inputData, Editor editor) {
        System.out.println(justCommand);
        Command command = getCommandByString(justCommand);
        if (inputData.getCommandArg() != null)
            commandHistory.add(justCommand + " " + inputData.getCommandArg());
        else commandHistory.add(justCommand);
        logger.info("Executing command...");
        if (justCommand.equals("history")) {
            logger.info("Recognized history.");
            return new OutputData("Success", getHistory(7));
        }
        OutputData result;
        try {
            result = command.exec(editor, inputData);
            logger.info("Executed command.");
        } catch (NullPointerException e) {
            logger.error("Command was not found.");
            result = new OutputData("Error", "Command was not found. Try again.");
        }
        return result;
    }

    private Command getCommandByString(String command) {
        for (Command command1: commands) {
            if (command1.getName().equals(command)) {
                return command1;
            }
        }
        return null;
    }

    public Editor getCollection() {
        return editor;
    }
}
