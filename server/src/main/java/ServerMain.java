import database.DatabaseService;
import logic.ServerRunner;

public class ServerMain {
    public static void main(String[] args) {
        DatabaseService databaseService = new DatabaseService();
        databaseService = null;
        ServerRunner serverRunner = new ServerRunner();
        serverRunner.start();
    }
}
