package fr.istic.m2il.vv.mutator;

import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import org.junit.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void testMainWithoutArgument() throws Exception {
        exit.expectSystemExitWithStatus(0);
        String[] args = new String[0];
        App.main(args);
    }

    @Test
    public void testMainWithArgument() throws Exception{
        exit.expectSystemExitWithStatus(0);
        App.main(new String[]{"src/main/resources/test.properties"});
    }
}