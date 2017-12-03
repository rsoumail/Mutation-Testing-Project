package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.util.Utils;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.*;
import javassist.bytecode.BadBytecode;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BooleanMethodMutator implements Mutator{

    private static Logger logger = LoggerFactory.getLogger(VoidMethodMutator.class);
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
            MVNRunner testRunner = new MVNRunner(this.targetProject.getPom().getAbsolutePath() , "surefire:test", "-Dtest=" + this.targetProject.getTestClassNameOfClass(ctMethod.getDeclaringClass().getName()));
            Boolean returnValue = false;
            ctMethod.setBody("{ return " +  returnValue + ";}");
            logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
            Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
            testRunner.run();
            this.revert();
        }

    }

    @Override
    public void revert() throws CannotCompileException, IOException {
        logger.info("Reverting  {}", getClass().getName() + "Revert " + modified.getName() + " on " +targetProject.getLocation());
        modified.getDeclaringClass().defrost();
        modified.setBody(original, null);
        Utils.write(modified.getDeclaringClass(), this.targetProject.getClassesLocation());
    }
}
