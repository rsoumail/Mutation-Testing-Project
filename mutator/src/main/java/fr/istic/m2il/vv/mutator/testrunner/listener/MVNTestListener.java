package fr.istic.m2il.vv.mutator.testrunner.listener;

import fr.istic.m2il.vv.mutator.common.TimeWatch;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;

import java.util.concurrent.TimeUnit;

public class MVNTestListener implements ITestListener {

    private static int TIME_OUT = 10;
    @Override
    public void timedOut(Thread task) {
        /*TimeWatch watcher = TimeWatch.start();
        watcher.time(TimeUnit.SECONDS);
        while (true){
            if(watcher.time(TimeUnit.SECONDS) == TIME_OUT){
                task.stop();
            }
        }*/
    }
}
