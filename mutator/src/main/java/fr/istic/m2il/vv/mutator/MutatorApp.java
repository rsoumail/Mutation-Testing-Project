package fr.istic.m2il.vv.mutator;

import fr.istic.m2il.vv.mutator.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutator.common.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.mutant.*;
import fr.istic.m2il.vv.mutator.target.TargetProject;
import javassist.bytecode.BadBytecode;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.runner.JUnitCore;

import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
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


    public static void main(String[] args) throws Exception {

        ApplicationProperties applicationProperties = new ApplicationProperties();

        if(Utils.loadPropertiesFile(applicationProperties.getApplicationPropertiesFile()).getProperty("target.project").isEmpty()){
            System.err.println("Veuillez indiquer la propriété target dans le fichier application properties");
            exit(0);
        }
        else {

            ClassLoaderParser classLoaderParser = new ClassLoaderParser();

            TargetProject targetProject = new TargetProject();
            targetProject.setLocation(new File(Utils.loadPropertiesFile(applicationProperties.getApplicationPropertiesFile()).getProperty("target.project")));
            targetProject.setPom(new File(targetProject.getLocation().getAbsolutePath() + "/pom.xml"));
            targetProject.setClasses(classLoaderParser.getClassesFromDirectory(targetProject.getClassesLocation().getAbsolutePath()));
            targetProject.setTests(classLoaderParser.getClassesFromDirectory(targetProject.getTestsLocation().getAbsolutePath()));

            JavaAssistHelper javaAssistHelper = new JavaAssistHelper(new ClassPool() , new Loader(), new CustomTranslator(),targetProject);

            Mutator mutator = new ArithmeticOperatorMutator(targetProject);

            MutatorExecutor mutatorExecutor = new MutatorExecutor(javaAssistHelper);
            mutatorExecutor.execute(mutator, targetProject);


            /*PITRunner pitRunner = new PITRunner();
            pitRunner.run();
*/
        }
    }

    public static void mutate(File inputDir) throws CannotCompileException, BadBytecode, NotFoundException, IOException, ClassNotFoundException, InterruptedException, MavenInvocationException {

        mutateVoidReturnType(_sourcesClasses, inputDir);
        mutateBooleanReturnType(_sourcesClasses, inputDir);

    }


    public static void mutateVoidReturnType(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode, ClassNotFoundException, InterruptedException, MavenInvocationException {

        ClassLoader classLoader = MutatorApp.class.getClassLoader();
        File file = new File(classLoader.getResource("application.properties").getFile());
        for(CtClass ctClass: pool.get(classes)){
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for(CtMethod method: methods){
                /*Mutator mutant = new VoidMethodMutator(inputPath, new File(Utils.loadPropertiesFile(file).getProperty("target")));
                mutant.mutate(method);*/
            }

        }
    }

    public static void mutateBooleanReturnType(String[] classes, File inputPath) throws NotFoundException, CannotCompileException, IOException, BadBytecode, InterruptedException, MavenInvocationException {

        ClassLoader classLoader = MutatorApp.class.getClassLoader();
        File file = new File(classLoader.getResource("application.properties").getFile());
        for(CtClass ctClass: pool.get(classes)){
            for(CtMethod method: ctClass.getDeclaredMethods()){
//                Mutator mutant = new BooleanMethodMutator(inputPath, new File(Utils.loadPropertiesFile(file).getProperty("target")));
//                mutant.mutate(method);
            }

        }

    }

}
