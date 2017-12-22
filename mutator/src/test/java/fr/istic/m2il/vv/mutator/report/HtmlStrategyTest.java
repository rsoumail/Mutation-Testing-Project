package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.mutant.Mutator;
import fr.istic.m2il.vv.mutator.mutant.VoidMethodMutator;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.CtMethod;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HtmlStrategyTest {

    TargetProject targetProject;
    CtMethod ctMethod;
    JavaAssistHelper javaAssistHelper;
    Mutator mutator;
    Method original;
    Method[] methods;

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
        targetProject.setTests(listtest);
        targetProject.setClasses(listclass);
        javaAssistHelper = JavaAssistHelper.getInstance();

        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));

        methods = TargetClassForTestMutator.class.getDeclaredMethods();
        for(Method m: methods){
            if(m.getName().equals("voidMethod")){
                original = m;
            }
        }

        ctMethod = javaAssistHelper.getPool().get("fr.istic.m2il.vv.mutator.TargetClassForTestMutator").getDeclaredMethod(original.getName());
        mutator = new VoidMethodMutator(targetProject);
        mutator.mutate(ctMethod);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void executeReportDirPropertyNotNullAndTrue() throws Exception {
        ApplicationProperties.getInstance().getApplicationPropertiesFile().setProperty("report.timestamped", "true");
        ReportService.getInstance().setReportStrategy(new HtmlStrategy());
        ReportService.getInstance().toGraphicReport();
        Assert.assertTrue(new File(ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.dir")).getName().contains("report-*.html"));
    }

    @Test
    public void executeReportDirPropertyNotNullAndFalse() throws Exception {
        ApplicationProperties.getInstance().getApplicationPropertiesFile().setProperty("report.timestamped", "false");
        ReportService.getInstance().setReportStrategy(new HtmlStrategy());
        ReportService.getInstance().toGraphicReport();
        Assert.assertNotNull(new File(ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.dir")));
    }

    @Test
    public void executeReportDirPropertyNull() throws Exception {
        ApplicationProperties.getInstance().getApplicationPropertiesFile().setProperty("report.timestamped", "");
        ReportService.getInstance().setReportStrategy(new HtmlStrategy());
        ReportService.getInstance().toGraphicReport();
        System.out.println(ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.dir"));
        Assert.assertNotNull(new File(ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.dir") + "/report.html"));
    }

}