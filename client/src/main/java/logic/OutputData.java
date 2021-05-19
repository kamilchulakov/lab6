package logic;

import java.io.Serializable;

public class OutputData implements Serializable {
    static final long serialVersionUID = 8129437039424566965L;
    private String statusMessage;
    private String resultMessage;

    public OutputData(String statusMessage, String resultMessage) {
        this.statusMessage = statusMessage;
        this.resultMessage = resultMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
