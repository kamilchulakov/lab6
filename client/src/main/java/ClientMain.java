import interfaces.CLI;
import interfaces.UI;

public class ClientMain {
    public static void main(String[] args) {
        UI ui = new CLI();
        ui.run();
    }
}
