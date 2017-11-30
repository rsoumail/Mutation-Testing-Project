package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.Utils;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.maven.shared.invoker.MavenInvocationException;

public class ComparisonOperatorMutator implements Mutator {

	private CtMethod original;
	private CtMethod modified;
	private TargetProject targetProject;

	public ComparisonOperatorMutator(TargetProject targetProject) {
		this.targetProject = targetProject;
	}

	@Override
	public void mutate(CtMethod ctMethod)
			throws CannotCompileException, BadBytecode, IOException, MavenInvocationException {

		modified = ctMethod;
		original = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);

		if (!ctMethod.getDeclaringClass().isInterface()) {
			ctMethod.getDeclaringClass().defrost();
			MethodInfo methodInfo = ctMethod.getMethodInfo();
			CodeAttribute code = methodInfo.getCodeAttribute();
			CodeIterator iterator = code.iterator();
			MVNRunner testRunner = new MVNRunner(this.targetProject.getPom().getAbsolutePath(), "test");

			while (iterator.hasNext()) {
				int pos = iterator.next();
				switch (iterator.byteAt(pos)) {
				// Replace operator < by <=
				case Opcode.IF_ICMPLT:
					iterator.writeByte(Opcode.IF_ICMPLE, pos);
					Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
					testRunner.run();
					this.revert();
					break;
				// Replace operator > by >=
				case Opcode.IF_ICMPGT:
					iterator.writeByte(Opcode.IF_ICMPGE, pos);
					Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
					testRunner.run();
					this.revert();
					break;
				// Replace operator <= by <
				case Opcode.IF_ICMPLE:
					iterator.writeByte(Opcode.IF_ICMPLT, pos);
					Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
					testRunner.run();
					this.revert();
					break;
				// Replace operator >= by >
				case Opcode.IF_ICMPGE:
					iterator.writeByte(Opcode.IF_ICMPGT, pos);
					Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
					testRunner.run();
					this.revert();
					break;
				// > => >=
				case Opcode.IFGT:
					System.out.println("Case 1 : " + Opcode.IFGT + " on method " + ctMethod.getName() + " on class "
							+ ctMethod.getDeclaringClass().getName());
					iterator.writeByte(Opcode.IFGE, pos);
					Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
					testRunner.run();
					this.revert();
					break;
				// < => <=
				case Opcode.IFLT:
					System.out.println("Case 2 : " + Opcode.IFLT + " on method " + ctMethod.getName() + " on class "
							+ ctMethod.getDeclaringClass().getName());
					iterator.writeByte(Opcode.IFLE, pos);
					Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
					testRunner.run();
					this.revert();
					break;

				case Opcode.IFGE:
					System.out.println("Case 3 : " + Opcode.IFGE + " on method " + ctMethod.getName() + " on class "
							+ ctMethod.getDeclaringClass().getName());
					iterator.writeByte(Opcode.IFGT, pos);
					Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
					testRunner.run();
					this.revert();
					break;

				case Opcode.IFLE:
					System.out.println("Case 4 : " + Opcode.IFLE + " on method " + ctMethod.getName() + " on class "
							+ ctMethod.getDeclaringClass().getName());
					iterator.writeByte(Opcode.IFLT, pos);
					Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
					testRunner.run();
					this.revert();
					break;

				}

			}

		}
	}

	@Override
	public void revert() throws CannotCompileException, IOException, BadBytecode {
		modified.getDeclaringClass().defrost();
		modified.setBody(original, null);
		Utils.write(modified.getDeclaringClass(), this.targetProject.getClassesLocation());
	}
}
