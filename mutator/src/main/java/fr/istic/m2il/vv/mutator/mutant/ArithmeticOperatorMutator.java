package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.util.Utils;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.*;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

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
            while (iterator.hasNext()) {
                HashMap<Integer, Integer> m = new HashMap<>();
                int pos = iterator.next();
                switch (iterator.byteAt(pos)) {
                    case Opcode.IADD:
                        m.put(pos, Opcode.ISUB);
                        break;

                    case Opcode.ISUB:
                        m.put(pos, Opcode.IADD);
                        break;

                    case Opcode.FADD:
                        m.put(pos, Opcode.FSUB);
                        break;

                    case Opcode.FSUB:
                        m.put(pos, Opcode.FADD);
                        break;

                    case Opcode.LADD:
                        m.put(pos, Opcode.LSUB);
                        break;

                    case Opcode.LSUB:
                        m.put(pos, Opcode.LADD);
                        break;

                    case Opcode.DADD:
                        m.put(pos, Opcode.DSUB);
                        break;

                    case Opcode.DSUB:
                        m.put(pos, Opcode.DADD);
                        break;

                    case Opcode.IMUL:
                        m.put(pos, Opcode.IDIV);
                        break;

                    case Opcode.IDIV:
                        m.put(pos, Opcode.IMUL);;
                        break;

                    case Opcode.FDIV:
                        m.put(pos, Opcode.FMUL);
                        break;

                    case Opcode.FMUL:
                        m.put(pos, Opcode.FDIV);
                        break;

                    case Opcode.LMUL:
                        m.put(pos, Opcode.LDIV);
                        break;

                    case Opcode.LDIV:
                        m.put(pos, Opcode.LMUL);
                        break;

                    case Opcode.DMUL:
                        m.put(pos, Opcode.DDIV);
                        break;

                    case Opcode.DDIV:
                        m.put(pos, Opcode.DMUL);
                        break;
                }
                if(!m.isEmpty()){
                    System.out.println("size " + m.get(pos));
                    iterator.writeByte(m.get(pos), pos);
                    logger.info("Mutating  {}", getClass().getName() + "Mutate " + ctMethod.getName() + " on " +targetProject.getLocation());
                    Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                    testRunner.run();
                    this.revert();
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
