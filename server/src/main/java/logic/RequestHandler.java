package logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.DatagramPacket;

public class RequestHandler implements Runnable{
    //private static final Logger userLogger = LogManager.getLogger(RequestHandler.class);
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    @Override
    public void run() {
        while(ServerRunner.isRunning()) {
            try {
                byte[] incomingData = new byte[65515];
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                ServerRunner.getSocket().receive(incomingPacket);
                logger.info(incomingPacket.getAddress() + ":" + incomingPacket.getPort() + " received packet " + incomingPacket.getLength() + " bytes");
            } catch (IOException e) {
                logger.error("Had some tromblems with reading packet!");
            }
        }
    }
}
