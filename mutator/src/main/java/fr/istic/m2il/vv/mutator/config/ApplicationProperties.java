package fr.istic.m2il.vv.mutator.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class ApplicationProperties {

    private static Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);
    private Properties applicationPropertiesFile;
    private static ApplicationProperties instance;

    private ApplicationProperties() throws IOException {
        logger.info("Loading application.properties");

        Properties properties = new Properties();
        properties.load(ApplicationProperties.class.getResourceAsStream("/application.properties"));
        this.applicationPropertiesFile = properties;
        logger.info("application.properties loaded");
    }

    public static ApplicationProperties getInstance() throws IOException {
        if(instance == null){
            instance = new ApplicationProperties();
        }
        return instance;
    }

    public Properties getApplicationPropertiesFile() {
        return applicationPropertiesFile;
    }

    public void setApplicationPropertiesFile(Properties applicationPropertiesFile) {
        this.applicationPropertiesFile = applicationPropertiesFile;
    }
}
