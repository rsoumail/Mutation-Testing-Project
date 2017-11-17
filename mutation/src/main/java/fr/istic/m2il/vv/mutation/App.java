package fr.istic.m2il.vv.mutation;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import javassist.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class App {
//<<<<<<< 333b5839742817a574aece49195655f7003e73bc
    public static void main(String[] args){
/*=======
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
>>>>>>> Iitialisation des expressionEditor*/
        try{
            ClassPool pool = ClassPool.getDefault();
            Loader loader = new Loader(pool);

            Translator logger = new Translator() {
                public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
                    System.out.println("Starting...");
                }

                public void onLoad(ClassPool classPool, String classname) throws NotFoundException, CannotCompileException {
                    System.out.println("Loading...: " + classname);
                }
            };

            loader.addTranslator(pool, logger);
            pool.appendClassPath("../input/target/classes");
            pool.appendClassPath("../input/target/test-classes");
            loader.run("fr.istic.m2il.vv.input.App",args);
            
            JUnitCore jUnitCore = new JUnitCore();
            
            String classes[] = {"fr.istic.m2il.vv.input.AdditionTest",
            					"fr.istic.m2il.vv.input.DivisionTest",
            					"fr.istic.m2il.vv.input.MultiplicationTest",
            					"fr.istic.m2il.vv.input.SubstractionTest"};
            for(CtClass ctClass : pool.get(classes)) {
            	System.out.println("test: " + ctClass.getName());
            	Request request = Request.aClass(ctClass.toClass());
            	Result r = jUnitCore.run(request);
            	System.out.println("Tests ran : " + r.getRunCount() + ", failed : " + r.getFailureCount());
            }
/*=======
            pool.appendClassPath("input/target/classes");
            loader.run("fr.istic.m2il.vv.input.App", args);
>>>>>>> Iitialisation des expressionEditor*/
        }
        catch(Throwable exc) {
            System.out.println("Impossible de charger les sources de l'input.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
        try {
            runTest();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void runTest() throws ClassNotFoundException, MalformedURLException{




        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
                new URL("file:///media/www-dev/public/MutationTesting/input/target/classes/"),
                new URL("file:///media/www-dev/public/MutationTesting/input/target/test-classes/"),
        });

        JUnitCore core = new JUnitCore();
        Class<?> additionTest = urlClassLoader.loadClass("fr.istic.m2il.vv.input.AdditionTest");

        Result result = core.run(additionTest);

        System.out.println("FINISHED");
        System.out.println(String.format("| RUN: %d", result.getRunCount()));
        if(result.wasSuccessful())
            System.out.println("| ALL SUCCEEDED !");
        else
            System.out.println("| FAILURE ! ");
        for (Failure failure : result.getFailures()){
            System.out.println(failure.toString());
            System.out.println(failure.getTrace());
        }
        System.out.println(String.format("| TIME: %dms", result.getRunTime()));

    }
}
