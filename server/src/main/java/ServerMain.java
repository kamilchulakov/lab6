import logic.ServerRunner;

public class ServerMain {
    public static void main(String[] args) {
        ServerRunner serverRunner = new ServerRunner("collection.json");
        serverRunner.start();
    }
}
