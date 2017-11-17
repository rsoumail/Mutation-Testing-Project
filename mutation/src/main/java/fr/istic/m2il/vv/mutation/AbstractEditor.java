package fr.istic.m2il.vv.mutation;

import javassist.CannotCompileException;
import javassist.CtMethod;

public interface AbstractEditor {
    void replace(CtMethod ctMethod) throws CannotCompileException;
}
