package fr.istic.m2il.vv.mutation;

//import fr.istic.m2il.vv.mutation.mutator.ArithmeticOperatorMutator;
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
    static File testInputDir ;
    static File saveSourcesDir;
    static String[] _sourcesClasses;
    static List<Class<?>> _testClasses = new ArrayList<>();
    static JUnitCore core = new JUnitCore();
    static URLClassLoader urlClassLoader;


    public static void main(String[] args) throws Exception {

        if(args.length < 1){
            System.err.println("Veuillez indiquer le chemin du projet source");
         exit(0);
        }
        else {
            loadSources(args[0]);
            mutate(new File("mutation/target/classes/"));
        }

    }

    public static void runTest() throws ClassNotFoundException, IOException, NotFoundException, CannotCompileException {



        for(Class<?> clazz : _testClasses) {
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

    public static void mutate(File inputDir) throws CannotCompileException, BadBytecode, NotFoundException, IOException {

        mutateArithmeticOperation(_sourcesClasses, inputDir);

    }

    public static void mutateArithmeticOperation(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode {

        List<Mutator> mutators = new ArrayList<>();
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

        for(CtClass ctClass: pool.get(classes)){
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                Mutator mutator = new BooleanMethodMutator(inputPath);
                mutator.mutate(method);
            }
        }
    }

    public static void loadTestClass(List<Class<?>> classes,URLClassLoader urlClassLoader, File dir) throws IOException, NotFoundException, CannotCompileException, ClassNotFoundException {

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                loadTestClass(classes, urlClassLoader, file);
            } else {
                String[] pathElements = file.getPath().split("classes/");
                String classFile = pathElements[1].replace(".class", "");
                classFile = classFile.replace("/", ".");
                classes.add(urlClassLoader.loadClass(classFile));
            }
        }

    }

    public static void loadClass(List<String> classes, File dir) throws IOException, NotFoundException, CannotCompileException, ClassNotFoundException {

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                loadClass(classes, file);
            } else {
                String[] pathElements = file.getPath().split("classes/");
                String classFile = pathElements[1].replace(".class", "");
                classFile = classFile.replace("/", ".");
                classes.add(classFile);
            }
        }

    }


    public static void loadSources(String input) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {

        try{
            pool = ClassPool.getDefault();
            loader = new Loader(pool);

            translator = new CustomTranslator();

            inputDir = new File(input + "/target/classes");
            testInputDir = new File(input  + "/target/test-classes");
            saveSourcesDir = new File("target/classes");


            loader.addTranslator(pool, translator);
            pool.appendClassPath(inputDir.getPath());
            pool.appendClassPath(testInputDir.getPath());
        }
        catch(Throwable exc) {
            System.out.println("Impossible de charger les sources de l'input.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }

        List<String> sourcesClassesList = new ArrayList<>();
        loadClass(sourcesClassesList, inputDir);
        _sourcesClasses = new String[sourcesClassesList.size()];
        int i = 0;
        for(String source: sourcesClassesList){
            _sourcesClasses[i] = source.toString();
            i++;
        }

        urlClassLoader = URLClassLoader.newInstance(new URL[]{
                //new URL("file://" + inputDir.getAbsoluteFile() + "/"),
                new URL("file://" + saveSourcesDir.getAbsoluteFile() + "/"),
                new URL("file://" + testInputDir.getAbsolutePath() + "/"),
        });

        loadTestClass(_testClasses, urlClassLoader, testInputDir);

    }
}
