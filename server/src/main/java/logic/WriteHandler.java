package logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

public class WriteHandler {
    public static void handleWrite(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientData client = (ClientData) key.attachment();
        client.getBuffer().flip();
        OutputData answer = ServerRunner.getAnswerHandler().execute(client.getInputData());
        if(answer != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(answer);
            byte[] replyBytes = outputStream.toByteArray();
            ByteBuffer buff = ByteBuffer.wrap(replyBytes);
            channel.send(buff, client.getClientAddress());
            //userLogger.info("send answer " + replyBytes.length + " bytes");
        }
        key.interestOps(SelectionKey.OP_READ);
    }
}
