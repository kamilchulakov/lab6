package interfaces;

import henchmen.Validator;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Provides working GUI interface.
 */
public class GUI extends AbstractUI{
    /**
     * JFrame is a base of Swing GUI.
     */
    JFrame frame;

    /**
     * Class constructor with collection filename.
     * @param filename is name of a json file.
     */
    public GUI(String filename) {
        cachedFilenames = new ArrayList<>();
        validator = new Validator();
        createUI();
    }

    /**
     * Class constructor.
     */
    public GUI() {
        super();
    }

    @Override
    protected String getArg(String arg) {
        return getInput("Type an "+ arg, arg);
    }

    @Override
    protected String getCommand() {
        return getInput("Type a command", "help");
    }

    @Override
    protected void createUI() {
        inputDataFlag = false;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
    }

    @Override
    public void display(String status, String message) {
        int constLength = 150;
        if (message.length() > constLength & !message.contains("help")) {
            int parts = message.length() / constLength;
            StringBuilder message2 = new StringBuilder();
            for (int i=0; i<parts; i++) {
                message2.append(message, i * constLength, (i + 1) * constLength);
                message2.append("\n");
            }
            message2.append(message.substring(parts * constLength));
            message2.append("\n");
            message = message2.toString();
        }
        if (status.equals("Error")) JOptionPane.showMessageDialog(frame,
                message, status, JOptionPane.ERROR_MESSAGE);
        else if (status.equals("Success")) JOptionPane.showMessageDialog(frame,
                message, status, JOptionPane.INFORMATION_MESSAGE);
        else if (status.equals("Failure")) JOptionPane.showMessageDialog(frame,
                message, status, JOptionPane.WARNING_MESSAGE);
        else if (status.equals("Undefined")) JOptionPane.showMessageDialog(frame,
                message, status, JOptionPane.QUESTION_MESSAGE);
        else JOptionPane.showMessageDialog(frame,
                    message, status, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Provides any input.
     * @param heading is a heading of input dialog.
     * @param basicValue is a value in input dialog.
     * @return String input
     */
    private String getInput(String heading, String basicValue) {
        return JOptionPane.showInputDialog(heading, basicValue);
    }
}
