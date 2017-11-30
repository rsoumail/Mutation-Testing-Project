package fr.istic.m2il.vv.mutation.mutator;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.expr.ExprEditor;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.IOException;

public interface Mutator {
    void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, NotFoundException, MavenInvocationException;

    void revert() throws CannotCompileException, IOException, BadBytecode;
}
