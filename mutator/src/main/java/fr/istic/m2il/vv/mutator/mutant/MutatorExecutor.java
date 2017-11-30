package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.common.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.*;
import javassist.bytecode.BadBytecode;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MutatorExecutor {

    private static Logger logger = LoggerFactory.getLogger(MutatorExecutor.class);
    private JavaAssistHelper javaAssistHelper;

    public MutatorExecutor(JavaAssistHelper javaAssistHelper) {
        this.javaAssistHelper = javaAssistHelper;
    }



    public void execute(Mutator mutator, TargetProject targetProject) throws CannotCompileException, BadBytecode, NotFoundException, IOException, MavenInvocationException {
        logger.info("Execute mutant  {}", mutator.getClass().getName() + " on " +targetProject.getLocation());
        for(CtClass ctClass: this.javaAssistHelper.getPool().get(targetProject.getClassesNames())){
            ctClass.defrost();
            CtMethod[] methods = ctClass.getDeclaredMethods();

            for(CtMethod method: methods){
                logger.info("Mutate  {}", method.getName() + " on " +targetProject.getLocation());
                logger.trace("Mutate  {}", method.getName() + " on " +targetProject.getLocation());
                System.out.println("methode " + method.getName());
                mutator.mutate(method);
            }

        }
    }
}
