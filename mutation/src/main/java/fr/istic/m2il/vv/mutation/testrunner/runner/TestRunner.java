package fr.istic.m2il.vv.mutation.testrunner.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestRunner implements ITestRunner{

    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    private List<Class<?>> classes;

    private List<Class<?>> testClasses;

    public TestRunner() {
        classes = new ArrayList<>();
        testClasses = new ArrayList<>();
    }

    /**
     * @return the classes
     */
    public List<Class<?>> getClasses() {
        return classes;
    }


    /*
	 * (non-Javadoc)
	 *
	 * @see fr.istic.m2il.vv.mutation.testrunner.runner.ITestRunner#setClasses(java.util.List)
	 */
    @Override
    public void setClasses(List<Class<?>> classes) {
        for (Class classString : classes) {
            addClass(classString);
        }
        if(classes.size() == 0){
            logger.warn("No classes loaded during this setter call");
        }
        else{
            logger.debug("{} classes are loaded in TestRunner",classes.size());
        }
    }

    private void addClass(Class clazz) {
        if (classes == null) {
            classes = new ArrayList<>();
        }
        logger.debug("Adding {} to TestRunner classes collection",clazz);
        classes.add(clazz);
    }

    /**
     * @return the testClasses
     */
    public List<Class<?>> getTestClasses() {
        return testClasses;
    }

    /*
         * (non-Javadoc)
         *
         * @see fr.istic.m2il.vv.mutation.testrunner.runner.ITestRunner#setTestClasses(java.util.List)
         */
    @Override
    public void setTestClasses(List<Class<?>> testClasses) {
        for (Class testClass : testClasses) {
            addTestClass(testClass);
        }
        if(testClasses.size() == 0){
            logger.warn("No test classes loaded during this setter call");
        }
        else{
            logger.debug("{} test classes are loaded in TestRunner",classes.size());
        }
    }

    private void addTestClass(Class testClass) {
        if (testClasses == null) {
            testClasses = new ArrayList<>();
        }
        testClasses.add(testClass);
    }


    @Override
    public void execute() throws Exception {

    }
}
