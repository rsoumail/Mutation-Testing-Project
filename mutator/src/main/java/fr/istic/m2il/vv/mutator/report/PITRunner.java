package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.util.Utils;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.File;

public class PITRunner {

    String command;

    public PITRunner() {
        ClassLoader classLoader = getClass().getClassLoader();
        File applicationProperties = new File(classLoader.getResource("application.properties").getFile());
        command = "-Dpit.targetproject.classes="+ Utils.loadPropertiesFile(applicationProperties).getProperty("pit.target.classes") + " -Dpit.targetproject.tests="+Utils.loadPropertiesFile(applicationProperties).getProperty("pit.target.tests") + " org.pitest:pitest-maven:mutationCoverage" ;
    }

    public void run() throws MavenInvocationException {
        MVNRunner runner = new MVNRunner( new File("pom.xml").getAbsolutePath(), this.command, "");
        runner.run();
    }
}
