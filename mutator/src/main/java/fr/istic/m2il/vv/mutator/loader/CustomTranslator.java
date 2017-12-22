package fr.istic.m2il.vv.mutator.loader;

import javassist.*;

import java.util.HashSet;
import java.util.Set;

public class CustomTranslator implements Translator{


    public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
        System.out.println("Starting...");
    }

    public void onLoad(ClassPool classPool, String className) throws NotFoundException, CannotCompileException {
    }

}
