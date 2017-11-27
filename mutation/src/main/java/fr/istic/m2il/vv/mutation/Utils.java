package fr.istic.m2il.vv.mutation;

import javassist.CannotCompileException;
import javassist.CtClass;

import java.io.File;
import java.io.IOException;

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
}
