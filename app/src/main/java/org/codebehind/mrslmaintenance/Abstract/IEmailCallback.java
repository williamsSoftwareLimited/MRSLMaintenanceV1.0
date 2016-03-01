package org.codebehind.mrslmaintenance.Abstract;

import org.codebehind.mrslmaintenance.Entities.Email;

/**
 * Created by root on 23/02/16.
 */
public interface IEmailCallback {

    void onCallback(Email e, int n);
}
