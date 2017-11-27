package fr.istic.m2il.vv.mutation;

import fr.istic.m2il.vv.mutation.mutator.ArithmeticOperatorMutator;
import fr.istic.m2il.vv.mutation.mutator.BooleanMethodMutator;
import fr.istic.m2il.vv.mutation.mutator.Mutator;
import fr.istic.m2il.vv.mutation.mutator.VoidMethodMutator;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Opcode;
import org.junit.runner.JUnitCore;

import javassist.*;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class MutatorApp {

    static ClassPool pool;
    static Loader loader;
    static CustomTranslator translator;
    static File inputDir ;
    static  File testInputDir ;


    public static void main(String[] args) throws Exception {

        if(args.length < 1){
            System.err.println("Veuillez indiquer le chemin du projet source");
         exit(0);
        }
        else {

            /*File inputDir = new File(args[0] + "/target/classes");
            File testInputDir = new File(args[0]  + "/target/test-classes");*/

            loadSources(args[0]);
            mutate(new File("mutation/target/classes/"));

            /*deleteTarget(new File(  args[0] +"/target"));
            rebuildTarget("javac  -d input/target -cp input/src/main/java/fr/istic/m2il/vv/input/*.java");*/

        }

    }

    public static void runTest() throws ClassNotFoundException, IOException, NotFoundException, CannotCompileException {

        File f = new File("target/classes/");
        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
                //new URL("file://" + inputDir.getAbsoluteFile() + "/"),
                new URL("file://" + f.getAbsoluteFile() + "/"),
                new URL("file://" + testInputDir.getAbsolutePath() + "/"),
        });


        JUnitCore core = new JUnitCore();
        //Class<?>[] _testClasses = new Class<?>[testInputDir.listFiles().length];
        //loadTestClass(_testClasses, urlClassLoader, testInputDir);

        //String[] _testClasses = new String[];

        /*for(CtClass ctClass : pool.get(_testClasses)){
            //testClasses.add(ctClass.toClass());
        }*/

       // URL[] urls = new URL[translator.getCtClasses().size()];
      /*  int i = 0;
        for(CtClass classToReload : translator.getCtClasses()){
            System.out.println("URL" + classToReload.getURL());
            urls[i++] = classToReload.getURL();
        }
*/
        Class<?>[] classes = {
                urlClassLoader.loadClass("fr.istic.m2il.vv.input.AdditionTest"),
                urlClassLoader.loadClass("fr.istic.m2il.vv.input.SubstractionTest"),
                urlClassLoader.loadClass("fr.istic.m2il.vv.input.MultiplicationTest"),
                urlClassLoader.loadClass("fr.istic.m2il.vv.input.DivisionTest")
                };

        //loadTestClass(classes, testInputDir);

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

    public static void mutate(File inputDir){
        try{
             /*pool = ClassPool.getDefault();
             loader = new Loader(pool);

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
*/
            /*LoadClasses To mutate*/
            String classes[] = {
                    "fr.istic.m2il.vv.input.Operation",
                    "fr.istic.m2il.vv.input.Addition",
                    "fr.istic.m2il.vv.input.Division",
                    "fr.istic.m2il.vv.input.Multiplication",
                    "fr.istic.m2il.vv.input.Substraction"
            };

            mutateArithmeticOperation(classes, inputDir);
            /*try {
                runTest();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }*/
            //Utils.deleteTarget(new File("mutation/target"));
           // File mutation = new File("mutation");

            //System.out.println("New File " + mutation.getAbsolutePath());
           // Utils.rebuildTarget("javac -d ../mutation/" + " -cp " + mutation.getAbsolutePath()+"/src/main/java/fr/istic/m2il/vv/mutation/*.java");

            //mutateVoidReturnType(classes, inputDir);


            //Utils.deleteTarget(new File("mutation/target"));
            //Utils.rebuildTarget("javac -cp mutation/src/fr/istic/m2il/vv/mutation/*.java");
            /*mutateBooleanReturnType(classes, inputDir);
            try {
                runTest();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }*/

        }
        catch(Throwable exc) {
            System.out.println("Impossible de charger les sources de l'input.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }

    public static void mutateArithmeticOperation(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode {

        List<ArithmeticOperatorMutator> mutators = new ArrayList<>();
        for(CtClass ctClass: pool.get(classes)){
            ctClass.defrost();
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for(CtMethod method: methods){
                ArithmeticOperatorMutator bcArithmeticEditor = new ArithmeticOperatorMutator(inputPath);
                bcArithmeticEditor.mutate(method);
                mutators.add(bcArithmeticEditor);
            }

        }

        try {
            runTest();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        finally {
            for(Mutator mutator:mutators){
                mutator.revert();
            }

        }

    }

    public static void mutateVoidReturnType(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode {

        for(CtClass ctClass: pool.get(classes)){
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                Mutator mutator = new VoidMethodMutator(inputPath);
                mutator.mutate(method);
            }
        }

    }

    public static void mutateBooleanReturnType(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode {

        try {
            for(CtClass ctClass: pool.get(classes)){
                for (CtMethod method : ctClass.getDeclaredMethods()) {
                    Mutator mutator = new BooleanMethodMutator(inputPath);
                    mutator.mutate(method);
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadTestClass(Class<?>[] classes,URLClassLoader urlClassLoader, File dir) throws IOException, NotFoundException, CannotCompileException {

        /*for(CtClass c: translator.getCtClasses()){
            System.out.println("classe :" + c.toClass().getCanonicalName());
        }*/

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                loadTestClass(classes, urlClassLoader, file);
            } else {
                String name = file.getPath();
                if(file.getPath().contains("test-classes"))
                    System.out.println(file.getPath());
               //String className =  name.replaceAll("\\.class","");
              // className = className.replaceAll("/", "\\.");

                //String className = file.getAbsolutePath()

                /*if(name.contains(".class")){
                    String[] array = name.split("\\.");

                    //Class<?> clazz = file.
                }*/
            }
        }

    }

    public static void loadSources(String input) throws NotFoundException, CannotCompileException {
        pool = ClassPool.getDefault();
        loader = new Loader(pool);

        translator = new CustomTranslator();

        inputDir = new File(input + "/target/classes");
        testInputDir = new File(input  + "/target/test-classes");

        loader.addTranslator(pool, translator);
        pool.appendClassPath(inputDir.getPath());
        pool.appendClassPath(testInputDir.getPath());

    }
}
