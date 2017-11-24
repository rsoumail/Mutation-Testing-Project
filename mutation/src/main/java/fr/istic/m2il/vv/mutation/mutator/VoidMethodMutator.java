package fr.istic.m2il.vv.mutation.mutator;

import fr.istic.m2il.vv.mutation.Utils;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;

import java.io.IOException;

public class VoidMethodMutator implements Mutator {

    private String inputPath;

    public VoidMethodMutator(String inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public void replace(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException {
        ctMethod.getDeclaringClass().defrost();
        ctMethod.setBody("{}");
        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
    }

}
