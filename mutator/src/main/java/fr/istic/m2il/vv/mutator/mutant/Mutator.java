package fr.istic.m2il.vv.mutator.mutant;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.IOException;

public interface Mutator {
    void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, NotFoundException, MavenInvocationException;
}
