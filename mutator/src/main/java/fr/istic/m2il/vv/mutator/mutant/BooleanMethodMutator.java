package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.Utils;
import fr.istic.m2il.vv.mutator.target.TargetProject;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.*;
import javassist.bytecode.BadBytecode;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.IOException;

public class BooleanMethodMutator implements Mutator{


    private CtMethod original;
    private CtMethod modified;
    private TargetProject targetProject;

    public BooleanMethodMutator(TargetProject targetProject) {
        this.targetProject = targetProject;
    }

    @Override
    public void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, NotFoundException, MavenInvocationException {
        modified = ctMethod;
        original = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        if(!ctMethod.getDeclaringClass().isInterface() && ctMethod.getReturnType().equals(CtClass.booleanType)){
            ctMethod.getDeclaringClass().defrost();
            MVNRunner testRunner = new MVNRunner(this.targetProject.getPom().getAbsolutePath() , "test");
            Boolean returnValue = false;
            ctMethod.setBody("{ return " +  returnValue + ";}");
            Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
            testRunner.run();
            this.revert();
        }

    }

    @Override
    public void revert() throws CannotCompileException, IOException {
        modified.getDeclaringClass().defrost();
        modified.setBody(original, null);
        Utils.write(modified.getDeclaringClass(), this.targetProject.getClassesLocation());
    }
}
