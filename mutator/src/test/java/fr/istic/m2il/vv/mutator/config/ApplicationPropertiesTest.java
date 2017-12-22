package fr.istic.m2il.vv.mutator.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ApplicationPropertiesTest {

    @Before
    public void setUp() throws Exception {
        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));
    }

    @Test
    public void getApplicationPropertiesFile() throws Exception {
        Assert.assertNotNull(ApplicationProperties.getInstance());
    }
}