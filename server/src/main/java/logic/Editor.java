package logic;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import database.DatabaseService;
import objects.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Editor {
    static HashMap<String, LabWork> collection;
    DatabaseService databaseService;

    public Editor() {
        readCollectionFromDatabase();
    }

    private void readCollectionFromDatabase() {
            try {
                databaseService = DatabaseService.getInstance();
                collection = DatabaseService.getInstance().getCollection();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
    }

    public HashMap<String, LabWork> getCollection() {
        return collection;
    }

    public void setCollection(HashMap<String, LabWork> collection) {
        this.collection = collection;
    }

    public String getStringCollection() {
        return collection.toString();
    }
    public String getNiceLookingString() {
        if (collection.isEmpty()) {
            return "collection is clear, so theres no objects to show their fuel type ¯\\_(ツ)_/¯";
        } else {
            String template = "%5s %8s %8s %8s %20s %23s %12s %13s %30s\n";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String.format(template,
                    "key", "id", "name", "author", "coords", "creation_date", "min_point", "difficulty", "discipline")));
            collection.keySet().stream().forEach(key ->stringBuilder.append(String.format(template,key, collection.get(key).getId(),
                    collection.get(key).getName(), collection.get(key).getAuthor(), collection.get(key).getCoordinates(),
                    collection.get(key).getCreationDate().toString(), collection.get(key).getMinimalPoint(), collection.get(key).getDifficulty(),
                    collection.get(key).getDiscipline())));
            return stringBuilder.toString();
        }
    }

    public void removeElementByKey(String key, String auth) {
        try {
            databaseService.remove(key, auth);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void clear() {
        try {
            databaseService.clear();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void removeAllLowerByLabwork(LabWork labWork) throws SQLException {
        databaseService.removeAllLower(labWork);
    }

    public String getAverageMinimalPoint() {
        long result = 0;
        if (collection.size() == 0) return String.valueOf(result);
        result = collection.values().stream().mapToLong(LabWork::getMinimalPoint).sum();
        return String.valueOf(result / collection.size());
    }

    public String getDescendingDifficulty() {
        //collection.values().stream().collect(Collectors.toList());
        //ArrayList<Difficulty> difficulties = new ArrayList<>();
        //collection.values().forEach(s -> difficulties.add(s.getDifficulty()));
        //difficulties.sort(new DifficultyComparator());
        StringBuilder result = new StringBuilder();
        collection.values().stream().map(LabWork::getDifficulty).sorted(new DifficultyComparator()).forEach(s->result.append(s.toString()).append(" "));
        //difficulties.forEach(difficulty -> result.append(difficulty.toString()).append(" "));
        return result.toString();
    }

    public String removeByDiscipline(Discipline discipline, String user) {
        try {
            databaseService.removeByDiscipline(discipline, user);
            return "REMOVED";
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return "Tried to remove.";
    }

    public void update(int id, LabWork labWork) throws SQLException {
        databaseService.update(id, labWork);
    }

    public void insert(String key, LabWork labwork) throws SQLException {
        if (collection.containsKey(key)) throw new NoSuchElementException();
        databaseService.insert(key, labwork);
        readCollectionFromDatabase();
        System.out.println(collection);
    }
    
    public void save() {

    }

    public String addUser(String user, String pass) throws SQLException {
        databaseService.addUser(pass, user);
        return "All good: user add";
    }

    public boolean userExists(String user, String pass) throws SQLException {
        return databaseService.checkUser(user, pass);
    }

    public void clear(String auth) throws SQLException {
        databaseService.user_clear(auth);
    }
}
