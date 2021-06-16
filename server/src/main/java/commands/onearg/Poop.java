package commands.onearg;

import org.slf4j.LoggerFactory;
import henchmen.Validator;
import input_exceptions.CancelException;
import input_exceptions.ExecuteCommandException;
import input_exceptions.LessThanException;
import input_exceptions.MoreThanException;
import thread.CMDManager;
import logic.Editor;
import logic.InputData;
import logic.OutputData;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Poop {
    Validator validator = new Validator();
    ArrayList<String> cachedFilenames;
    CMDManager cmdManager;
    Logger logger = LoggerFactory.getLogger(Poop.class);
    Editor editor;
    ArrayList<String> cachedResults;

    public void run(Editor editor, InputData inputData) {
        cmdManager = new CMDManager();
        cachedFilenames = new ArrayList<>();
        cachedResults = new ArrayList<>();
        this.editor = editor;
        executeScript(inputData.getCommandArg());
    }

    private boolean isValidCommand(String command) {
        return validator.validate(command);
    }

    /**
     * Provides a base for executing a script.
     *
     * @param input is command with arg (filename).
     */
    private void executeScript(String input) {
        logger.info("Recognized execute_script.");
        try {
            String filename = input;
            if (!cachedFilenames.contains(filename)) {
                cachedFilenames.add(filename);
                executeScriptLoop(filename);
            } else logger.warn("Error", "Prevented StackOverflow! Filename: " + filename);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the commands and does something. (Like run).
     *
     * @param filename is a name of a file where commands should be listed.
     */
    private void executeScriptLoop(String filename) {
        try {
            File file = new File(filename);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                processCommandFromExecuteScriptLoop(data, myReader);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            cachedResults.add("File not found!");
        }
    }

    /**
     * Checks if input contains execute_script
     *
     * @param input is command with arg line.
     * @return true or false.
     */
    private boolean isExecuteScript(String input) {
        return validator.isExecuteScript(input);
    }

    /**
     * Continues execute_script loop if a command from the file is valid.
     *
     * @param input is a command with arg.
     */
    private void processCommandFromExecuteScriptLoop(String input, Scanner scanner) {
        if (isValidCommand(input)) {
            if (isExecuteScript(input)) {
                executeScript(input);
            } else {
                String pureCommand = input.split(" ")[0];
                logger.info("Getting input data.");
                InputData inputData = new InputData(false);
                boolean[] flags = validator.getInputDataFlagsForCommand(pureCommand);
                try {
                    askForInputCheckForCommand(flags, inputData, input, scanner);
                    if (inputData.equals(new InputData())) logger.info("No input data was provided.");
                    OutputData result = cmdManager.execute(editor, pureCommand, inputData);
                    cachedResults.add(result.getResultMessage());
                    logger.info("This is result status: " + result.getStatusMessage());
                    logger.info("This is result:\n" + result.getResultMessage());
                } catch (ExecuteCommandException e) {
                    logger.warn("Found command. Not arg.");
                    processCommandFromExecuteScriptLoop(e.getCommand(), scanner);
                }
            }
        }
    }

    private void askForInputCheckForCommand(boolean[] flags, InputData inputData, String input, Scanner scanner) throws ExecuteCommandException {
        if (needsArg(flags)) {
            setArgToInputDataExec(inputData, input);
        }
        if (needsName(flags)) {
            String string = scanner.nextLine();
            if (isValidCommand(string)) throw new ExecuteCommandException(string);
            setLabNameToInputDataExec(inputData, string);
        }
        if (needsCoordinateX(flags)) {
            String string = scanner.nextLine();
            if (isValidCommand(string)) throw new ExecuteCommandException(string);
            setCorXToInputDataExec(inputData, string);
        }
        if (needsCoordinateY(flags)) {
            String string = scanner.nextLine();
            if (isValidCommand(string)) throw new ExecuteCommandException(string);
            setCorYToInputDataExec(inputData, string);
        }
        if (needsMinimalPoint(flags)) {
            String string = scanner.nextLine();
            if (isValidCommand(string)) throw new ExecuteCommandException(string);
            setMinimalPointToInputDataExec(inputData, string);
        }
        if (needsDifficulty(flags)) {
            String string = scanner.nextLine();
            if (isValidCommand(string)) throw new ExecuteCommandException(string);
            setDifficultyToInputDataExec(inputData, string);
        }
        if (needsDiscName(flags)) {
            String string = scanner.nextLine();
            if (isValidCommand(string)) throw new ExecuteCommandException(string);
            setDiscNameToInputDataExec(inputData, string);
        }
        if (needsDiscHours(flags)) {
            String string = scanner.nextLine();
            if (isValidCommand(string)) throw new ExecuteCommandException(string);
            setDiscHoursToInputDataExec(inputData, string);
        }
    }

    protected void setDiscHoursToInputDataExec(InputData inputData, String arg) {
        try {
            inputData.setSelfStudyHours(arg);
            logger.info("Got discipline hours.");
        } catch (NullPointerException e) {
            logger.info("Everything is okay, but cancel was pushed.");
            throw new CancelException();
        } catch (Exception e) {
            logger.error("Unhandled exception: " + e.getMessage());
        }
    }
    /**
     * Checks if a command needs arg from its line.
     * @param flags is flags.
     * @return true or false.
     */
    private boolean needsArg(boolean[] flags) {
        return flags[0];
    }
    /**
     * Checks if a command needs labname.
     * @param flags is flags.
     * @return true or false.
     */
    private boolean needsName(boolean[] flags) {
        return flags[1];
    }
    /**
     * Checks if a command needs coordinate x.
     * @param flags is flags.
     * @return true or false.
     */
    private boolean needsCoordinateX(boolean[] flags) {
        return flags[2];
    }
    /**
     * Checks if a command needs coordinate y.
     * @param flags is flags.
     * @return true or false.
     */
    private boolean needsCoordinateY(boolean[] flags) {
        return flags[3];
    }
    /**
     * Checks if a command needs minimal point.
     * @param flags is flags.
     * @return true or false.
     */
    private boolean needsMinimalPoint(boolean[] flags) {
        return flags[4];
    }
    /**
     * Checks if a command needs difficulty.
     * @param flags is flags.
     * @return true or false.
     */
    private boolean needsDifficulty(boolean[] flags) {
        return flags[5];
    }
    /**
     * Checks if a command needs discipline name.
     * @param flags is flags.
     * @return true or false.
     */
    private boolean needsDiscName(boolean[] flags) {
        return flags[6];
    }
    /**
     * Checks if a command needs study hours.
     * @param flags is flags.
     * @return true or false.
     */
    private boolean needsDiscHours(boolean[] flags) {
        return flags[7];
    }


    protected void setDiscNameToInputDataExec(InputData inputData, String arg) {
        try {
            inputData.setDisciplineName(arg);
            logger.info("Got discipline name.");
        } catch (NullPointerException e) {
            logger.info("Everything is okay, but cancel was pushed.");
            throw new CancelException();
        } catch (Exception e) {
            logger.error("Unhandled exception: " + e.getMessage());
        }
    }

    ;

    protected void setDifficultyToInputDataExec(InputData inputData, String arg) {
        try {
            inputData.setDifficulty(arg);
            logger.info("Got difficulty.");
        } catch (IllegalArgumentException e) {
            logger.error("IllegalArgument exception: " + e.getMessage());
        } catch (NullPointerException e) {
            logger.info("Everything is okay, but cancel was pushed.");
            throw new CancelException();
        } catch (Exception e) {
            logger.error("Unhandled exception: " + e.getMessage());
        }
    }

    ;

    protected void setMinimalPointToInputDataExec(InputData inputData, String arg) {
        try {
            inputData.setMinimalPoint(arg);
            logger.info("Got minimal point.");
        } catch (NullPointerException e) {
            logger.info("Everything is okay, but cancel was pushed.");
            throw new CancelException();
        } catch (NumberFormatException e) {
            logger.error("Number format exception for minimal point.");
        } catch (LessThanException e) {
            logger.warn("Less than exception for minimal point.");
        } catch (Exception e) {
            logger.error("Unhandled exception: " + e.getMessage());
        }
    }

    ;

    protected void setCorYToInputDataExec(InputData inputData, String arg) {
        try {
            inputData.setCoordinateY(Float.parseFloat(arg));
            logger.info("Got coordinate Y.");
        } catch (MoreThanException e) {
            logger.warn("More than exception for Y.");
        } catch (NumberFormatException e) {
            logger.error("Number format exception for Y.");
        } catch (LessThanException e) {
            logger.warn("Less than exception for Y.");
        } catch (NullPointerException e) {
            logger.info("Everything is okay, but cancel was pushed.");
            throw new CancelException();
        } catch (Exception e) {
            logger.error("Unhandled exception: " + e.getMessage());
        }
    }

    ;

    protected void setCorXToInputDataExec(InputData inputData, String arg) {
        try {
            inputData.setCoordinateX(Float.parseFloat(arg));
            logger.info("Got coordinate X.");
        } catch (MoreThanException e) {
            logger.warn("More than exception for X.");
        } catch (LessThanException e) {
            logger.warn("Less than exception for X.");
        } catch (NullPointerException e) {
            logger.info("Everything is okay, but cancel was pushed.");
            throw new CancelException();
        } catch (Exception e) {
            logger.error("Unhandled exception for X: " + e.getMessage());
        }
    }

    ;

    protected void setLabNameToInputDataExec(InputData inputData, String input) {
        try {
            inputData.setLabName(input);
            logger.info("Got labwork name.");
        } catch (NullPointerException e) {
            logger.info("Everything is okay, but cancel was pushed.");
            throw new CancelException();
        } catch (Exception e) {
            logger.warn("Invalid labwork name.");
        }
    }

    ;

    protected void setArgToInputDataExec(InputData inputData, String input) {
        if (input.split(" ").length == 1) {
            logger.warn("No arg for command was found.");
            inputData.setCommandArg(null);
        } else {
            inputData.setCommandArg(input.split(" ")[1]);
        }
        logger.info("Got command arg.");
    }
}
