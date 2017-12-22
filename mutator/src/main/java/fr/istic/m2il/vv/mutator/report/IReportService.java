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
     *  Display Report information about Mutations Analysis
     */
    void doReport();

    /**
     * Generates a Gui Report according to the strategy choose (Html, XML ....)
     * @throws IOException input/output Exception
     */
    void toGraphicReport() throws IOException;

}
