package logic;
import interfaces.CLI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Arrays;

public class ServerRunner {
    private static int PORT = 26262;
    private static boolean running;
    private static String path;
    private static DatagramSocket socket;
    private static CMDManager answerHandler;
    private static final Logger logger = LoggerFactory.getLogger(ServerRunner.class);
    private static Selector selector;
    public ServerRunner(String path) {
        ServerRunner.path = path;
    }

    public void start() {
        if (running) {
            logger.error("ServerRunner has already started!!");
        } else {
            try {
                logger.info("ServerRunner just started initialization.");
                running = true;
                DatagramChannel datagramChannel = DatagramChannel.open();
                datagramChannel.configureBlocking(false);
                datagramChannel.bind(new InetSocketAddress(PORT));
                socket = datagramChannel.socket();
                selector = Selector.open();
                answerHandler = new CMDManager(path);
                new Thread(new CLI()).start();
                datagramChannel.register(selector, SelectionKey.OP_READ, new ClientData());
                System.out.println("Listening " + PORT + " port");
                logger.info("Everything went fine.");
                SelectorManager.run();
            } catch (SocketException e) {
                logger.error("Check my SOCKET!");
                System.err.println("Check my SOCKET!");
            } catch (ClosedChannelException e) {
                logger.error("Check my CHANNEL! It is closed...");
                System.err.println("Check my CHANNEL! It is closed...");
            } catch (IOException e) {
                logger.error("Server didn't start..."+ Arrays.toString(e.getStackTrace()));
                System.err.println("Server didn't start!");
            }
        }
    }

    public static void stop() {
        try {
            logger.info("Trying to save into file...");
            answerHandler.getCollection().save(path);
        } catch (IOException e) {
            logger.error("Didn't save into file..."+Arrays.toString(e.getStackTrace()));
            System.out.println("Didn't save into file due to IOException!");
        }
        logger.error("Program was stopped by a user!");
        System.out.println("Program was stopped by a user!");
        running = false;
    }

    public static DatagramSocket getSocket() {
        return socket;
    }

    public static CMDManager getAnswerHandler() {
        return answerHandler;
    }

    public static boolean isRunning() {
        return running;
    }

    public static Selector getSelector() {
        return selector;
    }
}
