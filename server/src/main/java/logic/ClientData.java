package logic;

import henchmen.CommandHistory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ClientData {
    private SocketAddress clientAddress;
    private byte[] incomingData = new byte[65515];
    private CommandHistory commandHistory = null;
    private ByteBuffer buffer = ByteBuffer.wrap(incomingData);
    //private CommandHistory commandHistory = new CommandHistory();
    private boolean isConnected = false;

    public InputData getInputData() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(incomingData);
        ObjectInputStream is = new ObjectInputStream(in);
        try {
            InputData inputData = (InputData) is.readObject();
            if(inputData.getCommandName().equals("connect")) {
                isConnected = true;
            }
            if (commandHistory != null) commandHistory.add(inputData.getCommandName());
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
}
