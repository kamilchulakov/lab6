package henchmen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesGetter {
    public String getInputFileName(){
        return "collection.json";
    }
    public static String getOutputFilename(){
        return "collection.json2";
    }
}
