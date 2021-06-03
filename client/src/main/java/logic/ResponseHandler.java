package logic;

import interfaces.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;

public class ResponseHandler extends Thread{

    RequestHandler requestHandler;
    UI ui;
    private Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public ResponseHandler(RequestHandler requestHandler, UI ui2) {
        this.requestHandler = requestHandler;
        ui = ui2;
    }

    @Override
    public void run() {
        while(requestHandler.isRunnable()) {
            if (requestHandler.getChannel().isOpen()) {
                try {
                    byte[] incomingData = new byte[65515];
                    ByteBuffer byteBuffer = ByteBuffer.wrap(incomingData);
                    requestHandler.getChannel().receive(byteBuffer);
                    byteBuffer.flip();
                    ByteArrayInputStream in = new ByteArrayInputStream(incomingData);
                    ObjectInputStream is = new ObjectInputStream(in);
                    OutputData answer = (OutputData) is.readObject();
                    logger.info("OutputData has been received.");
                    logger.warn(answer.getResultMessage());
                    if (answer.getResultMessage().equals("connected")) {
                        requestHandler.setConnected(true);
                        System.out.println("Connection with server has been established!");
                        logger.info("Connection with server has been established!");
                        if(requestHandler.getLastCommand()!= null) {
                            //System.out.println("Client resent command!");
                            requestHandler.send(requestHandler.getLastCommand());
                        }
                    } else {
                        requestHandler.setLastCommand(null);
                        ui.display(answer);
                    }
                } catch (PortUnreachableException e) {
                    ui.display("Error","Не удалось подключиться к серверу!\nПопытка подключиться к серверу через 3 секунды");
                    try {
                        Thread.sleep(1000);
                        System.out.print(".\n");
                        Thread.sleep(1000);
                        System.out.print(".\n");
                        Thread.sleep(1000);
                        System.out.print(".\n");
                        Thread.sleep(1000);
                        requestHandler.connect();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    ui.display("Error","Не удалось определить класс, возможно что-то с сериализацией данных или недостаток размера буфера!");
                    requestHandler.setRunnable(false);
                } catch (StreamCorruptedException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
