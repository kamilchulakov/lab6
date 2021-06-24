package interfaces;

import henchmen.Validator;
import logic.DefaultCommandManager;
import thread.CMDManager;

import java.io.IOException;
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
        cmdManager = new DefaultCommandManager();
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
        String string = "";
        try {
            string = scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("\n");
            System.exit(0);
        } return string;
    }

    @Override
    public void run() {
        try {
            runUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
