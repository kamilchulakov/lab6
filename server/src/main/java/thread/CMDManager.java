package thread;

import commands.noinput.Save;
import logic.Editor;
import logic.InputData;
import logic.OutputData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import commands.Command;
import henchmen.CommandHistory;
import henchmen.FabricForCommands;


import java.io.IOException;
import java.util.*;

public class CMDManager implements Runnable{
    private CommandHistory commandHistory = new CommandHistory();
    private static final Logger logger
            = LoggerFactory.getLogger(CMDManager.class);
    private final ArrayList<Command> commands = new ArrayList<>();
    private Editor editor;
    private ClientData clientData;
    public CMDManager() {
        FabricForCommands fabricForCommands = new FabricForCommands();
        commands.addAll(fabricForCommands.getAllCommandsArrayList());
        editor = new Editor();
    }
    public CMDManager(String path) {
        this();
        editor = new Editor();
    }

    public CMDManager(ClientData clientData) throws IOException {
        this();
        this.clientData = clientData;
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
        logger.warn(String.format("Executing command: %s with InputData: %s", justCommand, inputData));
        if (justCommand.equals("history")) {
            logger.warn("Recognized history.");
            return new OutputData("Success", getHistory(7));
        }
        OutputData result;
        synchronized (editor) {
            try {
                result = command.exec(editor, inputData);
                logger.warn("Executed command.");
            } catch (NullPointerException e) {
                logger.error("Command was not found.");
                result = new OutputData("Error", "Command was not found. Try again.");
            }
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

    public OutputData save() {
        return new Save().exec(editor, new InputData());
    }

    @Override
    public void run() {
        try {
            InputData inputData = clientData.getInputData();
            clientData.setOutputData(execute(inputData));
            AllThreadsDataQueues.toWriteQueue.add(clientData);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
