package logic;

import henchmen.CommandHistory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadHandler {
    private static ArrayList<SocketAddress> stomach =new ArrayList<>();
    private static HashMap<SocketAddress, CommandHistory> commandHistoryHashMap = new HashMap<>();
    public static void handleRead(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.configureBlocking(false);
        ClientData client = (ClientData) key.attachment();
        Buffer buffer = client.getBuffer();
        buffer.clear();
        SocketAddress socketAddress = channel.receive(client.getBuffer());
        if (!stomach.contains(socketAddress)) {
            stomach.add(socketAddress);
            commandHistoryHashMap.put(socketAddress, new CommandHistory());
        }
        client.setCommandHistory(commandHistoryHashMap.get(socketAddress));
        client.setClientAddress(socketAddress);
        if (client.getClientAddress() != null) {
            System.out.println(((InetSocketAddress) client.getClientAddress()).getAddress() + ":" + ((InetSocketAddress) client.getClientAddress()).getPort() + " received packet");
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }
}
