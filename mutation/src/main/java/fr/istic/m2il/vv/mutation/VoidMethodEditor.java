package fr.istic.m2il.vv.mutation;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;

import java.io.IOException;

public class VoidMethodEditor implements Mutator {

    private String inputPath;

    public VoidMethodEditor(String inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public void replace(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException {
        ctMethod.getDeclaringClass().defrost();
        ctMethod.setBody("{}");
        System.out.println("try");
        write(ctMethod.getDeclaringClass());
        System.out.println("written ");
    }

    public void write(CtClass ctClass) throws CannotCompileException, IOException {
        System.out.println("Try write file");
        ctClass.writeFile(inputPath);
        System.out.println("Finish written");
    }
}
