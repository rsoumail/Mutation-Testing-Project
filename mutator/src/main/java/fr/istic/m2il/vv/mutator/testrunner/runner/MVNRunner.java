package fr.istic.m2il.vv.mutator.testrunner.runner;

import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class MVNRunner implements Callable<InvocationResult>{

    private String pom;
    private String command;
    private InvocationRequest request;
    private String options;
    private InvocationResult invocationResult;

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
        invoker.setMavenHome(new File(ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("maven.home")));
        this.invocationResult = invoker.execute( this.request );
        return this.invocationResult;
    }

    public String getPom() {
        return pom;
    }

    public String getCommand() {
        return command;
    }


    public String getOptions() {
        return options;
    }



    public InvocationResult getInvocationResult() {
        return invocationResult;
    }

    @Override
    public InvocationResult call() throws Exception {
        return this.run();
    }
}
