package fr.istic.m2il.vv.mutator;

import fr.istic.m2il.vv.mutator.common.CheckConfigurtionProperties;
import fr.istic.m2il.vv.mutator.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutator.common.TimeWatch;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.config.MutatingProperties;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.mutant.*;
import fr.istic.m2il.vv.mutator.report.HtmlStrategy;
import fr.istic.m2il.vv.mutator.report.ReportService;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class App {

	public static void main(String[] args) throws Exception {

		if (args.length != 1) {
			System.err.println("Veuillez passez le fichier de configuration en paramètre");
			System.exit(0);
		} else {
			ApplicationProperties applicationProperties = ApplicationProperties.getInstance(new File(args[0]));

			ClassLoaderParser classLoaderParser = new ClassLoaderParser();

			TargetProject targetProject = TargetProject.getInstance();

			TimeWatch watcher = TimeWatch.start();

			targetProject.setLocation(
					new File(applicationProperties.getApplicationPropertiesFile().getProperty("target.project")));
			targetProject.setPom(new File(targetProject.getLocation().getAbsolutePath() + "/pom.xml"));
			targetProject
					.setReportDir(applicationProperties.getApplicationPropertiesFile().getProperty("report.dir") != null
							? new File(applicationProperties.getApplicationPropertiesFile().getProperty("report.dir"))
							: new File(targetProject.getLocation() + "/target"));
			targetProject.setClasses(
					classLoaderParser.getClassesFromDirectory(targetProject.getClassesLocation().getPath()));
			targetProject
					.setTests(classLoaderParser.getClassesFromDirectory(targetProject.getTestsLocation().getPath()));


			CheckConfigurtionProperties checker = new CheckConfigurtionProperties(applicationProperties);
			checker.checksConfig();

			JavaAssistHelper javaAssistHelper = JavaAssistHelper.getInstance();

			ReportService.getInstance().setReportStrategy(new HtmlStrategy());
			ReportService.getInstance().setScanClassesTime(watcher.time(TimeUnit.SECONDS));

			watcher.reset();

			MutatorExecutorHelper mutatorExecutorHelper = new MutatorExecutorHelper();
			MutatorExecutor mutatorExecutor = new MutatorExecutor(javaAssistHelper);
			for (MutantType mutant : MutatingProperties.mutantsToAnalysis()) {
				Mutator mutator = (Mutator) mutatorExecutorHelper.getInstanceOf(mutant, targetProject);
				mutatorExecutor.execute(mutator, targetProject);
			}

			ReportService.getInstance().setRunMutationAnalysisTime(watcher.time(TimeUnit.SECONDS));

			ReportService.getInstance().doReport();
			ReportService.getInstance().toGraphicReport();
		}

	}

}
