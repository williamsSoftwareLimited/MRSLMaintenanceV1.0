package org.codebehind.mrslmaintenance.Entities;

import java.util.UUID;

/**
 * Created by Gavin on 29/01/2015.
 */
public class ReportEquipmentParameters {
    private UUID Id;
    private UUID ReportId;
    private UUID EquipmentId;
    private String Name; // got from Parameters table?
    private String Value; // the Value can be string, integer, boolean etc
    private String Comment;

    public UUID getReportId() {
        return ReportId;
    }
    public void setReportId(UUID reportId) {
        ReportId = reportId;
    }
    public UUID getEquipmentId() {
        return EquipmentId;
    }
    public void setEquipmentId(UUID equipmentId) {
        EquipmentId = equipmentId;
    }
    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
