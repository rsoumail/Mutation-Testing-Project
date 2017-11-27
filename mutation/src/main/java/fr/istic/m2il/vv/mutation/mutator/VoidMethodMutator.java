package fr.istic.m2il.vv.mutation.mutator;

import fr.istic.m2il.vv.mutation.Utils;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;

import java.io.File;
import java.io.IOException;

public class VoidMethodMutator implements Mutator {

    private File inputPath;
    private CtMethod original;

    public VoidMethodMutator(File inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, NotFoundException {
        if(ctMethod.getReturnType().equals(CtClass.voidType)){
            original = ctMethod;
            ctMethod.getDeclaringClass().defrost();
            ctMethod.setBody("{}");
            Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
        }
    }

    @Override
    public void revert() throws CannotCompileException, IOException {
        Utils.write(original.getDeclaringClass(), this.inputPath);
    }

}
