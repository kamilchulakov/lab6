package thread;

import java.util.LinkedList;
import java.util.Queue;

public class AllThreadsDataQueues {

    public static Queue<ClientData> toExecuteQueue = new LinkedList<>();
    public static Queue<ClientData> toWriteQueue = new LinkedList<>();
}
