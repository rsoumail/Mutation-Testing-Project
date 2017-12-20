package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.mutant.Mutator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * An interface of Report Service aggregates all reports generate during mutation testing
 */
public interface IReportService {

    /**
     * Adds a report to reports list of a mutator  in the report service
     *
     * @param report
     */
    void addReport(Mutator mutator, Report report);

    /**
     * Returns all report informations in a markdown format
     *
     * @return
     */
    public String toMarkdown();

    /**
     *  Display Report information about Mutations Analysis
     */
    void doReport();


    void toHtml() throws IOException;

}
