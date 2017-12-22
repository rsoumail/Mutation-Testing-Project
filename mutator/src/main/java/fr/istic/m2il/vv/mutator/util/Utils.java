package fr.istic.m2il.vv.mutator.util;

import fr.istic.m2il.vv.mutator.mutant.Mutator;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Properties;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static void write(CtClass ctClass, File inputPath) throws IOException, CannotCompileException {

        ctClass.writeFile(inputPath.getAbsolutePath());
    }

    public static Properties loadPropertiesFile(File propertiesFile){

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(propertiesFile);

            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  prop;
    }

    public static int testsCasesInTestClass(Class<?> klass){
        int testsCases = 0;


        Method[] methods = klass.getDeclaredMethods();

        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().toString()
                            .equals("interface org.junit.Test")) {
                        testsCases++;
                    }
                }
        }
        return testsCases;
    }

    public static void revert(CtMethod modified, CtMethod original, Mutator mutator, TargetProject targetProject) throws IOException, CannotCompileException {
        if(modified.getDeclaringClass().isFrozen())
            modified.getDeclaringClass().defrost();
        logger.info("Reverting  {}", mutator.getClass().getName() + " Revert " + modified.getName() + " on " +targetProject.getLocation());
        modified.setBody(original, null);
        Utils.write(modified.getDeclaringClass(), targetProject.getClassesLocation());
    }
}
