package org.codebehind.mrslmaintenance.Services.Abstract;

import org.codebehind.mrslmaintenance.Entities.Report;

/**
 * Created by root on 16/02/16.
 */
public interface IEmailService {

    int sendEmail(Report r);
}
