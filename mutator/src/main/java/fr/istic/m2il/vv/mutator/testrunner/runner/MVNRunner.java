package fr.istic.m2il.vv.mutator.testrunner.runner;

import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.util.Utils;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MVNRunner {

    private String pom;
    private String command;
    private InvocationRequest request;
    private String options;

    public MVNRunner(String pom, String command, String options) {
        this.pom = pom;
        this.command = command;
        this.options = options;
        request = new DefaultInvocationRequest();
        request.setPomFile( new File( this.pom ) );
        request.setGoals( Arrays.asList( this.command ) );
        request.setMavenOpts(options);
    }

    public InvocationResult run() throws MavenInvocationException, IOException {
        Invoker invoker = new DefaultInvoker();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("application.properties").getFile());
        invoker.setMavenHome(new File(ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("maven.home")));
        return invoker.execute( this.request );
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
