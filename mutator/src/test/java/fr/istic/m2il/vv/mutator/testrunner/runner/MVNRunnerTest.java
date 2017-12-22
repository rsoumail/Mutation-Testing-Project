package fr.istic.m2il.vv.mutator.testrunner.runner;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MVNRunnerTest {

    MVNRunner mvnRunner;
    @Before
    public void setUp() throws Exception {
        mvnRunner= new MVNRunner(new File("pom.xml").getAbsolutePath(), "surefire:test", "-Dtest=" + TargetClassForTestMutator.class.getName());
        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));
    }

    @After
    public void tearDown() throws Exception {
        mvnRunner = null;
    }

    @Test
    public void run() throws Exception {
        Assert.assertNotNull(mvnRunner.run());
    }

    @Test
    public void gettersAndSetters() throws Exception {
        Assert.assertEquals(new File("").getAbsolutePath() + "/pom.xml", mvnRunner.getPom());
        Assert.assertEquals("surefire:test", mvnRunner.getCommand());
        Assert.assertEquals("-Dtest="+ TargetClassForTestMutator.class.getName(), mvnRunner.getOptions());
    }

}