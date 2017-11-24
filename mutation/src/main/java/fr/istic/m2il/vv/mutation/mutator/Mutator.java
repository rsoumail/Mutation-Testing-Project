package fr.istic.m2il.vv.mutation.mutator;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.expr.ExprEditor;

import java.io.IOException;

public interface Mutator {
    void replace(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException;
}
