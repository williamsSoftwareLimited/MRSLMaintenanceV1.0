package org.codebehind.mrslmaintenance.Entities.Abstract;

import java.io.Serializable;

/**
 * Created by root on 21/12/15.
 */
public class AEntity implements Serializable {
    private int _id;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

}
