package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.util.Utils;
import freemarker.template.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HtmlStrategy implements IReportStrategy{

    private static final String TEMPLATES_DIRECTORY = "src/main/resources/templates";
    private static final String HTML_TEMPLATE = "report_template.html";
    private IReportService reportService;
    private Configuration configuration;
    private Template template;

    @Override
    public void configure(IReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public void execute() {

        try {
            this.configuration = initConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Map<String, Object> input = new HashMap<>();
        input.put("reports_by_mutator", ((ReportService)this.reportService).getReports());
        input.put("reports", ((ReportService)this.reportService).getReports().keySet());
        input.put("timings_scan_classpath", ((ReportService)this.reportService).getScanClassesTime()+1);
        input.put("timings_mutation_analysis", ((ReportService)this.reportService).getRunMutationAnalysisTime()+1);
        input.put("timings_total", ((ReportService)this.reportService).doTotalTime());
        input.put("statistic_generate", ((ReportService)this.reportService).getTotalMutationsNumber());
        input.put("statistic_killed", ((ReportService)this.reportService).getTotalKilledMutantsNumber());
        input.put("statistic_timedout", ((ReportService)this.reportService).getTimedOutMutantsNumber());
        input.put("statistic_rate", String.format("%.2f", ((ReportService)this.reportService).getRate(((ReportService)this.reportService).getTotalKilledMutantsNumber(), ((ReportService)this.reportService).getTotalMutationsNumber())));
        input.put("statistic_ran_tests", ((ReportService)this.reportService).getTotalTestsCasesRan());

        try {
            this.template = this.configuration.getTemplate(HTML_TEMPLATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringWriter stringWriter = new StringWriter();
        try {
            this.template.process(input, stringWriter);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer fileWriter = null;
        File reportFile;
        try {
            if(ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.timestamped") != null &&
                    ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.timestamped").trim().equals("true")){

                    Date now = new Date();
                    String time = DateFormat.getDateTimeInstance(
                            DateFormat.SHORT, DateFormat.SHORT).format(now);
                    String[] timeParts = time.split(" ");
                    String[] timePart1 = timeParts[0].split("/");

                    reportFile = new File(TargetProject.getInstance().getReportDir() , "report-" + timePart1[0] + "-" + timePart1[1] + "-" + timePart1[2] + "-" + timeParts[1]+ ".html");

            }else if(ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.timestamped") == null
                    || (ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.timestamped") != null &&
                    ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty("report.timestamped").trim().equals("false")
            )){
                reportFile = new File(TargetProject.getInstance().getReportDir() , "report.html");
            }
            else{
                reportFile = new File(TargetProject.getInstance().getReportDir() , "report.html");
            }

            fileWriter = new FileWriter(reportFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.template.process(input, fileWriter);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static Configuration initConfig() throws IOException {

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);

        configuration.setDirectoryForTemplateLoading(new File(TEMPLATES_DIRECTORY));

        configuration.setIncompatibleImprovements(new Version(2, 3, 27));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.FRANCE);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return configuration;
    }
}
