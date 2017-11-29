package fr.istic.m2il.vv.mutation;

import javassist.CtClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class JavaProcess {

    private JavaProcess() {}

    public static int exec(List<Class<?>> classes) throws IOException,
            InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = "";
        for(Class<?> klass: classes){
            className = className + klass.getCanonicalName() + "";
        }
        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className).inheritIO();

        Process process = builder.start();
        process.waitFor();
        return process.exitValue();
    }

}
