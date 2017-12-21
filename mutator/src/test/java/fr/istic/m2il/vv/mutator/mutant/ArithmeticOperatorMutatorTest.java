package fr.istic.m2il.vv.mutator.mutant;

import java.io.File;
import java.util.List;

import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.istic.m2il.vv.mutator.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.CustomTranslator;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.util.Utils;
import javassist.ClassPool;
import javassist.Loader;

import static org.mockito.Mockito.*;

public class ArithmeticOperatorMutatorTest {

    ApplicationProperties applicationProperties;
	ClassLoaderParser classLoaderParser;


	TargetProject targetProject;
	MVNRunner mvnRunner;
    File fileMocked;
    Mutator mutator;
	
	/* Jeu de données */
	List<Class<?>> classes;
	
    @Before
    public void setUp() throws Exception {
 fileMocked = mock(File.class);

        applicationProperties = ApplicationProperties.getInstance();
        targetProject = TargetProject.getInstance();
        targetProject = TargetProject.getInstance();
        targetProject.setLocation(fileMocked);
        targetProject.setClassesLocation(fileMocked);
        targetProject.setPom(fileMocked);
        targetProject.setTestsLocation(fileMocked);
    	 
    	 //classLoaderParser = new ClassLoaderParser();

        mutator = new ArithmeticOperatorMutator(targetProject);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mutate() throws Exception {
    	//Mutator mutator = (Mutator) mutatorExecutorHelper.getInstanceOf(MutantType.ArithmeticOperatorMutator, targetProject);
      //  mutatorExecutor.execute(mutator, targetProject);
        
    }

    @Test
    public void revert() throws Exception {
    }

}