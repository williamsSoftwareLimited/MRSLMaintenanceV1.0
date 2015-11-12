package org.codebehind.mrslmaintenance.Entities;

/**
 * Created by Gavin on 29/01/2015.
 */
public class ReportEquipmentParameters {
    private int _id;
    private int _reportId;
    private int _parameterId;
    private int _equipmentId;
    private String _value; // the Value can be string, integer, boolean etc
    private Parameter _parameter;

    public ReportEquipmentParameters(int reportId, int equipmentId, int parameterId, String value){
        this(-1, reportId, parameterId, equipmentId, value);
    }

    public ReportEquipmentParameters(int id, int reportId, int equipmentId, int parameterId, String value){
        this(-1, reportId, parameterId, equipmentId, value, null);
    }

    public ReportEquipmentParameters(int id, int reportId, int equipmentId, int parameterId, String value, Parameter parameter){
        setId(id);
        setReportId(reportId);
        setParameterId(parameterId);
        setEquipmentId(equipmentId);
        setValue(value);
        setParameter(parameter); // this is for the UI
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getReportId() {
        return _reportId;
    }

    public void setReportId(int reportId) {
        _reportId = reportId;
    }

    public int getEquipmentId() {
        return _equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        _equipmentId = equipmentId;
    }

    public int getParameterId() {
        return _parameterId;
    }

    public void setParameterId(int parameterId) {
        _parameterId = parameterId;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String value) {
        _value = value;
    }

    public Parameter getParameter(){
        return _parameter;
    }

    public void setParameter(Parameter parameter){
        _parameter=parameter;
    }
}
