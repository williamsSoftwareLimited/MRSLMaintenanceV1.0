package org.codebehind.mrslmaintenance.Entities.Abstract;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by root on 21/12/15.
 */
public class AEntity implements Serializable {
    private int _id;
    private UUID _uuid;
    private Date _timestamp;

    public int getId() {
        return _id;
    }
    public void setId(int _id) {
        this._id = _id;
    }
    public UUID getUUID() {
        return _uuid;
    }
    public void setUUID(UUID _id) {
        this._uuid = _id;
    }
    public Date getTimestamp(){return _timestamp;}
    public void setTimestamp(Date timestamp){_timestamp=timestamp;}

}
