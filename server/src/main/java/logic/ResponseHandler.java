package logic;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ResponseHandler {
    static Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    public static void send(OutputData answer, InetAddress IPAddress, int port) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(answer);
            byte[] replyBytes = outputStream.toByteArray();
            DatagramPacket replyPacket = new DatagramPacket(replyBytes, replyBytes.length, IPAddress, port);
            ServerRunner.getSocket().send(replyPacket);
            logger.info(IPAddress + ":" + port + " send answer " + replyBytes.length + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
