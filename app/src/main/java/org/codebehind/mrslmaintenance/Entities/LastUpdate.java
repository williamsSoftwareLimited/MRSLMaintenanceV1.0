package org.codebehind.mrslmaintenance.Entities;

import org.codebehind.mrslmaintenance.Entities.Abstract.AEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by root on 29/03/16.
 */
public class LastUpdate extends AEntity implements Serializable {
// this inherits all it needs so far from AEntity
    public LastUpdate(int id, Date timestamp, UUID uuid){
        setUUID(uuid);
        setTimestamp(timestamp);
        setId(id);
}
    public LastUpdate(Date timestamp, UUID uuid){
        this(-1, timestamp, uuid);
    }
}
