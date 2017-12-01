package fr.istic.m2il.vv.mutator.javassistloader;

import javassist.*;

import java.util.HashSet;
import java.util.Set;

public class CustomTranslator implements Translator{

    private Set<CtClass> ctClasses = new HashSet<CtClass>();

    public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
        System.out.println("Starting...");
    }

    public void onLoad(ClassPool classPool, String className) throws NotFoundException, CannotCompileException {
        System.out.println("Loading...: " + className);
        ctClasses.add(classPool.get(className));
    }

    public Set<CtClass> getCtClasses() {
        return ctClasses;
    }
}
