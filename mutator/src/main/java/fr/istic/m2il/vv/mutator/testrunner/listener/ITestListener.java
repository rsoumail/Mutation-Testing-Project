package fr.istic.m2il.vv.mutator.testrunner.listener;

import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import org.junit.runner.notification.RunListener;

public interface ITestListener {

    void timedOut(Thread thread);
}
