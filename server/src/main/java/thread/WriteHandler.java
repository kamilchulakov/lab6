package thread;

import logic.InputData;
import logic.OutputData;
import logic.ServerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

public class WriteHandler implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(WriteHandler.class);
    private ClientData clientData;

    public WriteHandler(ClientData clientData) {
        this.clientData = clientData;
    }

    public static void handleWrite(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        ClientData client = (ClientData) key.attachment();
        client.getBuffer().flip();
        InputData inputData = client.getInputData();
        OutputData answer = ServerRunner.getAnswerHandler().execute(inputData);
        if (inputData.getCommandName().equals("history")) answer = new OutputData("Success", client.getCommandHistory());
//        if(answer != null) {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ObjectOutputStream os = new ObjectOutputStream(outputStream);
//            os.writeObject(answer);
//            byte[] replyBytes = outputStream.toByteArray();
//            ByteBuffer buff = ByteBuffer.wrap(replyBytes);
//            channel.send(buff, client.getClientAddress());
//            logger.info("Sent answer " + replyBytes.length + " bytes");
//        }
//        key.interestOps(SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        OutputData answer = clientData.getOutputData();
        if(answer != null) {
            try {
                DatagramChannel channel = clientData.getDatagramChannel();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(outputStream);
                os.writeObject(answer);
                byte[] replyBytes = outputStream.toByteArray();
                ByteBuffer buff = ByteBuffer.wrap(replyBytes);
                channel.send(buff, clientData.getClientAddress());
                logger.info("Sent answer " + replyBytes.length + " bytes");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
