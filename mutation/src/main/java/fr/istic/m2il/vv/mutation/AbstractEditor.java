package fr.istic.m2il.vv.mutation;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.expr.ExprEditor;

public abstract class AbstractEditor extends ExprEditor {
    void replace(CtMethod ctMethod) throws CannotCompileException{

    }
}
