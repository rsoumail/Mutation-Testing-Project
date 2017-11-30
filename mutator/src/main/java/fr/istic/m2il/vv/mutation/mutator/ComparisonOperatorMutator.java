package fr.istic.m2il.vv.mutation.mutator;

import fr.istic.m2il.vv.mutation.Utils;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ComparisonOperatorMutator implements Mutator{


    private File inputPath;
    private CtMethod original;
   
    public ComparisonOperatorMutator(File inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, NotFoundException {
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute code = methodInfo.getCodeAttribute();
        CodeIterator iterator = code.iterator();

        while (iterator.hasNext()) {
            int pos = iterator.next();
            switch (iterator.byteAt(pos)) {
            	//Replace operator < by <=
            case Opcode.IF_ICMPLT:
            	iterator.writeByte(Opcode.IF_ICMPLE, pos);
                break;
               //Replace operator > by >=
            case Opcode.IF_ICMPGT:
            	iterator.writeByte(Opcode.IF_ICMPGE, pos);
            	break;
            	//Replace operator <= by <
            case Opcode.IF_ICMPLE:
            	iterator.writeByte(Opcode.IF_ICMPLT, pos);
            	break;
            	//Replace operator >= by >
            case Opcode.IF_ICMPGE:
            	iterator.writeByte(Opcode.IF_ICMPGT, pos);
            	break;
            	
//            case Opcode.IF_DCMPLT:
//            	iterator.writeByte(Opcode.IF_DCMPLE, pos);
//                break;
//                
//            case Opcode.IF_DCMPGT:
//            	iterator.writeByte(Opcode.IF_DCMPGE, pos);
//            	break;  
//            	
//            case Opcode.IF_DCMPLE:
//            	iterator.writeByte(Opcode.IF_DCMPLT, pos);
//            	break;
//            
//            case Opcode.IF_DCMPGE:
//            	iterator.writeByte(Opcode.IF_DCMPGT, pos);
//            	break;
//            	
//            case Opcode.IF_LCMPLT:
//            	iterator.writeByte(Opcode.IF_LCMPLE, pos);
//                break;
//                
//            case Opcode.IF_LCMPGT:
//            	iterator.writeByte(Opcode.IF_LCMPGE, pos);
//            	break;  
//            	
//            case Opcode.IF_LCMPLE:
//            	iterator.writeByte(Opcode.IF_LCMPLT, pos);
//            	break;
//            
//            case Opcode.IF_LCMPGE:
//            	iterator.writeByte(Opcode.IF_LCMPGT, pos);
//            	break;
//            	
//            case Opcode.IF_FCMPLT:
//            	iterator.writeByte(Opcode.IF_FCMPLE, pos);
//                break;
//                
//            case Opcode.IF_FCMPGT:
//            	iterator.writeByte(Opcode.IF_FCMPGE, pos);
//            	break;  
//            	
//            case Opcode.IF_FCMPLE:
//            	iterator.writeByte(Opcode.IF_FCMPLT, pos);
//            	break;
//            
//            case Opcode.IF_FCMPGE:
//            	iterator.writeByte(Opcode.IF_FCMPGT, pos);
//            	break;
             
            }

            Utils.write(ctMethod.getDeclaringClass(), this.inputPath);
        }
    }

    @Override
    public void revert() throws CannotCompileException, IOException, BadBytecode {
    	
    }
}
