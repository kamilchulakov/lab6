package henchmen;

import commands.Command;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class CommandHistory {
    private Queue<String> commandsStringQueue = new LinkedList<>();

    public void add(String line) {
        commandsStringQueue.add(line);
    }

    public String getPureHistory(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> itr = commandsStringQueue.iterator();
        int i = 0;
        while (itr.hasNext() & i < number) {
            stringBuilder.append(itr.next().split(" ")[0]).append("\n");
            System.out.print("");
            i++;
        }
        return stringBuilder.toString();
    }

    public String getHistory(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> itr = commandsStringQueue.iterator();
        int i = commandsStringQueue.size() - number;
        while (i > 0) {
            itr.next();
            i--;
        }

        while (itr.hasNext() & i < number) {
            stringBuilder.append(itr.next()).append("\n");
            System.out.print("");
            i++;
        }
        return stringBuilder.toString();
    }
}
