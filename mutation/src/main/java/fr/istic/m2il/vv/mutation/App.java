package fr.istic.m2il.vv.mutation;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javassist.*;

public class App {
	public static void main(String[] args) {
		try {
			ClassPool pool = ClassPool.getDefault();
			Loader loader = new Loader(pool);

			Translator logger = new Translator() {
				public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
					System.out.println("Starting...");
				}

				public void onLoad(ClassPool classPool, String classname)
						throws NotFoundException, CannotCompileException {
					System.out.println("Loading...: " + classname);
				}
			};

			loader.addTranslator(pool, logger);
			pool.appendClassPath("../input/target/classes");
			pool.appendClassPath("../input/target/test-classes");
			loader.run("fr.istic.m2il.vv.input.App", args);

			JUnitCore jUnitCore = new JUnitCore();

			// Liste des classesSubstraction
			 String classes[] = {
					 "fr.istic.m2il.vv.input.Operation", "fr.istic.m2il.vv.input.Addition",
					 "fr.istic.m2il.vv.input.Division", "fr.istic.m2il.vv.input.Multiplication",
					 "fr.istic.m2il.vv.input.Substraction",
			 "fr.istic.m2il.vv.input.AdditionTest",
			 "fr.istic.m2il.vv.input.DivisionTest",
			 "fr.istic.m2il.vv.input.MultiplicationTest",
			 "fr.istic.m2il.vv.input.SubstractionTest"
			 };
//			String classes[] = { "fr.istic.m2il.vv.input.Operation", "fr.istic.m2il.vv.input.Addition",
//					"fr.istic.m2il.vv.input.Division", "fr.istic.m2il.vv.input.Multiplication",
//					"fr.istic.m2il.vv.input.Substraction" };

			for (CtClass ctClass : pool.get(classes)) {
				if(ctClass.getName().contains("Test")) {
					System.out.println("test: " + ctClass.getName());
					Request request = Request.aClass(ctClass.toClass());
					Result r = jUnitCore.run(request);
					 System.out.println("Tests ran : " + r.getRunCount() + ", failed : " +
					 r.getFailureCount());
					 for(Failure f: r.getFailures()) {
						 System.out.println(f.getMessage());
					 }
				}
				

				// if(!(ctClass.getName().contains("fr.istic.m2il.vv.input.Operation"))) {
				// Liste des methodes
				CtMethod[] methodes = ctClass.getDeclaredMethods();
				// Boucle sur les méthodes de chaque classe
				for (CtMethod method : methodes) {
					final CtClass returnType = method.getReturnType();

					if (returnType.equals(CtClass.voidType)) {
						// méthode de type void
						System.out.println("name: " + method.getName() + ":: void");
					} else if (returnType.equals(CtClass.doubleType)) {
						// méthode de type double
						System.out.println("name: " + method.getName() + ":: double");
						// doubleMutation(ctClass, method, ctClass.getName());
					}
				}
				// }

			}

		} catch (Throwable exc) {
			System.out.println("Oh, no! Something went wrong.");
			System.out.println(exc.getMessage());
			exc.printStackTrace();
		}
	}

	private static void doubleMutation(CtClass myClass, CtMethod myMethod, String myClassName)
			throws NotFoundException, CannotCompileException {
		double value = 12.25;
		String mutate = "{ return " + value + ";}";
		myMethod.setBody(mutate);

		System.out.println("method mutated => " + myMethod.getName());
	}
}
