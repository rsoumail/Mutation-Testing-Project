package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.CustomTranslator;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.util.Utils;
import javassist.ClassPool;
import javassist.Loader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanMethodMutatorTest {

    ApplicationProperties applicationProperties;
    ClassLoaderParser classLoaderParser;
    TargetProject targetProjectMocked;
    JavaAssistHelper javaAssistHelperMocked;
    MutatorExecutor mutatorExecutorMocked;
    MutatorExecutorHelper mutatorExecutorHelperMocjed;

    @Before
    public void setUp() throws Exception {
        applicationProperties = mock(ApplicationProperties.class);
        //when(applicationProperties.getApplicationPropertiesFile()).thenReturn((new File("")));

        classLoaderParser = new ClassLoaderParser();


        targetProjectMocked = mock(TargetProject.class);
        javaAssistHelperMocked = mock(JavaAssistHelper.class);
        javaAssistHelperMocked.setPool(mock(ClassPool.class));
        javaAssistHelperMocked.setLoader(mock(Loader.class));
        javaAssistHelperMocked.setTranslator(mock(CustomTranslator.class));
        javaAssistHelperMocked.setTargetProject(targetProjectMocked);
        /*mutatorExecutorMocked = mock(MutatorExecutor.class);
        //when(mutatorExecutorMocked.getJavaAssistHelper()).thenReturn(javaAssistHelperMocked);


        when(targetProjectMocked.getLocation()).thenReturn(new File(Utils.loadPropertiesFile(applicationProperties.getApplicationPropertiesFile()).getProperty("target.project")));
        when(targetProjectMocked.getPom()).thenReturn(new File(targetProjectMocked.getLocation().getAbsolutePath() + "/pom.xml"));
//		 when(targetProjectMocked.getClass()).thenReturn(classLoaderParser.getClassesFromDirectory(targetProjectMocked.getLocation().getAbsolutePath()));
//
//
//         targetProject.setTests(classLoaderParser.getClassesFromDirectory(targetProject.getTestsLocation().getAbsolutePath()));
//
//         javaAssistHelper = new JavaAssistHelper(new ClassPool() , new Loader(), new CustomTranslator(),targetProject);
//         mutatorExecutorHelper = new MutatorExecutorHelper();
//         mutatorExecutor = new MutatorExecutor(javaAssistHelper);*/
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mutate() throws Exception {
    }

    @Test
    public void revert() throws Exception {
    }

}