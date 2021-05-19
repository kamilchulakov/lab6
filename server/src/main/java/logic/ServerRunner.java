package logic;
import interfaces.CLI;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class ServerRunner {
    private static int PORT = 26262;
    private static boolean running;
    private static String path;
    private static DatagramSocket socket;
    private static RequestHandler requestHandler = new RequestHandler();
    private static ResponseHandler responseHandler = new ResponseHandler();
    private static CMDManager answerHandler;
    //static final Logger userLogger = LogManager.getLogger(UDPSocketServer.class);
    private static Selector selector;
    public ServerRunner(String path) {
        this.path = path;
    }

    public void start() {
        if(running) {
            //userLogger.error("Сервер уже запущен!");
        } else {
            try {
                running = true;
                DatagramChannel datagramChannel = DatagramChannel.open();
                datagramChannel.configureBlocking(false);
                datagramChannel.bind(new InetSocketAddress(PORT));
                socket = datagramChannel.socket();
                selector = Selector.open();
                answerHandler = new CMDManager(path);
                new Thread(new CLI()).start();
                datagramChannel.register(selector, SelectionKey.OP_READ, new ClientData());
                System.out.println("Сервер слушает " + PORT + " порт");
                SelectorManager.run();
            } catch (SocketException e) {
                System.err.println("Ошибка сокета!");
            } catch (ClosedChannelException e) {
                System.err.println("Канал закрыт!");
            } catch (IOException e) {
                System.err.println("Произошла ошибка при запуске сервера!");
            }
        }
    }

    public static void stop() {
        try {
            answerHandler.getCollection().save(path);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при сохранении файла!");
        }
        System.out.println("Программа завершена по требованию пользователя!");
        running = false;
    }

    public static DatagramSocket getSocket() {
        return socket;
    }

    public static RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public static ResponseHandler getResponseHandler() {
        return responseHandler;
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
