package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.Utils;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.*;
import javassist.bytecode.BadBytecode;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.File;
import java.io.IOException;

public class VoidMethodMutator implements Mutator {

    private File inputPath;
    private CtMethod original;
    private CtMethod modified;
    private File pomFil;

    public VoidMethodMutator(File inputPath, File pomFil) {
        this.inputPath = inputPath;
        this.pomFil = pomFil;
    }

    @Override
    public void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, NotFoundException, MavenInvocationException {

        modified = ctMethod;
        original = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);

        if(!ctMethod.getDeclaringClass().isInterface() && ctMethod.getReturnType().equals(CtClass.voidType)){
            ctMethod.getDeclaringClass().defrost();
            MVNRunner testRunner = new MVNRunner(this.pomFil.getAbsolutePath() + "/pom.xml", "test");
            ctMethod.setBody("{}");
            Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
            testRunner.run();
            this.revert();
        }

    }

    @Override
    public void revert() throws CannotCompileException, IOException {
        modified.getDeclaringClass().defrost();
        modified.setBody(original, null);
        Utils.write(modified.getDeclaringClass(), this.inputPath);
    }

}
