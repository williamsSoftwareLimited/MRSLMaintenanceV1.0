package org.codebehind.mrslmaintenance.Services;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;

import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.ReportEquipParams;
import org.codebehind.mrslmaintenance.Models.Abstract.IEmailDbModel;
import org.codebehind.mrslmaintenance.Models.Abstract.IReportEquipParamsModel;
import org.codebehind.mrslmaintenance.Models.Abstract.IReportModel;
import org.codebehind.mrslmaintenance.Models.Abstract.ISiteEquipmentDbModel;
import org.codebehind.mrslmaintenance.Services.Abstract.IEmailParseService;
import org.codebehind.mrslmaintenance.Services.Abstract.IEmailService;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by root on 16/02/16.
 */
public class EmailService implements IEmailService {

    private static final String LOG_TAG="EmailService",
                                REPORT_="Report: ",
                                DATE_=", Date: ",
                                ENGINEER_=", Engineer: ";

    private Activity _act;
    private IEmailParseService _parser;
    private ISiteEquipmentDbModel _siteEquipModel;
    private IEmailDbModel _emailModel;

    public EmailService(Activity act, IEmailParseService parser, ISiteEquipmentDbModel siteEquipModel, IEmailDbModel emailModel){

        if (act==null) Log.wtf(LOG_TAG, "construct: violation arg act is null.");
        if (parser==null) Log.wtf(LOG_TAG, "construct: violation arg parser is null.");
        if (siteEquipModel==null) Log.wtf(LOG_TAG, "construct: violation arg siteEquipModel is null.");
        if (emailModel==null) Log.wtf(LOG_TAG, "construct: violation arg emailModel is null.");

        _act=act;
        _parser=parser;
        _siteEquipModel=siteEquipModel;
        _emailModel=emailModel;
    }

    public int sendEmail(Report r){
        Intent intent;
        String subject;

        if (_act==null) {

            Log.wtf(LOG_TAG, "sendEmail: arg violation _act is null. Check the constructor.");
            return -1;
        }

        if (r==null){

            Log.wtf(LOG_TAG, "sendEmail: arg violation report is null.");
            return -1;
        }

        r.setSiteEquipmentList(_siteEquipModel.getSiteEquipmentListForReport(r.getId()));

        intent=new Intent(Intent.ACTION_SEND);

        subject=REPORT_+ r.getSiteName()+DATE_+ DateFormat.getDateInstance().format(r.getReportDate());

        //intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");

        intent.putExtra(Intent.EXTRA_EMAIL, _emailModel.getSelectedArray());
        //intent.putExtra(Intent.EXTRA_CC, CC);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(_parser.returnReport(r)));

        try {

            _act.startActivity(intent);

            Log.d(LOG_TAG, "Finished sending email...");

        } catch (android.content.ActivityNotFoundException ex) {

            return -1;
        }

        return 1;
    }
}
