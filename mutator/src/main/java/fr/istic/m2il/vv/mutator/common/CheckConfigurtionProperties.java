package fr.istic.m2il.vv.mutator.common;

import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.config.ConfigOption;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;

import java.io.File;

import static java.lang.System.exit;

public class CheckConfigurtionProperties {

    private ApplicationProperties applicationProperties;
    private Integer existStatus;

    public CheckConfigurtionProperties(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    public Integer checksConfig(){
        existStatus = new Integer(0);
        this.checksProperties();
        this.checkFileExist(TargetProject.getInstance().getLocation());
        this.checkFileExist(TargetProject.getInstance().getPom());
        this.checkFileExist(TargetProject.getInstance().getClassesLocation());
        this.checkFileExist(TargetProject.getInstance().getTestsLocation());
        this.checksClassesAndTestsExist(TargetProject.getInstance());
        return existStatus;
    }

    public void checksProperties(){
        existStatus = new Integer(-1);
        for(ConfigOption propertie: ConfigOption.values()){
            if(!propertie.equals(ConfigOption.MUTATORS))
                checkPropertie(propertie.toString());
        }
        existStatus = new Integer(0);
    }

    public void checkFileExist(File file){
        existStatus = new Integer(-1);
        if(!file.exists()){
            System.err.println("Vérifier que le fichier/dossier " + file.getPath() +" existe");
            exit(0);
        }
        existStatus = new Integer(0);
    }

    private void checkPropertie(String propertie){
        existStatus = new Integer(-1);
        if(applicationProperties.getApplicationPropertiesFile().getProperty(propertie) == null){
            System.err.println("Veuillez indiquer la propriété " + propertie + " dans le fichier application properties");
            exit(0);
        }
        existStatus = new Integer(0);
    }

    public void checksClassesAndTestsExist(TargetProject targetProject){
        existStatus = new Integer(-1);
        if(targetProject.getClasses().size() == 0){
            System.out.println();
            System.err.println(" Le projet cible ne contient aucun fichier .class");
            exit(0);
        }
        if(targetProject.getTests().size() == 0){
            System.out.println();
            System.err.println(" Le projet cible ne contient aucun fichier .class de tests");
            exit(0);
        }
        existStatus = new Integer(0);
    }
}
