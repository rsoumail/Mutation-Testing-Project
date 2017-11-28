package fr.istic.m2il.vv.mutation.mutator;

import fr.istic.m2il.vv.mutation.Utils;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.bytecode.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ArithmeticOperatorMutator implements Mutator{

    private File inputPath;
    private CtMethod original;
    private HashMap<Integer, Integer> bytes = new HashMap<>();

    public ArithmeticOperatorMutator(File inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public void mutate( CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException {

        if(ctMethod.getDeclaringClass().isInterface()){
            original = ctMethod;
        }

        if(!ctMethod.getDeclaringClass().isInterface()){
            original = ctMethod;
            ctMethod.getDeclaringClass().defrost();
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute code = methodInfo.getCodeAttribute();
            CodeIterator iterator = code.iterator();

            while (iterator.hasNext()) {
                int pos = iterator.next();
                switch (iterator.byteAt(pos)) {

                    case Opcode.IADD:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.ISUB, pos);
                        break;

                    case Opcode.ISUB:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.IADD, pos);
                        break;

                    case Opcode.FADD:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.FSUB, pos);
                        break;

                    case Opcode.FSUB:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.FADD, pos);
                        break;

                    case Opcode.LADD:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.LSUB, pos);
                        break;

                    case Opcode.LSUB:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.LADD, pos);
                        break;

                    case Opcode.DADD:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.DSUB, pos);
                        break;

                    case Opcode.DSUB:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.DADD, pos);
                        break;

                    case Opcode.IMUL:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.IDIV, pos);
                        break;

                    case Opcode.IDIV:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.IMUL, pos);
                        break;

                    case Opcode.FDIV:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.FMUL, pos);
                        break;

                    case Opcode.FMUL:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.FDIV, pos);
                        break;

                    case Opcode.LMUL:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.LDIV, pos);
                        break;

                    case Opcode.LDIV:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.LMUL, pos);
                        break;

                    case Opcode.DMUL:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.DDIV, pos);
                        break;

                    case Opcode.DDIV:
                        bytes.put(pos, iterator.byteAt(pos));
                        iterator.writeByte(Opcode.DMUL, pos);
                        break;

                }


                Utils.write(ctMethod.getDeclaringClass(), this.inputPath);

            }
        }

    }

    @Override
    public void revert() throws CannotCompileException, IOException, BadBytecode {
        if(!original.getDeclaringClass().isInterface()) {
            original.getDeclaringClass().defrost();
            MethodInfo methodInfo = original.getMethodInfo();
            CodeAttribute code = methodInfo.getCodeAttribute();
            CodeIterator iterator = code.iterator();



            while (iterator.hasNext()) {
                int pos = iterator.next();
                if(bytes.containsKey(pos))
                    iterator.writeByte(bytes.get(pos), pos);

            }

        }

        Utils.write(original.getDeclaringClass(), this.inputPath);
    }



}
