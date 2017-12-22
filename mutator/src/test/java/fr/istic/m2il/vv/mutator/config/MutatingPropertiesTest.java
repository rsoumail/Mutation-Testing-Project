package fr.istic.m2il.vv.mutator.config;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MutatingPropertiesTest {
    @Before
    public void setUp() throws Exception {
        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));
        ApplicationProperties.getInstance().getApplicationPropertiesFile().setProperty("mutators", "");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mutantsToAnalysisNotEmptyWithMutatorPropertyEmpty() throws Exception {
        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));
        ApplicationProperties.getInstance().getApplicationPropertiesFile().setProperty("mutators", "");
        Assert.assertEquals(4, MutatingProperties.mutantsToAnalysis().size());
    }

    @Test
    public void mutantsToAnalysisNotEmptyWithMutatorPropertyNotEmpty() throws Exception {
        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));
        ApplicationProperties.getInstance().getApplicationPropertiesFile().setProperty("mutators", "ARITHMETIC_MUTATOR");
        Assert.assertEquals(1, MutatingProperties.mutantsToAnalysis().size());
    }

    @Test
    public void mutantsToAnalysisNotEmptyWithMutatorPropertyNul() throws Exception {
        ApplicationProperties.getInstance(new File("src/main/resources/test.properties"));
        Assert.assertEquals(4, MutatingProperties.mutantsToAnalysis().size());
    }

}