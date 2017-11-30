package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.Utils;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ComparisonOperatorMutator implements Mutator{


    private File inputPath;
    private CtMethod original;
    private HashMap<Integer, Integer> bytes = new HashMap<>();

    public ComparisonOperatorMutator(File inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, NotFoundException {
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute code = methodInfo.getCodeAttribute();
        CodeIterator iterator = code.iterator();

        while (iterator.hasNext()) {
            int pos = iterator.next();
            switch (iterator.byteAt(pos)) {

            }

            Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
        }
    }

    @Override
    public void revert() throws CannotCompileException, IOException, BadBytecode {

    }
}
