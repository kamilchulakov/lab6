package commands.onearg;

import logic.Editor;
import logic.InputData;
import logic.OutputData;

import java.sql.SQLException;

public class Login extends AbstractOneArgCommand{
    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "login <user> <pass>";
    }

    @Override
    public OutputData exec(Editor editor, InputData inputData) {
        String user = inputData.getCommandArg().split(" ")[0];
        String pass = inputData.getCommandArg().split(" ")[1];
        try {
            if (editor.userExists(user, pass)) {
                return new OutputData("Login",
                        user);
            }
        } catch (SQLException exception) {
            return new OutputData("Failure", "SQL Exception");
        }
        return new OutputData("Failure", "Invalid login/pass");
    }
}
