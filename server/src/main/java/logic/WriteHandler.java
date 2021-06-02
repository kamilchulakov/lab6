package logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

public class WriteHandler {
    private static final Logger logger = LoggerFactory.getLogger(WriteHandler.class);
    public static void handleWrite(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientData client = (ClientData) key.attachment();
        client.getBuffer().flip();
        InputData inputData = client.getInputData();
        //client.addToHistory(inputData);
        OutputData answer = ServerRunner.getAnswerHandler().execute(inputData);
        if(answer != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(answer);
            byte[] replyBytes = outputStream.toByteArray();
            ByteBuffer buff = ByteBuffer.wrap(replyBytes);
            channel.send(buff, client.getClientAddress());
            logger.info("Sent answer " + replyBytes.length + " bytes");
        }
        key.interestOps(SelectionKey.OP_READ);
    }
}
