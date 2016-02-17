package org.codebehind.mrslmaintenance.Services;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.codebehind.mrslmaintenance.Services.Abstract.IEmailService;

/**
 * Created by root on 16/02/16.
 */
public class EmailService implements IEmailService {

    private static final String LOG_TAG="EmailService";

    private Activity _act;

    public EmailService(Activity act){

        if (act==null) Log.wtf(LOG_TAG, "construct: violation arg act is null.");

        _act=act;
    }

    public int sendEmail(){
        Intent intent;

        if (_act==null) {

            Log.wtf(LOG_TAG, "sendEmail: violation arg _act is null. Check the constructor.");
            return -1;
        }

        intent=new Intent(Intent.ACTION_SEND);

        //intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"gavinwilliams_69@hotmail.com"});
        //intent.putExtra(Intent.EXTRA_CC, CC);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {

            _act.startActivity(intent);

            Log.d(LOG_TAG, "Finished sending email...");

        } catch (android.content.ActivityNotFoundException ex) {

            return -1;
        }

        return 1;
    }
}
