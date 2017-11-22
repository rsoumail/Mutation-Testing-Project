package fr.istic.m2il.vv.mutation;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.*;

import java.io.IOException;

public class BCArithmeticEditorMutator implements Mutator {

    private String inputPath;
    private int oldOpcode, newOpcode;

    public BCArithmeticEditorMutator(String inputPath, int oldOpcode, int newOpcode) {
        this.inputPath = inputPath;
        this.oldOpcode = oldOpcode;
        this.newOpcode = newOpcode;
    }

    @Override
    public void replace( CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException {

        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute code = methodInfo.getCodeAttribute();
        CodeIterator iterator = code.iterator();

        while (iterator.hasNext()) {
            int pos = iterator.next();
            if(iterator.byteAt(pos) == this.oldOpcode){
                iterator.writeByte(this.newOpcode, pos);
                write(ctMethod.getDeclaringClass());
            }
        }
    }

    public void write(CtClass ctClass) throws CannotCompileException, IOException {
        ctClass.writeFile(inputPath);
    }
}
