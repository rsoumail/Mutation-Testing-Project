package fr.istic.m2il.vv.mutator.common;

import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ClassLoaderParserTest {

    @Test
    public void classesFromDirectoryExist() throws Exception {
        ClassLoaderParser classLoaderParser = new ClassLoaderParser();
        TargetProject.getInstance().setLocation(new File(""));
        Assert.assertNotNull(classLoaderParser.getClassesFromDirectory(TargetProject.getInstance().getClassesLocation().getAbsolutePath()));
    }

    @Test
    public void classFromDirectoryDoesNotExist() throws Exception{
        ClassLoaderParser classLoaderParser = new ClassLoaderParser();
        TargetProject.getInstance().setLocation(new File("/bidulle"));
        Assert.assertEquals(0, classLoaderParser.getClassesFromDirectory(TargetProject.getInstance().getClassesLocation().getAbsolutePath()).size());
    }

}