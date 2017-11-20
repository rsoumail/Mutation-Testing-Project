package fr.istic.m2il.vv.mutation;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.expr.ExprEditor;

public class BCArithmeticEditor extends AbstractEditor{


    public BCArithmeticEditor() {

    }

    public void replace(CtMethod ctMethod) throws CannotCompileException {
        try {
            ctMethod.instrument(new ExprEditor(){

            });
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
