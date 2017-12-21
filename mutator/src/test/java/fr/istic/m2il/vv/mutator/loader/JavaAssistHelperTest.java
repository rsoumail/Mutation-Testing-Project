package fr.istic.m2il.vv.mutator.loader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.istic.m2il.vv.mutator.common.CheckConfigurtionProperties;
import fr.istic.m2il.vv.mutator.common.TimeWatch;
import fr.istic.m2il.vv.mutator.config.ConfigOption;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.ClassPool;
import javassist.Loader;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class JavaAssistHelperTest {

	ClassPool pool;
	Loader loader;
	CustomTranslator translator;
	TargetProject targetProject;
	List<Class<?>> classes;
	List<Class<?>> tests;
	File fileMocked;
	JavaAssistHelper javaHelp;

	@Before
	public void setUp() throws Exception {
		pool = new ClassPool();
		loader = new Loader();
		//Class<Loader> cl = mock(Class.class);
		translator = new CustomTranslator();
		javaHelp = JavaAssistHelper.getInstance();
		targetProject = TargetProject.getInstance();
		classes = new ArrayList<>();
		tests = new ArrayList<>();
        classes.add(ConfigOption.class);
        tests.add(TimeWatch.class);
        targetProject.setClasses(classes);
        targetProject.setTests(tests);
        //fileMocked = mock(File.class);
        targetProject.setLocation(new File("../input"));
        targetProject.setClassesLocation(new File("../input/target/classes"));
        targetProject.setTestsLocation(new File("../input/target/test-classes"));
    }

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getInstance() throws Exception {
	}

	@Test
	public void getPool() throws Exception {
	}

	@Test
	public void initPool() throws Exception {

		Assert.assertNotNull(javaHelp.getPool().get("fr.istic.m2il.vv.input.Addition"));
	}

}