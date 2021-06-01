package logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestHandler {
    private DatagramChannel channel;
    private int PORT;
    private InetAddress IPAddress;
    private InputData lastCommand;
    private SocketAddress serverAddress;
    private boolean connected;
    private boolean sentLast;
    private boolean runnable;

    public RequestHandler(int port) {
        PORT = port;
        connected = false;
        runnable = true;
        serverAddress = new InetSocketAddress("localhost",
                PORT);
    }

    public OutputData connect() {
        if (connected) {
            return new OutputData("Error", "Already connected!");
        }
        try {
            IPAddress = InetAddress.getByName("localhost");
            //System.out.println("Попытка подключиться к клиенту " + IPAddress + ":" + PORT);
            channel = DatagramChannel.open();
            channel.connect(serverAddress);
            channel.configureBlocking(false);
            InputData inputData = new InputData();
            inputData.setCommandName("connect");
            send(inputData);
            runnable = true;
            return new OutputData("Success", "Connected to server.");
        } catch (IOException e) {
            return new OutputData("Error", "Can't connect to server.");
        }
    }

    public OutputData execute(InputData inputData) {
        send(inputData);
        return new OutputData("Success", "Запрос на сервер отправлен!!");
    };

    public void send(InputData inputData) {
        try {
            if (!inputData.getCommandName().equals("connect")) {
                lastCommand = inputData;
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(inputData);
            ByteBuffer buffer = ByteBuffer.wrap(outputStream.toByteArray());
            channel.send(buffer, serverAddress);
        } catch (IOException e) {
            sentLast = false;
            System.err.println("Произошла ошибка при отправке запроса!");
            runnable = false;
        }
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public void setChannel(DatagramChannel channel) {
        this.channel = channel;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(InetAddress IPAddress) {
        this.IPAddress = IPAddress;
    }

    public InputData getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(InputData lastCommand) {
        this.lastCommand = lastCommand;
    }

    public SocketAddress getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(SocketAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isRunnable() {
        return runnable;
    }

    public void setRunnable(boolean runnable) {
        this.runnable = runnable;
    }
}
