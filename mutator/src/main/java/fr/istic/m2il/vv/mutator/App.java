package fr.istic.m2il.vv.mutator;

import fr.istic.m2il.vv.mutator.common.CheckConfigurtionProperties;
import fr.istic.m2il.vv.mutator.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutator.common.TimeWatch;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.config.MutatingProperties;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.loader.CustomTranslator;
import fr.istic.m2il.vv.mutator.mutant.*;
import fr.istic.m2il.vv.mutator.report.ReportService;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;


import fr.istic.m2il.vv.mutator.util.Utils;
import javassist.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class App {



    public static void main(String[] args) throws Exception {

        ApplicationProperties applicationProperties = ApplicationProperties.getInstance();

        /*if(!applicationProperties.getApplicationPropertiesFile()){
            System.err.println("Impossible de trouver le fichier application.properties");
            exit(0);
        }*/
        CheckConfigurtionProperties cheker = new CheckConfigurtionProperties(applicationProperties);
        cheker.checks();
        ClassLoaderParser classLoaderParser = new ClassLoaderParser();

        TargetProject targetProject = new TargetProject();
        TimeWatch watcher = TimeWatch.start();

        targetProject.setLocation(new File(applicationProperties.getApplicationPropertiesFile().getProperty("target.project")));

        cheker.checkFileExist(new File(targetProject.getLocation().getAbsolutePath() + "/pom.xml"));
        cheker.checkFileExist(targetProject.getClassesLocation());
        cheker.checkFileExist(targetProject.getTestsLocation());
        targetProject.setPom(new File(targetProject.getLocation().getAbsolutePath() + "/pom.xml"));

        targetProject.setClasses(classLoaderParser.getClassesFromDirectory(targetProject.getClassesLocation().getPath()));
        targetProject.setTests(classLoaderParser.getClassesFromDirectory(targetProject.getTestsLocation().getPath()));

        cheker.checksClassesAndTestsExist(targetProject);

        JavaAssistHelper javaAssistHelper = JavaAssistHelper.getInstance(new ClassPool() , new Loader(), new CustomTranslator(),targetProject);



        ReportService.getInstance().setScanClassesTime(watcher.time(TimeUnit.SECONDS));

        watcher.reset();

        MutatorExecutorHelper mutatorExecutorHelper = new MutatorExecutorHelper();
        MutatorExecutor mutatorExecutor = new MutatorExecutor(javaAssistHelper);
        for(MutantType mutant: MutatingProperties.mutantsToAnalysis()){
            Mutator mutator = (Mutator) mutatorExecutorHelper.getInstanceOf(mutant, targetProject);
            mutatorExecutor.execute(mutator, targetProject);

        }

        ReportService.getInstance().setRunMutationAnalysisTime(watcher.time(TimeUnit.SECONDS));

        ReportService.getInstance().doReport();
    }

}
