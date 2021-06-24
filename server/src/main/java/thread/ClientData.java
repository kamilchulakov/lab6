package thread;

import henchmen.CommandHistory;
import logic.InputData;
import logic.OutputData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class ClientData {
    private SocketAddress clientAddress;
    private byte[] incomingData = new byte[65515];
    private CommandHistory commandHistory = null;
    private DatagramChannel datagramChannel;
    private ByteBuffer buffer = ByteBuffer.wrap(incomingData);
    //private CommandHistory commandHistory = new CommandHistory();
    private boolean isConnected = false;
    private OutputData outputData = null;
    private InputData inputData = null;

    public InputData getInputData() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(incomingData);
        ObjectInputStream is = new ObjectInputStream(in);
        try {
            InputData inputData = (InputData) is.readObject();
            if(inputData.getCommandName().equals("connect")) {
                isConnected = true;
            }
            if (commandHistory != null && !inputData.getCommandName().equals("connect")) commandHistory.add(inputData.getCommandName());
            this.inputData = inputData;
            return inputData;
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

//    public void addToHistory(InputData inputData) {
//        commandHistory.add(inputData.getCommandName());
//    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public byte[] getIncomingData() {
        return incomingData;
    }

    public SocketAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(SocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public void setCommandHistory(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    public String getCommandHistory() {
        return commandHistory.getHistory(10);
    }

    public OutputData getOutputData() {
        return outputData;
    }

    public void setOutputData(OutputData outputData) {
        this.outputData = outputData;
    }

    public void setIncomingData(byte[] incomingData) {
        this.incomingData = incomingData;
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public void setDatagramChannel(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    @Override
    public String toString() {
        if (inputData != null && outputData != null) {
            return "ClientData{" +
                    "clientAddress=" + clientAddress +
                    ", command=" + inputData.getCommandName() +
                    ", result=" + outputData.getResultMessage().substring(0, 4) +
                    "...}";
        } else return "clientAddress=" + clientAddress + " has no input/output data";

    }

    public CommandHistory getCommandHistoryArray() {
        return commandHistory;
    }
}
