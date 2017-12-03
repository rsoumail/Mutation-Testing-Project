package fr.istic.m2il.vv.mutator.common;

import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.config.ConfigurationProperties;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.util.Utils;

import java.io.File;

import static java.lang.System.exit;

public class CheckConfigurtionProperties {

    private ApplicationProperties applicationProperties;

    public CheckConfigurtionProperties(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    public void checks(){
        for(ConfigurationProperties propertie: ConfigurationProperties.values()){
            if(!propertie.equals(ConfigurationProperties.MUTATORS))
                checkPropertie(propertie.toString());
        }
    }

    public void checkFileExist(File file){
        if(!file.exists()){
            System.err.println("Vérifier que le fichier/dossier " + file.getPath() +" existe");
            exit(0);
        }
    }

    private void checkPropertie(String propertie){
        if(Utils.loadPropertiesFile(applicationProperties.getApplicationPropertiesFile()).getProperty(propertie) == null){
            System.err.println("Veuillez indiquer la propriété " + propertie + " dans le fichier application properties");
            exit(0);
        }
    }

    public void checksClassesAndTestsExist(TargetProject targetProject){
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
    }
}
