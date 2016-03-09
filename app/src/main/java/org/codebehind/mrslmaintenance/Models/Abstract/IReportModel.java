package org.codebehind.mrslmaintenance.Models.Abstract;

import org.codebehind.mrslmaintenance.Entities.Report;

import java.util.ArrayList;

/**
 * Created by root on 08/03/16.
 */
public interface IReportModel {
    int add(Report report);

    int update(Report report);

    Report getReport(int reportId);

    ArrayList<Report> getAll();
}
