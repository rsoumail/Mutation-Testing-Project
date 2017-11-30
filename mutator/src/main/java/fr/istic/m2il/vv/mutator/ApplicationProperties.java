package fr.istic.m2il.vv.mutator;

import java.io.File;

public class ApplicationProperties {

    private File applicationPropertiesFile;

    public ApplicationProperties(){
        System.out.println("OICI");
        ClassLoader classLoader = getClass().getClassLoader();
        System.out.println();
        this.applicationPropertiesFile = new File(classLoader.getResource("application.properties").getFile());
        System.out.println("File "+this.applicationPropertiesFile.getAbsolutePath());
    }

    public File getApplicationPropertiesFile() {
        return applicationPropertiesFile;
    }

    public void setApplicationPropertiesFile(File applicationPropertiesFile) {
        this.applicationPropertiesFile = applicationPropertiesFile;
    }
}
