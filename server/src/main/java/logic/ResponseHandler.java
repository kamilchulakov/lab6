package logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ResponseHandler {
    public static void send(OutputData answer, InetAddress IPAddress, int port) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(answer);
            byte[] replyBytes = outputStream.toByteArray();
            DatagramPacket replyPacket = new DatagramPacket(replyBytes, replyBytes.length, IPAddress, port);
            ServerRunner.getSocket().send(replyPacket);
            //userLogger.info(IPAddress + ":" + port + " send answer " + replyBytes.length + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
