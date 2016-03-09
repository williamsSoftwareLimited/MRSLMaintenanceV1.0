package org.codebehind.mrslmaintenance.Models.Abstract;

import org.codebehind.mrslmaintenance.Entities.Email;

import java.util.ArrayList;

/**
 * Created by root on 09/03/16.
 */
public interface IEmailDbModel {
    int add(Email email);

    int update(Email e);

    int delete(int i);

    ArrayList<Email> getList();

    String []getSelectedArray();
}
