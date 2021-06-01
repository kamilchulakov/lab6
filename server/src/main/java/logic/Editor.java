package logic;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import henchmen.PropertiesGetter;
import objects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Editor {
    HashMap<String, LabWork> collection;

    public Editor() {
        readCollectionFromConfig();
    }
    public Editor(String filename) {
        readCollectionFromFile(filename);
    }

    private void readCollectionFromFile(String filename) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = Paths.get(filename).toFile();
            file.setReadable(true);
            collection = mapper.readValue(file, collection.getClass());

        } catch (FileNotFoundException e) {
            System.out.println("Invalid filename.");
            System.exit(0);
        } catch (MismatchedInputException e) {
            System.out.println("Invalid json.");
            System.exit(0);
        } catch (Exception ex) {
            readCollectionFromConfig();
        }
    }

    private void readCollectionFromConfig() {
        PropertiesGetter propertiesGetter = new PropertiesGetter();
        try {
            readCollectionFromFile(propertiesGetter.getInputFileName());
        } catch (IOException e) {
            FabricLabWorks labWorks = new FabricLabWorks();
            collection = labWorks.getTestingMaterial();
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

    public void removeElementByKey(String key) {
        collection.remove(key);
    }

    public void clear() {
        collection.clear();
    }

    public void removeAllLowerByLabwork(LabWork labWork) {
        ArrayList<String> toDeleteKeys = new ArrayList<>();
        for (String key: collection.keySet()) {
            if (collection.get(key).compareTo(labWork) < 0) toDeleteKeys.add(key);
        }
        for (String key3: toDeleteKeys) collection.remove(key3);
    }

    public String getAverageMinimalPoint() {
        long result = 0;
        if (collection.size() == 0) return String.valueOf(result);
        for (LabWork labWork: collection.values()) result += labWork.getMinimalPoint();
        return String.valueOf(result / collection.size());
    }

    public String getDescendingDifficulty() {
        ArrayList<Difficulty> difficulties = new ArrayList<>();
        for (LabWork labwork: collection.values()
             ) {
            difficulties.add(labwork.getDifficulty());
        }
        difficulties.sort(new DifficultyComparator());
        StringBuilder result = new StringBuilder();
        for (Difficulty difficulty: difficulties) result.append(difficulty.toString()).append(" ");
        return result.toString();
    }

    public String removeByDiscipline(Discipline discipline) {
        Stream<String> stream = collection.keySet().stream().filter(
                s -> collection.get(s).getDiscipline().equals(discipline));
        Optional<String> optional = stream.findFirst();
        if (optional.isPresent()) {
            collection.remove(optional.get());
            return "Successfully removed element.";
        };
        return "No matches.";
    }

    public void update(int id, LabWork labWork) {
        boolean notFound = true;
        for (String key: collection.keySet()) {
            LabWork labWork1 = collection.get(key);
            if (labWork1.getId() == id) {
                labWork1.copyFromLabwork(labWork);
                notFound = false;
            }
        }
        if (notFound) throw new NoSuchElementException();

    }

    public void insert(String key, LabWork labwork) {
        collection.put(key, labwork);
    }

    public void save(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        File file = Paths.get(filename).toFile();
        file.setWritable(true);
        writer.writeValue(new FileOutputStream(file), collection);
    }

    public void removeIfLower(String key, LabWork labWork) {
        if (collection.get(key).compareTo(labWork) < 0) collection.remove(key);
    }
}
