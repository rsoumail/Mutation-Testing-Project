package fr.istic.m2il.vv.mutation.mutator;

import fr.istic.m2il.vv.mutation.Utils;
import fr.istic.m2il.vv.mutation.report.PITRunner;
import fr.istic.m2il.vv.mutation.testrunner.runner.MVNRunner;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.*;
import org.apache.maven.shared.invoker.MavenInvocationException;

import java.io.File;
import java.io.IOException;

public class ArithmeticOperatorMutator implements Mutator{

    private File inputPath;
    private CtMethod original;
    private CtMethod modified;
    private File pomFil;

    public ArithmeticOperatorMutator(File inputPath, File pomFil) {
        this.inputPath = inputPath;
        this.pomFil = pomFil;
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
            MVNRunner testRunner = new MVNRunner(this.pomFil.getAbsolutePath() + "/pom.xml", "test");
//            PITRunner pitRunner = new PITRunner();
            while (iterator.hasNext()) {
                int pos = iterator.next();
                switch (iterator.byteAt(pos)) {
                    case Opcode.IADD:
                        iterator.writeByte(Opcode.ISUB, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.ISUB:
                        iterator.writeByte(Opcode.IADD, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.FADD:
                        iterator.writeByte(Opcode.FSUB, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.FSUB:
                        iterator.writeByte(Opcode.FADD, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.LADD:
                        iterator.writeByte(Opcode.LSUB, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.LSUB:
                        iterator.writeByte(Opcode.LADD, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.DADD:
                        iterator.writeByte(Opcode.DSUB, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        //pitRunner.run();
                        break;

                    case Opcode.DSUB:
                        iterator.writeByte(Opcode.DADD, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        //pitRunner.run();
                        break;

                    case Opcode.IMUL:
                        iterator.writeByte(Opcode.IDIV, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.IDIV:
                        iterator.writeByte(Opcode.IMUL, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.FDIV:
                        iterator.writeByte(Opcode.FMUL, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.FMUL:
                        iterator.writeByte(Opcode.FDIV, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.LMUL:
                        iterator.writeByte(Opcode.LDIV, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.LDIV:
                        iterator.writeByte(Opcode.LMUL, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.DMUL:
                        iterator.writeByte(Opcode.DDIV, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
                        testRunner.run();
                        this.revert();
                        break;

                    case Opcode.DDIV:
                        iterator.writeByte(Opcode.DMUL, pos);
                        Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
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

        modified.getDeclaringClass().defrost();
        modified.setBody(original, null);
        Utils.write(modified.getDeclaringClass(), this.inputPath);

    }



}
