package fr.istic.m2il.vv.mutation.report;

import fr.istic.m2il.vv.mutation.Utils;
import fr.istic.m2il.vv.mutation.testrunner.runner.MVNRunner;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.File;

public class PITRunner {

    String command;

    public PITRunner() {
        ClassLoader classLoader = getClass().getClassLoader();
        File applicationProperties = new File(classLoader.getResource("application.properties").getFile());
        command = "-Dpit.target.classes="+ Utils.loadPropertiesFile(applicationProperties).getProperty("pit.target.classes") + " -Dpit.target.tests="+Utils.loadPropertiesFile(applicationProperties).getProperty("pit.target.tests") + " org.pitest:pitest-maven:mutationCoverage" ;
    }

    public void run() throws MavenInvocationException {
        MVNRunner runner = new MVNRunner( new File("pom.xml").getAbsolutePath(), this.command);
        runner.run();
    }
}
