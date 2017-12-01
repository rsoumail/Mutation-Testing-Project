package fr.istic.m2il.vv.mutator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ApplicationProperties {

    private static Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);
    private File applicationPropertiesFile;

    public ApplicationProperties(){

        logger.info("Loading application.properties");
        ClassLoader classLoader = getClass().getClassLoader();
        this.applicationPropertiesFile = new File(classLoader.getResource("application.properties").getFile());
        logger.info("application.properties loaded");
    }

    public File getApplicationPropertiesFile() {
        return applicationPropertiesFile;
    }

    public void setApplicationPropertiesFile(File applicationPropertiesFile) {
        this.applicationPropertiesFile = applicationPropertiesFile;
    }
}
