package fr.istic.m2il.vv.mutator.report;

public interface IReportStrategy {

    void configure(IReportService reportService);
    void execute();
}
