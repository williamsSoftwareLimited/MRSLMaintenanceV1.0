package org.codebehind.mrslmaintenance.Entities;

import java.util.UUID;

/**
 * Created by Gavin on 09/02/2015.
 */
public class Parameter {
    private UUID ID;
    private String Name;
    private int Type;

    public Parameter(){
        this(UUID.randomUUID(),"",-1);
    }
    public Parameter(UUID id, String name, int type){
        // invariant is null for all!
        setID(id);
        setName(name);
        setType(type);
    }

    public UUID getID() {
        return ID;
    }
    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }
    public void setType(int type) {
        Type = type;
    }
    @Override
    public String toString() {
        return getName();
    }
}
