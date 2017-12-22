package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.config.MutatingProperties;
import fr.istic.m2il.vv.mutator.report.Report;
import fr.istic.m2il.vv.mutator.report.ReportService;
import fr.istic.m2il.vv.mutator.util.Utils;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.*;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class ArithmeticOperatorMutator implements Mutator{

    private static Logger logger = LoggerFactory.getLogger(ArithmeticOperatorMutator.class);
    private CtMethod original;
    private CtMethod modified;
    private TargetProject targetProject;
    private MutantType mutantType = MutantType.ARITHMETIC_MUTATOR;
    private InvocationResult testResult;
    List<Future<InvocationResult>> results = null;

    public ArithmeticOperatorMutator(TargetProject targetProject) {
        this.targetProject = targetProject;
    }

    @Override
    public void mutate(CtMethod ctMethod) throws CannotCompileException, BadBytecode, IOException, MavenInvocationException, InterruptedException {
        modified = ctMethod;
        original = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);

        if(!ctMethod.getDeclaringClass().isInterface()){
            if(this.targetProject.getTestClassNameOfClass(ctMethod.getDeclaringClass().getName()) != null){
                ctMethod.getDeclaringClass().defrost();
                MethodInfo methodInfo = ctMethod.getMethodInfo();
                if(methodInfo.getCodeAttribute() != null){
                    CodeAttribute code = methodInfo.getCodeAttribute();
                    CodeIterator iterator = code.iterator();
                    MVNRunner testRunner = new MVNRunner(
                            this.targetProject.getPom().getAbsolutePath() , "surefire:test", "-Dtest=" +
                            this.targetProject.getTestClassNameOfClass(ctMethod.getDeclaringClass().getName())
                    );

                    while (iterator.hasNext()) {
                        boolean timeout = false;
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
                            iterator.writeByte(m.get(pos), pos);
                            logger.info("Mutating  {}", getClass().getName() + " Mutate " + ctMethod.getName() + " on " +ctMethod.getDeclaringClass().getName() + " of " +targetProject.getLocation());
                            Utils.write(ctMethod.getDeclaringClass(), this.targetProject.getClassesLocation());
                            Report report = new Report(MutantState.STARTED, getClass().getName() + " Mutate " + ctMethod.getName() + " on class " + ctMethod.getDeclaringClass().getName());
                            report.setMutatedClassName(ctMethod.getDeclaringClass().getName());
                            report.setMutatedMethodName(ctMethod.getName());
                            report.setMutatedLine(methodInfo.getLineNumber(pos));
                            report.setTestsRan(new Integer(Utils.testsCasesInTestClass(this.targetProject.getTestClassOfClass(ctMethod.getDeclaringClass().getName()))));
                            report.setTestClassRun(this.targetProject.getTestClassNameOfClass(ctMethod.getDeclaringClass().getName()));

                            ReportService.getInstance().newRanTest();

                            ExecutorService executor = Executors.newSingleThreadExecutor();

                            try {
                                results = executor.invokeAll(Arrays.asList(testRunner), MutatingProperties.getMutationTimeOut(), TimeUnit.SECONDS);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                timeout = true;
                                report.setMutantState(MutantState.TIMED_OUT);
                            }
                            finally {
                                if(timeout == false){
                                    testResult = testRunner.getInvocationResult();
                                    if( testResult != null){
                                        if(testRunner.getInvocationResult().getExitCode() != 0)
                                            report.setMutantState(MutantState.KILLED);
                                        else
                                            report.setMutantState(MutantState.SURVIVED);
                                    }
                                    else
                                        report.setMutantState(MutantState.TIMED_OUT);
                                }
                                executor.shutdown();
                                try {
                                    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                            ReportService.getInstance().addReport(this, report);

                            Utils.revert(modified, original, this, this.targetProject);
                        }
                    }
                }

            }
        }

    }

    public MutantType getMutantType() {
        return mutantType;
    }

    public InvocationResult getTestResult() {
        return testResult;
    }

}
