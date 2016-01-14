package org.codebehind.mrslmaintenance.Abstract;

import org.codebehind.mrslmaintenance.Entities.EquipmentParameters;

/**
 * Created by root on 13/01/16.
 */
public interface IEquipParamDelegate {

    void onCallback(EquipmentParameters equipParam);
}
