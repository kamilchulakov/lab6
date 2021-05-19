package logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

public class ReadHandler {
    public static void handleRead(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.configureBlocking(false);
        ClientData client = (ClientData) key.attachment();
        client.getBuffer().clear();
        client.setClientAddress(channel.receive(client.getBuffer()));
        if (client.getClientAddress() != null) {
            System.out.println(((InetSocketAddress) client.getClientAddress()).getAddress() + ":" + ((InetSocketAddress) client.getClientAddress()).getPort() + " received packet");
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }
}
