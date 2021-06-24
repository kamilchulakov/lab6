package thread;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AllThreadsDataQueues {

    public static Queue<ClientData> toExecuteQueue = new ConcurrentLinkedQueue<>();
    public static Queue<ClientData> toWriteQueue = new ConcurrentLinkedQueue<>();
}
