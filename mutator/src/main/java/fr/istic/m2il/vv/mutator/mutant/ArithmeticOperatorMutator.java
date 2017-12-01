package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.util.Utils;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.*;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ArithmeticOperatorMutator implements Mutator{

    private static Logger logger = LoggerFactory.getLogger(ArithmeticOperatorMutator.class);
    private CtMethod original;
    private CtMethod modified;
    private TargetProject targetProject;

    public ArithmeticOperatorMutator(TargetProject targetProject) {
        this.targetProject = targetProject;
    }

    @Override
    public void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, MavenInvocationException {
        modified = ctMethod;
        original = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);

        if(!ctMethod.getDeclaringClass().isInterface()){
            ctMethod.getDeclaringClass().defrost();
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute code = methodInfo.getCodeAttribute();
            CodeIterator iterator = code.iterator();
            MVNRunner testRunner = new MVNRunner(this.targetProject.getPom().getAbsolutePath() , "test");
//            PITRunner pitRunner = new PITRunner();
            while (iterator.hasNext()) {
                int pos = iterator.next();
                switch (iterator.byteAt(pos)) {
                    case Opcode.IADD:
                        iterator.writeByte(Opcode.ISUB, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.ISUB:
                        iterator.writeByte(Opcode.IADD, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.FADD:
                        iterator.writeByte(Opcode.FSUB, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.FSUB:
                        iterator.writeByte(Opcode.FADD, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.LADD:
                        iterator.writeByte(Opcode.LSUB, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.LSUB:
                        iterator.writeByte(Opcode.LADD, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.DADD:
                        iterator.writeByte(Opcode.DSUB, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        //pitRunner.run();
                        break;

                    case Opcode.DSUB:
                        iterator.writeByte(Opcode.DADD, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        //pitRunner.run();
                        break;

                    case Opcode.IMUL:
                        iterator.writeByte(Opcode.IDIV, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.IDIV:
                        iterator.writeByte(Opcode.IMUL, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.FDIV:
                        iterator.writeByte(Opcode.FMUL, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.FMUL:
                        iterator.writeByte(Opcode.FDIV, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.LMUL:
                        iterator.writeByte(Opcode.LDIV, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.LDIV:
                        iterator.writeByte(Opcode.LMUL, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.DMUL:
                        iterator.writeByte(Opcode.DDIV, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.DDIV:
                        iterator.writeByte(Opcode.DMUL, pos);
                        logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                        Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                        testRunner.run();
                        this.revert();
                        //pitRunner.run();
                        break;
                }
            }
        }

    }

    @Override
    public void revert() throws CannotCompileException, IOException, BadBytecode {

        logger.info("Reverting  {}", getClass().getName() + "Revert " + modified.getName() + " on " +targetProject.getLocation());
        modified.getDeclaringClass().defrost();
        modified.setBody(original, null);
        Utils.write(modified.getDeclaringClass(), this.targetProject.getClassesLocation());

    }



}
