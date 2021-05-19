package logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class ClientData {
    private SocketAddress clientAddress;
    private byte[] incomingData = new byte[65515];
    private ByteBuffer buffer = ByteBuffer.wrap(incomingData);
    private boolean isConnected = false;

    public InputData getInputData() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(incomingData);
        ObjectInputStream is = new ObjectInputStream(in);
        try {
            InputData inputData = (InputData) is.readObject();
            if(inputData.getCommandName().equals("connect")) {
                isConnected = true;
            }
            return inputData;
        } catch (ClassNotFoundException e) {
            throw new IOException();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

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
}
