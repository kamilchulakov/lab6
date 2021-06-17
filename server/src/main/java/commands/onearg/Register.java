package commands.onearg;

import logic.Editor;
import logic.InputData;
import logic.OutputData;

import java.sql.SQLException;

public class Register extends AbstractOneArgCommand{
    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "register <user> <pass> - can register new user";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        OutputData outputData;
        try {
            String user = inputData.getCommandArg().split(" ")[0];
            String pass = inputData.getCommandArg().split(" ")[1];
            outputData = new OutputData("Success", editor.addUser(user, pass));
        } catch (SQLException e) {
            e.printStackTrace();
            outputData = new OutputData("Error", "Some troubles with DB");
        } return outputData;
    }
}
