package org.codebehind.mrslmaintenance.Entities;

/**
 * Created by Gavin on 29/01/2015.
 */
public class ReportEquipParams {
    private int _id;
    private int _reportId;
    private int _parameterId;
    private int _siteEquipId;
    private String _value; // the Value can be string, integer, boolean etc
    private Parameter _parameter;

    public ReportEquipParams(int reportId, int equipmentId, int parameterId, String value){
        this(-1, reportId, parameterId, equipmentId, value);
    }

    public ReportEquipParams(int id, int reportId, int equipmentId, int parameterId, String value){
        this(-1, reportId, parameterId, equipmentId, value, null);
    }

    public ReportEquipParams(int id, int reportId, int equipmentId, int parameterId, String value, Parameter parameter){
        setId(id);
        setReportId(reportId);
        setParameterId(parameterId);
        setSiteEquipId(equipmentId);
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

    public int getSiteEquipId() {
        return _siteEquipId;
    }

    public void setSiteEquipId(int siteEquipId) {
        _siteEquipId = siteEquipId;
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
