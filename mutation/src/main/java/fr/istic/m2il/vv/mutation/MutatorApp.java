package fr.istic.m2il.vv.mutation;

import fr.istic.m2il.vv.mutation.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutation.mutator.ArithmeticOperatorMutator;
import fr.istic.m2il.vv.mutation.mutator.BooleanMethodMutator;
import fr.istic.m2il.vv.mutation.mutator.Mutator;
import fr.istic.m2il.vv.mutation.mutator.VoidMethodMutator;
import fr.istic.m2il.vv.mutation.report.PITRunner;
import fr.istic.m2il.vv.mutation.testrunner.runner.MVNRunner;
import javassist.bytecode.BadBytecode;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.runner.JUnitCore;

import javassist.*;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.lang.System.exit;

public class MutatorApp {

    static ClassPool pool;
    static Loader loader;
    static CustomTranslator translator;
    static File inputDir ;
    static File testInputDir ;
    static File saveSourcesDir;
    static String[] _sourcesClasses;

    static JUnitCore core = new JUnitCore();

    private static Logger logger = LoggerFactory.getLogger(MutatorApp.class);
    private static String classesPath = "";
    private static String testClassesPath = "";



    public static void main(String[] args) throws Exception {


        if(args.length < 1){
            System.err.println("Veuillez indiquer le chemin du projet source");
         exit(0);
        }
        else {

            //Properties properties = Utils.loadPropertiesFile("application.properties");

            /*classesPath = args[0] + "/target/classes";
            testClassesPath = args[0] + "/target/test-classes";

            logger.debug("Mutation testing for project :");
            logger.debug("Classes root directory : {}", classesPath);
            logger.debug("Test classes root directory : {}", testClassesPath);

            // Chargement des classes du projet target
            ClassLoaderParser classLoaderParser = new ClassLoaderParser();
            List<Class<?>> classList = classLoaderParser.getClassesFromDirectory(classesPath);
            List<Class<?>> testClassList = classLoaderParser.getClassesFromDirectory(testClassesPath);*/

            loadSources(args[0]);
            mutate(inputDir);
            PITRunner pitRunner = new PITRunner();
            pitRunner.run();

        }

    }

    /*public static void runTest() throws ClassNotFoundException, IOException, NotFoundException, CannotCompileException, InterruptedException {

        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
                new URL("file://" + saveSourcesDir.getAbsoluteFile() + "/"),
                new URL("file://" + testInputDir.getAbsolutePath() + "/"),
        });



        List<Class<?>> _testClasses = new ArrayList<>();
        _testClasses = loadTestClass(_testClasses, urlClassLoader, testInputDir);
        System.out.println("Size " + _testClasses.size());

        for(Class<?> clazz : _testClasses) {
            	System.out.println("Test: " + clazz.getName());
            	Request request = Request.aClass(clazz);
            	Result r = core.run(request);

                System.out.println("For " +  clazz.getName() + String.format("| RUN: %d", r.getRunCount()));
                System.out.println("");
                if(r.wasSuccessful())
                    System.out.println( "For " +  clazz.getName() + "| ALL SUCCEEDED !");
                else {
                    System.out.println("For " + clazz.getName() + String.format("| FAILURE! : %d", r.getFailureCount()));
                    System.out.println();
                    for (Failure failure : r.getFailures()){
                        System.out.println(failure.toString());
                        System.out.println(failure.getTrace());
                    }

                }

            System.out.println("For " +  clazz.getName() + String.format("| TIME: %dms", r.getRunTime()));
            System.out.println("");
        }

        urlClassLoader.close();
        System.out.println("ALL TESTS FINISHED");

    }*/

    public static void mutate(File inputDir) throws CannotCompileException, BadBytecode, NotFoundException, IOException, ClassNotFoundException, InterruptedException, MavenInvocationException {

        mutateArithmeticOperation(_sourcesClasses, inputDir);
        mutateVoidReturnType(_sourcesClasses, inputDir);
        mutateBooleanReturnType(_sourcesClasses, inputDir);

    }

    public static void mutateArithmeticOperation(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode, InterruptedException, MavenInvocationException {

        for(CtClass ctClass: pool.get(classes)){
            ctClass.defrost();
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for(CtMethod method: methods){
                Mutator mutator = new ArithmeticOperatorMutator(inputPath, new File("input/"));
                mutator.mutate(method);
            }
        }
    }

    public static void mutateVoidReturnType(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode, ClassNotFoundException, InterruptedException, MavenInvocationException {

        for(CtClass ctClass: pool.get(classes)){
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for(CtMethod method: methods){
                Mutator mutator = new VoidMethodMutator(inputPath, new File("input/"));
                mutator.mutate(method);
            }

        }


    }

    public static void mutateBooleanReturnType(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode, InterruptedException, MavenInvocationException {

        for(CtClass ctClass: pool.get(classes)){
            for(CtMethod method: ctClass.getDeclaredMethods()){
                Mutator mutator = new BooleanMethodMutator(inputPath, new File("input/"));
                mutator.mutate(method);
            }

        }

    }

    public static List<Class<?>> loadTestClass(List<Class<?>> classes,URLClassLoader urlClassLoader, File dir) throws IOException, NotFoundException, CannotCompileException, ClassNotFoundException {

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                loadTestClass(classes, urlClassLoader, file);
            } else {
                String[] pathElements = file.getPath().split("test-classes/");
                String classFile = pathElements[1].replace(".class", "");
                classFile = classFile.replace("/", ".");
                classes.add(urlClassLoader.loadClass(classFile));
            }
        }
        return  classes;

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

    }
}
