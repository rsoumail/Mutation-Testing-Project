package fr.istic.m2il.vv.mutation;

import java.io.IOException;

import fr.istic.m2il.vv.mutation.mutator.Mutator;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;

public class BCComparisonMutator implements Mutator {

	private String input;
	private int oldOpcode, newOpcode;

	public BCComparisonMutator(String input, int oldOpcode, int newOpcode) {
		this.input = input;
		this.oldOpcode = oldOpcode;
		this.newOpcode = newOpcode;
	}

	@Override
	public void replace(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException {

		MethodInfo methodInfo = ctMethod.getMethodInfo();
		CodeAttribute code = methodInfo.getCodeAttribute();
		CodeIterator iterator = code.iterator();

		while (iterator.hasNext()) {
			int pos = iterator.next();
			if (iterator.byteAt(pos) == this.oldOpcode) {
				iterator.writeByte(this.newOpcode, pos);
				write(ctMethod.getDeclaringClass());
			}
		}
	}

	public void write(CtClass ctClass) throws CannotCompileException, IOException {
		ctClass.writeFile(input);
	}

}
