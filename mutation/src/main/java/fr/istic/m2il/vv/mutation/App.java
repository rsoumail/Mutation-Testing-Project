package fr.istic.m2il.vv.mutation;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.Opcode;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javassist.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static java.lang.System.exit;

public class App {


    public static void main(String[] args) throws Exception {
        String inputPath, inputTestPath;
        if(args.length < 1){
            System.err.println(args[0]);
            System.err.println("Veuillez indiquer le chemin du projet source");
         exit(0);
        }
        else {
            inputPath = args[0] + "/target/classes/";
            inputTestPath = args[0] + "/target/test-classes/";
            mutate(inputPath, args);
            try {
                runTest(inputPath,
                        inputTestPath);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }/*
            deleteTarget(new File(inputPath));
            rebuildTarget("javac input/*.java");*/
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

    public static void deleteTarget(File inputPath){
        File[] allContents = inputPath.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteTarget(new File(file.getName()));
            }
        }
    }

    public static void buildTarget(){

    }

    public static void mutate(String inputPath, String[] args){
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


            mutateArithmeticOperation(pool, classes, inputPath);
            mutateComparisonOperation(pool, classes, inputPath);

        }
        catch(Throwable exc) {
            System.out.println("Impossible de charger les sources de l'input.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }

    public static void mutateArithmeticOperation(ClassPool pool, String[] classes, String inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode {

        for(CtClass ctClass: pool.get(classes)){
            ctClass.defrost();
            CtMethod methode = ctClass.getDeclaredMethod("operate");

            if(ctClass.getName().contains("Addition")){

                int oldopcode = 0;
                int newOpcode = 0;

                switch (methode.getReturnType().getName().toString()){
                    case "double":
                        oldopcode = Opcode.DADD;
                        newOpcode = Opcode.DSUB;
                        break;

                    case "int":
                        oldopcode = Opcode.IADD;
                        newOpcode = Opcode.ISUB;
                        break;

                    case "float":
                        oldopcode = Opcode.FADD;
                        newOpcode = Opcode.FSUB;
                        break;

                    case "long":
                        oldopcode = Opcode.LADD;
                        newOpcode = Opcode.LSUB;
                        break;

                }
                BCArithmeticEditorMutator bcArithmeticEditor = new BCArithmeticEditorMutator(inputPath, oldopcode, newOpcode);
                bcArithmeticEditor.replace(methode);
            }
            if(ctClass.getName().contains("Substraction")){

                int oldopcode = 0;
                int newOpcode = 0;

                switch (methode.getReturnType().getName().toString()){
                    case "double":
                        oldopcode = Opcode.DSUB;
                        newOpcode = Opcode.DADD;
                        break;

                    case "int":
                        oldopcode = Opcode.ISUB;
                        newOpcode = Opcode.IADD;
                        break;

                    case "float":
                        oldopcode = Opcode.FSUB;
                        newOpcode = Opcode.FADD;
                        break;

                    case "long":
                        oldopcode = Opcode.LSUB;
                        newOpcode = Opcode.LADD;
                        break;

                }

                BCArithmeticEditorMutator bcArithmeticEditor = new BCArithmeticEditorMutator(inputPath, oldopcode, newOpcode);
                bcArithmeticEditor.replace(methode);
            }
            if(ctClass.getName().contains("Multiplication")){

                int oldopcode = 0;
                int newOpcode = 0;

                switch (methode.getReturnType().getName().toString()){
                    case "double":
                        oldopcode = Opcode.DMUL;
                        newOpcode = Opcode.DDIV;
                        break;

                    case "int":
                        oldopcode = Opcode.IMUL;
                        newOpcode = Opcode.IDIV;
                        break;

                    case "float":
                        oldopcode = Opcode.FMUL;
                        newOpcode = Opcode.FDIV;
                        break;

                    case "long":
                        oldopcode = Opcode.LMUL;
                        newOpcode = Opcode.LDIV;
                        break;

                }

                BCArithmeticEditorMutator bcArithmeticEditor = new BCArithmeticEditorMutator(inputPath, oldopcode, newOpcode);
                bcArithmeticEditor.replace(methode);
            }
            if(ctClass.getName().contains("Division")){

                int oldopcode = 0;
                int newOpcode = 0;

                switch (methode.getReturnType().getName().toString()){
                    case "double":
                        oldopcode = Opcode.DDIV;
                        newOpcode = Opcode.DMUL;
                        break;

                    case "int":
                        oldopcode = Opcode.IDIV;
                        newOpcode = Opcode.IMUL;
                        break;

                    case "float":
                        oldopcode = Opcode.FDIV;
                        newOpcode = Opcode.FMUL;
                        break;

                    case "long":
                        oldopcode = Opcode.LDIV;
                        newOpcode = Opcode.LMUL;
                        break;

                }

                BCArithmeticEditorMutator bcArithmeticEditor = new BCArithmeticEditorMutator(inputPath, oldopcode, newOpcode);
                bcArithmeticEditor.replace(methode);
            }
        }

    }

    public static void mutateComparisonOperation(ClassPool pool, String[] classes, String inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode {

        for(CtClass ctClass: pool.get(classes)){
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                if (method.getReturnType().equals(CtClass.voidType)) {
                    VoidMethodMutator voidMethodEditor = new VoidMethodMutator(inputPath);
                    try {
                        voidMethodEditor.replace(method);
                    } catch (CannotCompileException e) {
                        e.printStackTrace();
                    } catch (BadBytecode badBytecode) {
                        badBytecode.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static void rebuildTarget(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }
}
