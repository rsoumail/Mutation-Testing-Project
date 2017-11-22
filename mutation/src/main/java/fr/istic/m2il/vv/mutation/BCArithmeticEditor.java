package fr.istic.m2il.vv.mutation;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.*;

import java.io.IOException;

public class BCArithmeticEditor implements Mutator {

    private String inputPath;
    private int oldOpcode, newOpcode;

    public BCArithmeticEditor(String inputPath, int oldOpcode, int newOpcode) {
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
                System.out.println("try");
                write(ctMethod.getDeclaringClass());
                System.out.println("written ");
            }
        }
    }

    public void write(CtClass ctClass) throws CannotCompileException, IOException {
        System.out.println("Try write file");
        ctClass.writeFile(inputPath);
        System.out.println("Finish written");
    }
}
