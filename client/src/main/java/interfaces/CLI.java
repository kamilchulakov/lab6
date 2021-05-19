package interfaces;

import henchmen.Validator;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Provides working CLI interface.
 */
public class CLI extends AbstractUI{
    /**
     * Scanner is a base of CLI.
     */
    public Scanner scanner;

    /**
     * Class constructor with collection filename.
     * @param filename is a name of a json file.
     */
    public CLI(String filename) {
        cachedFilenames = new ArrayList<>();
        validator = new Validator();
        createUI();
    }

    /**
     * Class constructor.
     */
    public CLI() {
        super();
    }

    @Override
    protected String getArg(String arg) {
        System.out.printf("Type an %s: ", arg);
        return getInput();
    }

    @Override
    protected String getCommand() {
        //System.out.print("Type a command: ");
        return getInput();
    }

    @Override
    protected void createUI() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void display(String status, String message) {
        System.out.printf("-------------------\nStatus: %s\nMessage: %s\n------------------\n", status, message.trim());
    }

    /**
     * Provides any input.
     * @return String input
     */
    private String getInput() {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.exit(0);
            return "";
        }
    }
}
