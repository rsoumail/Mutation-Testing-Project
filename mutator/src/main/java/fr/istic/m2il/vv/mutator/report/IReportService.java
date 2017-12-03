package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.mutant.Mutator;

import java.util.HashMap;
import java.util.List;

/**
 * An interface of Report Service aggregates all reports generate during mutation testing
 */
public interface IReportService {

    /**
     * Adds a reports list of a mutator  to the report service
     *
     * @param reports
     */
    void addReport(Mutator mutator, List<Report> reports);

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

}
