package org.codebehind.mrslmaintenance.Entities;

import java.util.UUID;

/**
 * Created by Gavin on 29/12/2014.
 */
public class Personnel {
    UUID _id;
    String _firstName;
    String _lastName;
    String _email;

    public UUID getId() {
        return _id;
    }
    public void setId(UUID id) {
        _id = id;
    }
    public String getFirstName() {
        return _firstName;
    }
    public void setFirstName(String _firstName) {
        this._firstName = _firstName;
    }
    public String getLastName() {
        return _lastName;
    }
    public void setLastName(String _lastName) {
        this._lastName = _lastName;
    }
    public String getEmail() {
        return _email;
    }
    public void setEmail(String _email) {
        this._email = _email;
    }
}
