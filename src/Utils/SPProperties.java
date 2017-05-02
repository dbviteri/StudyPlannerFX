package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class loads the properties file and keeps it in memory.
 * Its constructor accepts a prefix, which will correspond to the prefix in the studyplanner file
 * E.g. studyplannerfx.jdbc
 * Created by Didac on 30/04/2017.
 */
public class SPProperties {
    // Constants -------------------------------------------------------------------------------------------------------
    private static final String FILE_PROPERTIES = "studyplanner.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fileProperties = classLoader.getResourceAsStream(FILE_PROPERTIES);

        if(fileProperties == null){
            throw new SPException("Properties file is missing");
        }

        try{
            PROPERTIES.load(fileProperties);
        } catch(IOException e) {
            throw new SPException("Can't load properties file: " + e);
        }
    }

    // Variables -------------------------------------------------------------------------------------------------------

    private String prefix;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Constructs an instance of the properties file.
     * @param prefix To be used as prefix for the properties. E.g: xdn15mcu_studyplanner.jdbc.url
     *                                                             ^^^^^^^^^^^^^^^^^^^^^^^^^^
     */
    public SPProperties(String prefix){
        this.prefix = prefix;
    }

    // Methods  --------------------------------------------------------------------------------------------------------

    public String getProperty(String propertyWanted){
        String fullProperty = prefix + "." + propertyWanted;
        String property = PROPERTIES.getProperty(fullProperty);

        return property;
    }
}
