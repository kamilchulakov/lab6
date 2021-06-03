package henchmen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesGetter {
    public static String getInputFileName() throws IOException {
        return "collection.json";
    }
    public static String getOutputFilename() throws IOException {
        return "collection2.json";
    }
}
