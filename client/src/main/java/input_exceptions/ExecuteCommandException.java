package input_exceptions;

public class ExecuteCommandException extends Exception{
    String command2;
    public ExecuteCommandException(String command) {
        command2 = command;
    }
    public String getCommand() {
        return command2;
    }
}
