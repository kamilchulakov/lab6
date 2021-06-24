package logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.AllThreadsDataQueues;
import thread.CMDManager;
import thread.ReadHandler;
import thread.WriteHandler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class SelectorManager{
    private static final Selector selector = ServerRunner.getSelector();
    private static final Logger logger = LoggerFactory.getLogger(SelectorManager.class);

    public static void run() {
        ExecutorService readPool = Executors.newCachedThreadPool();
        ForkJoinPool executePool = new ForkJoinPool();
        ExecutorService writePool = Executors.newFixedThreadPool(10);
        while(ServerRunner.isRunning()){
            try {
//                if (selector.select(3000) == 0) {
//                    continue;
//                }
                selector.select(50);
                Set<SelectionKey> keys = selector.selectedKeys();

                for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
                    SelectionKey key = i.next();
                    i.remove();
                    if (key.isValid()) {
                        if (key.isReadable()) {
                            ReadHandler.handleRead(key);
                        }
                    }
                }
                while (!AllThreadsDataQueues.toExecuteQueue.isEmpty()) {
                    executePool.execute(new CMDManager(AllThreadsDataQueues.toExecuteQueue.poll()));
                }
                while (!AllThreadsDataQueues.toWriteQueue.isEmpty()) {
                    writePool.execute(new WriteHandler(AllThreadsDataQueues.toWriteQueue.poll()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
