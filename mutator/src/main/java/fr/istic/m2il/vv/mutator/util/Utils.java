package fr.istic.m2il.vv.mutator.util;

import javassist.CannotCompileException;
import javassist.CtClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    public static void rebuildTarget(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }

    public static void deleteTarget(File inputPath){
        File[] allContents = inputPath.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteTarget(new File(inputPath.toPath()+ "/"+ file.getName()));
            }
        }
        inputPath.delete();
    }

    public static void write(CtClass ctClass, File inputPath) throws CannotCompileException, IOException {
        ctClass.writeFile(inputPath.getAbsolutePath());
    }

    public static Properties loadPropertiesFile(File propertiesFile){

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(propertiesFile);

            // load a properties file
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
}
