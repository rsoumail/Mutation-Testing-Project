package fr.istic.m2il.vv.mutation;

import javassist.bytecode.Opcode;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import javassist.*;

import org.junit.runner.notification.Failure;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static java.lang.System.exit;

public class App {

    public static void main(String[] args){
        String inputPath, inputTestPath;
        if(args.length < 1){
            System.err.println(args[0]);
            System.err.println("Veuillez indiquer le chemin du projet source");
         exit(0);
        }
        else {
            inputPath = args[0] + "/target/classes/";
            inputTestPath = args[0] + "/target/test-classes/";
            mutate(inputPath);
            try {
                runTest(inputPath,
                        inputTestPath);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            deleteTarget(inputPath);
        }

    }

    public static void runTest(String targetClasses, String targetTestClasses ) throws ClassNotFoundException, MalformedURLException{




        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
                new URL("file://" + targetClasses),
                new URL("file://" + targetTestClasses),
        });

        JUnitCore core = new JUnitCore();
        Class<?>[] classes = {
                urlClassLoader.loadClass("fr.istic.m2il.vv.input.AdditionTest"),
                urlClassLoader.loadClass("fr.istic.m2il.vv.input.SubstractionTest"),
                urlClassLoader.loadClass("fr.istic.m2il.vv.input.MultiplicationTest"),
                urlClassLoader.loadClass("fr.istic.m2il.vv.input.DivisionTest")
                };
        for(Class<?> clazz : classes) {
            	System.out.println("Test: " + clazz.getName());
            	Request request = Request.aClass(clazz);
            	Result r = core.run(request);

                System.out.println("For " +  clazz.getName() + String.format("| RUN: %d", r.getRunCount()));
                if(r.wasSuccessful())
                    System.out.println( "For " +  clazz.getName() + "| ALL SUCCEEDED !");
                else
                    System.out.println( "For " +  clazz.getName() + "| FAILURE ! ");
                for (Failure failure : r.getFailures()){
                    System.out.println(failure.toString());
                    System.out.println(failure.getTrace());
                }

            System.out.println("For " +  clazz.getName() + String.format("| TIME: %dms", r.getRunTime()));
            System.out.println("");
        }

        System.out.println("ALL TESTS FINISHED");

    }

    public static void deleteTarget(String inputPath){
        File target = new File(inputPath);
        for(String file : target.list()){
            File currentFile = new File(target.getPath(),file);
            currentFile.delete();
        }
    }

    public static void buildTarget(){

    }

    public static void mutate(String inputPath){
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
            pool.appendClassPath(inputPath);

            /*LoadClasses To mutate*/
            String classes[] = {
                    "fr.istic.m2il.vv.input.Operation",
                    "fr.istic.m2il.vv.input.Addition",
                    "fr.istic.m2il.vv.input.Division",
                    "fr.istic.m2il.vv.input.Multiplication",
                    "fr.istic.m2il.vv.input.Substraction"
            };


            for(CtClass ctClass: pool.get(classes)){
                CtMethod methode = ctClass.getDeclaredMethod("operate");
                if(ctClass.getName().contains("Addition")){
                    int oldopcode = 0;
                    int newOpcode = 0;
                    switch (methode.getReturnType().getName()){
                        case "double":
                            oldopcode = Opcode.DADD;
                            newOpcode = Opcode.DSUB;
                            break;

                        case "int":
                            oldopcode = Opcode.IADD;
                            newOpcode = Opcode.ISUB;
                            break;

                        case "float":
                            oldopcode = Opcode.IADD;
                            newOpcode = Opcode.ISUB;
                            break;

                        case "long":
                            oldopcode = Opcode.IADD;
                            newOpcode = Opcode.ISUB;
                            break;

                    }
                    BCArithmeticEditor bcArithmeticEditor = new BCArithmeticEditor(inputPath, oldopcode, newOpcode);
                    bcArithmeticEditor.replace(methode);
                }
                if(ctClass.getName().contains("Substraction")){
                    BCArithmeticEditor bcArithmeticEditor = new BCArithmeticEditor(inputPath, Opcode.DSUB, Opcode.DADD);
                    bcArithmeticEditor.replace(methode);
                }
                if(ctClass.getName().contains("Multiplication")){
                    BCArithmeticEditor bcArithmeticEditor = new BCArithmeticEditor(inputPath, Opcode.DMUL, Opcode.DDIV);
                    bcArithmeticEditor.replace(methode);
                }
                if(ctClass.getName().contains("Division")){
                    BCArithmeticEditor bcArithmeticEditor = new BCArithmeticEditor(inputPath, Opcode.DDIV, Opcode.DMUL);
                    bcArithmeticEditor.replace(methode);
                }
            }
        }
        catch(Throwable exc) {
            System.out.println("Impossible de charger les sources de l'input.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }
}
