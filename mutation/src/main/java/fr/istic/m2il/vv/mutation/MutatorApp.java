package fr.istic.m2il.vv.mutation;

import fr.istic.m2il.vv.mutation.mutator.BCArithmeticMutator;
import fr.istic.m2il.vv.mutation.mutator.BooleanMethodMutator;
import fr.istic.m2il.vv.mutation.mutator.Mutator;
import fr.istic.m2il.vv.mutation.mutator.VoidMethodMutator;
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

public class MutatorApp {


    public static void main(String[] args) throws Exception {
        String inputPath, inputTestPath;
        if(args.length < 1){
            System.err.println(args[0]);
            System.err.println("Veuillez indiquer le chemin du projet source");
         exit(0);
        }
        else {

            File inputDir = new File(args[0] + "/target/classes");
            File testInputDir = new File(args[0]  + "/target/test-classes");

            /*inputPath = args[0] + "/target/classes/";
            inputTestPath = args[0] + "/target/test-classes/";*/
            mutate(inputDir, args);
            try {
                runTest(inputDir, testInputDir);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            /*deleteTarget(new File(  args[0] +"/target"));
            rebuildTarget("javac  -d input/target -cp input/src/main/java/fr/istic/m2il/vv/input/*.java");*/

        }

    }

    public static void runTest(File inputDir, File testInputDir ) throws ClassNotFoundException, MalformedURLException{




        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
                new URL("file://" + inputDir.getAbsoluteFile() + "/"),
                new URL("file://" + testInputDir.getAbsolutePath() + "/"),
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
                System.out.println();
                if(r.wasSuccessful())
                    System.out.println( "For " +  clazz.getName() + "| ALL SUCCEEDED !");
                else
                    System.out.println( "For " +  clazz.getName() +  String.format("| FAILURE! : %d", r.getFailureCount()));
                System.out.println();

                for (Failure failure : r.getFailures()){
                    System.out.println(failure.toString());
                    System.out.println(failure.getTrace());
                }

            System.out.println("For " +  clazz.getName() + String.format("| TIME: %dms", r.getRunTime()));
            System.out.println("");
        }

        System.out.println("ALL TESTS FINISHED");

    }

    public static void mutate(File inputDir, String[] args){
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
            pool.appendClassPath(inputDir.getAbsolutePath());

            /*LoadClasses To mutate*/
            String classes[] = {
                    "fr.istic.m2il.vv.input.Operation",
                    "fr.istic.m2il.vv.input.Addition",
                    "fr.istic.m2il.vv.input.Division",
                    "fr.istic.m2il.vv.input.Multiplication",
                    "fr.istic.m2il.vv.input.Substraction"
            };


            mutateArithmeticOperation(pool, classes, inputDir.getAbsolutePath());
            mutateVoidReturnType(pool, classes, inputDir.getAbsolutePath());
            mutateBooleanReturnType(pool, classes, inputDir.getAbsolutePath());

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
                BCArithmeticMutator bcArithmeticEditor = new BCArithmeticMutator(inputPath, oldopcode, newOpcode);
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

                BCArithmeticMutator bcArithmeticEditor = new BCArithmeticMutator(inputPath, oldopcode, newOpcode);
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

                BCArithmeticMutator bcArithmeticEditor = new BCArithmeticMutator(inputPath, oldopcode, newOpcode);
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

                Mutator mutator = new BCArithmeticMutator(inputPath, oldopcode, newOpcode);
                mutator.replace(methode);
            }
        }

    }

    public static void mutateVoidReturnType(ClassPool pool, String[] classes, String inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode {

        for(CtClass ctClass: pool.get(classes)){
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                if (method.getReturnType().equals(CtClass.voidType)) {
                    Mutator mutator = new VoidMethodMutator(inputPath);
                    try {
                        mutator.replace(method);
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

    public static void mutateBooleanReturnType(ClassPool pool, String[] classes, String inputPath) throws NotFoundException {

        try {
            for(CtClass ctClass: pool.get(classes)){
                for (CtMethod method : ctClass.getDeclaredMethods()) {
                    if (method.getReturnType().equals(CtClass.booleanType)) {
                        Mutator mutator = new BooleanMethodMutator(inputPath);
                        try {
                            mutator.replace(method);
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
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }


}
