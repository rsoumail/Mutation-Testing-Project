package fr.istic.m2il.vv.mutation;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import javassist.*;

public class App {
    public static void main(String[] args){
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
        }
        catch(Throwable exc) {
            System.out.println("Oh, no! Something went wrong.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }
}
