package fr.istic.m2il.vv.mutation.mutator;

import fr.istic.m2il.vv.mutation.Utils;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;

import java.io.IOException;

public class BooleanMethodMutator implements Mutator{

    private String inputPath;

    public BooleanMethodMutator(String inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public void replace(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException {
        ctMethod.getDeclaringClass().defrost();
        Boolean returnValue = false;
        ctMethod.setBody("{ return " +  returnValue + ";}");
        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
    }
}
