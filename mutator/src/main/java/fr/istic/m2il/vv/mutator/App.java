package fr.istic.m2il.vv.mutator;

import fr.istic.m2il.vv.mutator.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.javassistloader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.javassistloader.CustomTranslator;
import fr.istic.m2il.vv.mutator.mutant.*;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;


import fr.istic.m2il.vv.mutator.util.Utils;
import javassist.*;

import java.io.File;

import static java.lang.System.exit;

public class App {



    public static void main(String[] args) throws Exception {

        ApplicationProperties applicationProperties = new ApplicationProperties();

        if(Utils.loadPropertiesFile(applicationProperties.getApplicationPropertiesFile()).getProperty("target.project").isEmpty()){
            System.err.println("Veuillez indiquer la propriété targetproject dans le fichier application properties");
            exit(0);
        }
        else {

            ClassLoaderParser classLoaderParser = new ClassLoaderParser();

            TargetProject targetProject = new TargetProject();
            targetProject.setLocation(new File(Utils.loadPropertiesFile(applicationProperties.getApplicationPropertiesFile()).getProperty("target.project")));
            targetProject.setPom(new File(targetProject.getLocation().getAbsolutePath() + "/pom.xml"));
            targetProject.setClasses(classLoaderParser.getClassesFromDirectory(targetProject.getClassesLocation().getAbsolutePath()));
            targetProject.setTests(classLoaderParser.getClassesFromDirectory(targetProject.getTestsLocation().getAbsolutePath()));

            JavaAssistHelper javaAssistHelper = new JavaAssistHelper(new ClassPool() , new Loader(), new CustomTranslator(),targetProject);

            MutatorExecutorHelper mutatorExecutorHelper = new MutatorExecutorHelper();
            MutatorExecutor mutatorExecutor = new MutatorExecutor(javaAssistHelper);
            for(MutantType mutant: MutantType.values()){
                Mutator mutator = (Mutator) mutatorExecutorHelper.getInstanceOf(mutant, targetProject);
                mutatorExecutor.execute(mutator, targetProject);

            }


            /*PITRunner pitRunner = new PITRunner();
            pitRunner.run();*/
        }
    }

}
