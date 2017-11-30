package fr.istic.m2il.vv.mutator;

import java.io.File;

public class ApplicationProperties {

    private File applicationPropertiesFile;

    public ApplicationProperties(){
        ClassLoader classLoader = MutatorApp.class.getClassLoader();
        this.applicationPropertiesFile = new File(classLoader.getResource("application.properties").getFile());
    }

    public File getApplicationPropertiesFile() {
        return applicationPropertiesFile;
    }

    public void setApplicationPropertiesFile(File applicationPropertiesFile) {
        this.applicationPropertiesFile = applicationPropertiesFile;
    }
}
