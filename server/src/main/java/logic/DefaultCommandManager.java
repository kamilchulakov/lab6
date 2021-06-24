package logic;

import commands.Command;
import henchmen.CommandHistory;
import henchmen.FabricForCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.CMDManager;
import thread.ClientData;

import java.io.IOException;
import java.util.ArrayList;

public class DefaultCommandManager {
    private CommandHistory commandHistory = new CommandHistory();
    private final Logger logger
            = LoggerFactory.getLogger(CMDManager.class);
    private final ArrayList<Command> commands = new ArrayList<>();
    private Editor editor;
    private final ClientData clientData;
    public DefaultCommandManager() {
        FabricForCommands fabricForCommands = new FabricForCommands();
        commands.addAll(fabricForCommands.getAllCommandsArrayList());
        clientData = null;
        editor = new Editor();
    }
    public DefaultCommandManager(String path) {
        this();
        editor = new Editor();
    }

    public DefaultCommandManager(ClientData clientData) throws IOException {
        FabricForCommands fabricForCommands = new FabricForCommands();
        commands.addAll(fabricForCommands.getAllCommandsArrayList());
        editor = new Editor();
        this.clientData = clientData;
    }

    private String getHistory(int number) {
        return clientData.getCommandHistory();
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
        long startTime = System.currentTimeMillis();
        Command command = getCommandByString(justCommand);
        if (inputData.getCommandArg() != null)
            commandHistory.add(justCommand + " " + inputData.getCommandArg());
        else commandHistory.add(justCommand);
        if (!justCommand.equals("login") && inputData.getAuth() == null)
            if (!justCommand.equals("connect") && !justCommand.equals("register")) return new OutputData("Please login", "Use login or register before " + inputData.getCommandName());
        logger.warn(String.format("Executing command: %s with InputData: %s", justCommand, inputData));
        if (justCommand.equals("history")) {
            logger.warn("Recognized history.");
            return new OutputData("Success", getHistory(7));
        }
        OutputData result;
        synchronized (editor) {
            try {
                result = command.exec(editor, inputData);
                logger.warn("Executed command: " + justCommand);
            } catch (NullPointerException e) {
                logger.error("Command was not found.");
                //if (command.getName().equals("login")) e.printStackTrace();
                result = new OutputData("Error", "Command was not found. Try again.");
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println(justCommand + ": That took " + (endTime - startTime) + " milliseconds");
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
