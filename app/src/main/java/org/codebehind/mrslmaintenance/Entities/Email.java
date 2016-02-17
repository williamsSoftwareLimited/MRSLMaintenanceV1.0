package org.codebehind.mrslmaintenance.Entities;

import org.codebehind.mrslmaintenance.Entities.Abstract.AEntity;

/**
 * Created by root on 17/02/16.
 */
public class Email extends AEntity implements Comparable<Email> {

    private String _emailAddress;
    private Boolean _selected;
    private int _timeStamp;
    private Boolean _deleted;

    public Email (String emailAddress, Boolean selected){
        this(-1,emailAddress,selected);
    }

    public Email (int id, String emailAddress, Boolean selected){

        setId(id);
        setEmailAddress(emailAddress);
        setSelected(selected);
    }

    public String getEmailAddress() {
        return _emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        _emailAddress = emailAddress;
    }

    public Boolean getSelected() {
        return _selected;
    }

    public void setSelected(Boolean selected) {
        _selected = selected;
    }

    public int getTimeStamp() {
        return _timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        _timeStamp = timeStamp;
    }

    public Boolean getDeleted() {
        return _deleted;
    }

    public void setDeleted(Boolean deleted) {
        _deleted = deleted;
    }

    @Override
    public int compareTo(Email email) {

        if (email==null) return -1;

        return getId()-email.getId();
    }

}
