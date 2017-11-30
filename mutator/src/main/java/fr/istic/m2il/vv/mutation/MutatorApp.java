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


        ClassLoader classLoader = MutatorApp.class.getClassLoader();
        File file = new File(classLoader.getResource("application.properties").getFile());

        if(Utils.loadPropertiesFile(file).getProperty("target").isEmpty()){
            System.err.println("Veuillez indiquer la propriété target dans le fichier application properties");
            exit(0);
        }
        else {

            loadSources(new File(Utils.loadPropertiesFile(file).getProperty("target")));
            mutate(inputDir);
            PITRunner pitRunner = new PITRunner();
            pitRunner.run();

        }

    }

    public static void mutate(File inputDir) throws CannotCompileException, BadBytecode, NotFoundException, IOException, ClassNotFoundException, InterruptedException, MavenInvocationException {

        mutateArithmeticOperation(_sourcesClasses, inputDir);
        mutateVoidReturnType(_sourcesClasses, inputDir);
        mutateBooleanReturnType(_sourcesClasses, inputDir);

    }

    public static void mutateArithmeticOperation(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode, InterruptedException, MavenInvocationException {

        ClassLoader classLoader = MutatorApp.class.getClassLoader();
        File file = new File(classLoader.getResource("application.properties").getFile());
        for(CtClass ctClass: pool.get(classes)){
            ctClass.defrost();
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for(CtMethod method: methods){
                Mutator mutator = new ArithmeticOperatorMutator(inputPath, new File(Utils.loadPropertiesFile(file).getProperty("target")));
                mutator.mutate(method);
            }
        }
    }

    public static void mutateVoidReturnType(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode, ClassNotFoundException, InterruptedException, MavenInvocationException {

        ClassLoader classLoader = MutatorApp.class.getClassLoader();
        File file = new File(classLoader.getResource("application.properties").getFile());
        for(CtClass ctClass: pool.get(classes)){
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for(CtMethod method: methods){

                Mutator mutator = new VoidMethodMutator(inputPath, new File(Utils.loadPropertiesFile(file).getProperty("target")));
                mutator.mutate(method);
            }

        }


    }

    public static void mutateBooleanReturnType(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode, InterruptedException, MavenInvocationException {

        ClassLoader classLoader = MutatorApp.class.getClassLoader();
        File file = new File(classLoader.getResource("application.properties").getFile());
        for(CtClass ctClass: pool.get(classes)){
            for(CtMethod method: ctClass.getDeclaredMethods()){
                Mutator mutator = new BooleanMethodMutator(inputPath, new File(Utils.loadPropertiesFile(file).getProperty("target")));
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


    public static void loadSources(File input) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {

        try{
            pool = ClassPool.getDefault();
            loader = new Loader(pool);

            translator = new CustomTranslator();

            inputDir = new File(input.getAbsolutePath() + "/target/classes");

            testInputDir = new File(input.getAbsolutePath()  + "/target/test-classes");
            saveSourcesDir = new File("target/classes");

            loader.addTranslator(pool, translator);
            pool.appendClassPath(inputDir.getAbsolutePath());
            pool.appendClassPath(testInputDir.getAbsolutePath());


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
