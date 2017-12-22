package fr.istic.m2il.vv.mutator.loader;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.ClassPool;
import javassist.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class JavaAssistHelperTest {


	TargetProject targetProject;
	JavaAssistHelper javaAssistHelper;

	@Before
	public void setUp() throws Exception {
		targetProject = TargetProject.getInstance();
		targetProject.setLocation(new File(""));
		targetProject.setClassesLocation(new File("target/classes"));
		targetProject.setTestsLocation((new File("target/test-classes")));
		targetProject.setPom(new File("pom.xml"));
		List<Class<?>> listclass = new ArrayList<>();
		listclass.add(TargetClassForTestMutator.class);
		List<Class<?>> listtest = new ArrayList<>();
		listtest.add(TargetClassForTestMutatorTest.class);
		targetProject.setClasses(listclass);
		targetProject.setTests(listtest);
		javaAssistHelper = JavaAssistHelper.getInstance();
    }

	@After
	public void tearDown() throws Exception {
		targetProject = null;
		javaAssistHelper = null;
	}

	@Test
	public void getPool() throws Exception {
		javaAssistHelper = new JavaAssistHelper(new ClassPool(), new Loader(), new CustomTranslator(), TargetProject.getInstance());
		Assert.assertNotNull(javaAssistHelper.getPool().get("fr.istic.m2il.vv.mutator.TargetClassForTestMutator"));
	}
}