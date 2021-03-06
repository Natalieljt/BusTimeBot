package logic;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

/**
 * Controls the keys for various sources utilizes for the bot
 */
public class PropertiesLoader {
    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(PropertiesLoader.class);

    private static final String PROPERTIES_FILE = "key.properties";
    private static final String PROPERTIES_KEY_LTA = "lta";
    private static final String PROPERTIES_KEY_TELEGRAM = "telegram";
    private static final String PROPERTIES_USE_DATABASE = "use_database";

    private String ltaToken;
    private String telegramToken;
    private boolean useDatabase;

    public PropertiesLoader() {
        useDatabase = false;
        try {
            FileInputStream propertiesStream = new FileInputStream(PROPERTIES_FILE);
            Properties properties = new Properties();
            properties.load(propertiesStream);

            useDatabase = Boolean.parseBoolean(properties.getProperty(PROPERTIES_USE_DATABASE, "false"));
            telegramToken = properties.getProperty(PROPERTIES_KEY_TELEGRAM, "");
            ltaToken = properties.getProperty(PROPERTIES_KEY_LTA, "");

            //Close the stream and file as we don't need it anymore
            properties.clear();
            propertiesStream.close();
        } catch (Exception e) {
            logger.fatal("Exception occurred at Constructor of PropertiesLoader", e);
        }
    }

    public String getLtaToken() {
        return ltaToken;
    }

    public String getTelegramToken() {
        return telegramToken;
    }

    public boolean getUseDatabase() {
        return useDatabase;
    }
}
