package fr.istic.m2il.vv.mutator.testrunner.runner;

import fr.istic.m2il.vv.mutator.Utils;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Arrays;

public class MVNRunner {

    private String pom;
    private String command;
    private InvocationRequest request;

    public MVNRunner(String pom, String command) {
        this.pom = pom;
        this.command = command;
        request = new DefaultInvocationRequest();
        request.setPomFile( new File( this.pom ) );
        request.setGoals( Arrays.asList( this.command ) );
    }

    public void run() throws MavenInvocationException {
        Invoker invoker = new DefaultInvoker();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("application.properties").getFile());
        invoker.setMavenHome(new File(Utils.loadPropertiesFile(file).getProperty("maven.home")));
        invoker.execute( this.request );
    }

    public String getPom() {
        return pom;
    }

    public void setPom(String pom) {
        this.pom = pom;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
