package org.codebehind.mrslmaintenance.Services;

import android.util.Log;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Services.Abstract.IEmailParseService;

import java.text.DateFormat;

/**
 * Created by root on 07/03/16.
 */
public class EmailParseService implements IEmailParseService {

    private static final String LOG_TAG="EmailParseService";

    @Override
    public String returnReport(Report r) {
        StringBuilder sb;

        if (r==null) {

            Log.wtf(LOG_TAG, "returnReport: arg violation r===null.");
            return "<h1>Empty Report!</h1>";
        }

        sb=new StringBuilder();
        sb.append("<h3>"+r.getSiteName()+"</h3>");
        sb.append("<p>"+ DateFormat.getDateInstance().format(r.getReportDate())+"<br />");
        sb.append(r.getEngineerName()+"</p>");


        for(SiteEquipment se: r.getSiteEquipmentList()){

            sb.append("<p>");
            sb.append(se.getEquipment().getEquipmentName()+"<br />");

            for(Parameter p: se.getEquipment().getParameterList()) {

                sb.append(p.getName()+" = "+p.getNewValue()+" "+p.getUnits()+"<br />");
            }

            sb.append("</p>");
        }

        return sb.toString();
    }
}
