package fr.istic.m2il.vv.mutator.config;


import fr.istic.m2il.vv.mutator.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class ApplicationProperties {

    private static Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);
    private Properties applicationPropertiesFile;
    private static ApplicationProperties instance;

    private ApplicationProperties(File propertieFile) throws IOException {
        logger.info("Loading application.properties");
        this.applicationPropertiesFile = Utils.loadPropertiesFile(propertieFile);
        logger.info("application.properties loaded");
    }

    public static ApplicationProperties getInstance(File propertieFile) throws IOException {
        if(instance == null){
            instance = new ApplicationProperties(propertieFile);
        }
        return instance;
    }

    public static ApplicationProperties getInstance() throws IOException {
        return instance;
    }

    public Properties getApplicationPropertiesFile() {
        return applicationPropertiesFile;
    }

    public void setApplicationPropertiesFile(Properties applicationPropertiesFile) {
        this.applicationPropertiesFile = applicationPropertiesFile;
    }
}
