package henchmen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesGetter {
    public String getInputFileName() throws IOException {
        Properties prop = getProperties();

        return prop.getProperty("infile");
    }
    public String getOutputFilename() throws IOException {
        Properties prop = getProperties();
        return prop.getProperty("outfile");
    }
    private Properties getProperties() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
        }
        inputStream.close();
        return prop;
    }
}
