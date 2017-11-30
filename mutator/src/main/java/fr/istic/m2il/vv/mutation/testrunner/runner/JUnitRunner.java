package fr.istic.m2il.vv.mutation.testrunner.runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitRunner {

    private static Logger logger = LoggerFactory.getLogger(JUnitRunner.class);

    private JUnitCore jUnitCore;

    /**
     * Constructor instanciates junitcore
     */
    public JUnitRunner() {
        jUnitCore = new JUnitCore();
    }

    /**
     * Adds a listener
     * @param listener : the listener
     */
    public void addAListener(RunListener listener){
        if(jUnitCore==null){
            logger.warn("Listener can not be added to junit runner : the junit core is null");
        }
        else{
            logger.debug("Adding listener [{}]",listener.getClass());
            jUnitCore.addListener(listener);
        }
    }

    public void run(Class runClass){
        if(runClass.isInterface()){
            logger.debug("{} is an interface no test will be run");
        }
        else{
            Result result = jUnitCore.run(runClass);
        }
    }

}
