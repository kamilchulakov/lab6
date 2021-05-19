package interfaces;

import logic.OutputData;

/**
 * UI is the abstract base of this program.
 */
public interface UI {
    /**
     * Runs the UI.
     */
    void run();

    /**
     * Is used to display something.
     * @param status is typed message: Error, Success, Underfined.
     * @param message is just result message.
     */
    void display(String status, String message);
    void display(OutputData outputData);
}
