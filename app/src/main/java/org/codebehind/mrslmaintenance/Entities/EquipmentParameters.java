package org.codebehind.mrslmaintenance.Entities;

/**
 * Created by root on 10/11/15.
 */
public class EquipmentParameters {
    int _id;
    int _equipmentId;
    int _parameterId;

    public EquipmentParameters(int _equipmentId, int _parameterId) {
        this (-1,_equipmentId,_parameterId);
    }

    public EquipmentParameters(int _id, int _equipmentId, int _parameterId) {
        this._id = _id;
        this._equipmentId = _equipmentId;
        this._parameterId = _parameterId;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
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
}
