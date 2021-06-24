package thread;

import henchmen.CommandHistory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.HashMap;

public class ReadHandler {
    private static HashMap<SocketAddress, CommandHistory> commandHistoryHashMap = new HashMap<>();
    public static void handleRead(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.configureBlocking(false);
        ClientData client = (ClientData) key.attachment();
        //System.out.println(client.getClientAddress());
        Buffer buffer = client.getBuffer();
        buffer.clear();
        SocketAddress socketAddress = channel.receive(client.getBuffer());
        if (!commandHistoryHashMap.containsKey(socketAddress)) {
            commandHistoryHashMap.put(socketAddress, new CommandHistory());
        }
        client.setCommandHistory(commandHistoryHashMap.get(socketAddress));
        client.setClientAddress(socketAddress);
        //System.out.println(client.getClientAddress());
        if (client.getClientAddress() != null) {
            System.out.println(((InetSocketAddress) client.getClientAddress()).getAddress() + ":" + ((InetSocketAddress) client.getClientAddress()).getPort() + " received packet");
        }
        client.setDatagramChannel(channel);
        AllThreadsDataQueues.toExecuteQueue.add(client);
    }
}
