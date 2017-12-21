package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.report.Report;
import fr.istic.m2il.vv.mutator.report.ReportService;
import fr.istic.m2il.vv.mutator.util.Utils;
import fr.istic.m2il.vv.mutator.report.Report;
import fr.istic.m2il.vv.mutator.report.ReportService;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.*;
import org.apache.maven.shared.invoker.InvocationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class ComparisonOperatorMutator implements Mutator {

	private static Logger logger = LoggerFactory.getLogger(ComparisonOperatorMutator.class);
	private CtMethod original;
	private CtMethod modified;
	private TargetProject targetProject;
	private MutantType mutantType = MutantType.COMPARISON_MUTATOR;

	public ComparisonOperatorMutator(TargetProject targetProject) {
		this.targetProject = targetProject;
	}

	@Override
	public void mutate(CtMethod ctMethod)
			throws CannotCompileException, BadBytecode, IOException, MavenInvocationException {

		modified = ctMethod;
		original = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
		if (this.targetProject.getTestClassNameOfClass(ctMethod.getDeclaringClass().getName()) != null) {
			ctMethod.getDeclaringClass().defrost();
			MethodInfo methodInfo = ctMethod.getMethodInfo();
			if (methodInfo.getCodeAttribute() != null) {
				CodeAttribute code = methodInfo.getCodeAttribute();
				CodeIterator iterator = code.iterator();
				MVNRunner testRunner = new MVNRunner(this.targetProject.getPom().getAbsolutePath(), "surefire:test",
						"-Dtest=" + this.targetProject.getTestClassNameOfClass(ctMethod.getDeclaringClass().getName()));

				while (iterator.hasNext()) {
					HashMap<Integer, Integer> m = new HashMap<>();
					int pos = iterator.next();
					switch (iterator.byteAt(pos)) {
					// Replace operator < by <=
					case Opcode.IF_ICMPLT:
						// m.put(pos, Opcode.IF_ICMPGE);
						m.put(pos, Opcode.IF_ICMPLE);
						break;

					// Replace operator > by >=
					case Opcode.IF_ICMPGT:
						// m.put(pos, Opcode.IF_ICMPLE);
						m.put(pos, Opcode.IF_ICMPGE);
						break;

					// Replace operator <= by <
					case Opcode.IF_ICMPLE:
						// m.put(pos, Opcode.IF_ICMPGT);
						m.put(pos, Opcode.IF_ICMPLT);
						break;

					// Replace operator >= by >
					case Opcode.IF_ICMPGE:
						m.put(pos, Opcode.IF_ICMPGT);
						break;
					}

					if (!m.isEmpty()) {
						System.out.println("size " + m.get(pos));
						iterator.writeByte(m.get(pos), pos);
						/*
						 * logger.info("Mutating {}", getClass().getName() + "Mutate " +
						 * ctMethod.getName() + "" + "on " + targetProject.getLocation());
						 */
						Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
						Report report = new Report(MutantState.STARTED, getClass().getName() + " Mutate "
								+ ctMethod.getName() + " on class " + ctMethod.getDeclaringClass().getName());
						report.setMutatedClassName(ctMethod.getDeclaringClass().getName());
						report.setMutatedMethodName(ctMethod.getName());
						report.setMutatedLine(methodInfo.getLineNumber(pos));
						report.setTestsRan(new Integer(Utils.testsCasesInTestClass(
								this.targetProject.getTestClassOfClass(ctMethod.getDeclaringClass().getName()))));
						report.setTestClassRun(
								this.targetProject.getTestClassNameOfClass(ctMethod.getDeclaringClass().getName()));

						ReportService.getInstance().newRanTest();
						InvocationResult testResult = testRunner.run();
						if (testResult.getExitCode() != 0) {
							report.setMutantState(MutantState.KILLED);
						} else {
							report.setMutantState(MutantState.SURVIVED);
						}

						if (ReportService.getInstance().getReports().get(this) == null) {
							List<Report> mutantReportList = new ArrayList<>();
							mutantReportList.add(report);
							ReportService.getInstance().getReports().put(this, mutantReportList);
						} else {
							ReportService.getInstance().getReports().get(this).add(report);
						}

						this.revert();
					}
				}

			}
		}

	}

	@Override
	public void revert() throws CannotCompileException, IOException, BadBytecode {

		logger.info("Reverting  {}",
				getClass().getName() + "Revert " + modified.getName() + " on " + targetProject.getLocation());
		modified.getDeclaringClass().defrost();
		modified.setBody(original, null);
		Utils.write(modified.getDeclaringClass(), this.targetProject.getClassesLocation());
	}

	public MutantType getMutantType() {
		return mutantType;
	}

	public void setMutantType(MutantType mutantType) {
		this.mutantType = mutantType;
	}
}
