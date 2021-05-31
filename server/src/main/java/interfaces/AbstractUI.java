package interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import henchmen.Validator;
import logic.CMDManager;
import logic.Editor;
import logic.InputData;
import logic.OutputData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * AbstractUI is the base of each UI realisation.
 */
public abstract class AbstractUI implements UI, Runnable{
    /**
     * Logger is used to make logs.
     */
    Logger logger;
    /**
     * CMDManager is used to make OutputData.
     */
    CMDManager cmdManager;
    /**
     * Validator is used in all methods which check something.
     */
    Validator validator;
    /**
     * List of filenames which were used in current execute_script loop.
     * It is used to prevent StackOverFLow.
     */
    ArrayList<String> cachedFilenames;
    /**
     * This flag is used in InputData, to make the difference between GUI and CLI.
     * True = CLI.
     */
    boolean inputDataFlag = true;

    /**
     * Class constructor which isn't modified in children.
     */
    public AbstractUI() {
        cmdManager = CMDManager.getInstance();
        cachedFilenames = new ArrayList<>();
        validator = new Validator();
        logger = LoggerFactory.getLogger(UI.class);
        createUI();
    }

    @Override
    public final void runUI() throws IOException {
        while (true) {
            String input = askForCommand();
            String pureCommand = input.split(" ")[0];
            if (pureCommand.equals("clear")) {
                //Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
            else {
                OutputData result = new OutputData("Info", "It is server, but you typed: " + pureCommand);
                logger.warn("This is result status: " + result.getStatusMessage());
                logger.warn("This is result:\n" + result.getResultMessage());
                display(result.getStatusMessage(), result.getResultMessage());
                if (pureCommand.equals("save")) {
                    result = cmdManager.save();
                    display(result.getStatusMessage(), result.getResultMessage());
                }
            }

        }
    }

    /**
     * Provides a command and kinda validates it.
     * @return String command.
     */
    protected final String askForCommand() {
        String input = getCommand();
        if (isValidCommand(input)) {
            logger.warn("Got a valid command: " + input);
            return input;
        } else {
            logger.error("Got an invalid command.");
            return "";
        }
    }


    /**
     * Provides argument which will be typed.
     * @param arg is a message which will be displayed with input.
     * @return String arg.
     */
    protected final String askForArg(String arg) {
        return getArg(arg);
    }

    /**
     * Provides argument.
     * @param arg is a message which will be displayed with input.
     * @return String arg.
     */
    protected abstract String getArg(String arg);

    /**
     * Provides command and asks it.
     * @return String command.
     */
    protected abstract String getCommand();

    /**
     * It is init method for each UI.
     */
    protected abstract void createUI();

    /**
     * Checks if command is valid,
     * @param command is String command.
     * @return true or false.
     */
    private boolean isValidCommand(String command) {
        return validator.validate(command);
    }

}
