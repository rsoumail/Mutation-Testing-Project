package fr.istic.m2il.vv.mutator.common;

import fr.istic.m2il.vv.mutator.config.ApplicationProperties;

import fr.istic.m2il.vv.mutator.config.ConfigOption;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import org.junit.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.*;

public class CheckConfigurtionPropertiesTest {

    File fileMocked;
    ApplicationProperties applicationPropertiesMocked;
    CheckConfigurtionProperties checkConfigurtionProperties;
    TargetProject targetProject;
    Properties propertiesMocked;
    List<Class<?>> classes;


    @Before
    public void setUp() throws Exception {
        fileMocked = mock(File.class);
        when(fileMocked.exists()).thenReturn(true);
        propertiesMocked = mock(Properties.class);
        when(propertiesMocked.getProperty("target.project")).thenReturn("target.project");
        when(propertiesMocked.getProperty("maven.home")).thenReturn("maven.home");
        applicationPropertiesMocked = mock(ApplicationProperties.class);
        when(applicationPropertiesMocked.getApplicationPropertiesFile()).thenReturn(propertiesMocked);
        targetProject = TargetProject.getInstance();
        targetProject.setLocation(fileMocked);
        targetProject.setClassesLocation(fileMocked);
        targetProject.setPom(fileMocked);
        targetProject.setTestsLocation(fileMocked);
        classes = new ArrayList<>();
        classes.add(CheckConfigurtionProperties.class);
        classes.add(ConfigOption.class);
        targetProject.setClasses(classes);
        targetProject.setTests(classes);
        checkConfigurtionProperties = new CheckConfigurtionProperties(applicationPropertiesMocked);
    }

    @After
    public void tearDown() throws Exception {
        checkConfigurtionProperties = null;
        applicationPropertiesMocked = null;
        fileMocked = null;
        targetProject = null;
        propertiesMocked = null;
        classes = null;
    }
    @Test
    public void checksConfig() throws Exception {

        Assert.assertEquals(new Integer(0), checkConfigurtionProperties.checksConfig());
    }

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void fileNotExist(){
        when(fileMocked.exists()).thenReturn(false);
        exit.expectSystemExitWithStatus(0);
        checkConfigurtionProperties.checkFileExist(fileMocked);
        when(fileMocked.exists()).thenReturn(true);
    }
}