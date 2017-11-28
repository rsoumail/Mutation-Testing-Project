package fr.istic.m2il.vv.mutation;

import fr.istic.m2il.vv.mutation.mutator.ArithmeticOperatorMutator;
import fr.istic.m2il.vv.mutation.mutator.Mutator;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;

import java.io.File;
import java.io.IOException;

public class MutatorExecutor {

    public void execute(Mutator mutator, File targetProject) throws CannotCompileException, BadBytecode, NotFoundException, IOException {
        /*for(CtClass ctClass: pool.get(classes)){
            ctClass.defrost();
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for(CtMethod method: methods){

                mutator.mutate(method);
                //mutators.add(bcArithmeticEditor);
            }

        }*/
    }
}
