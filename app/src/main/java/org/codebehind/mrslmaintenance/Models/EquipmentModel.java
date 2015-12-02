package org.codebehind.mrslmaintenance.Models;

import org.codebehind.mrslmaintenance.Entities.Equipment;

import java.util.ArrayList;

/**
 * Created by Gavin on 02/01/2015.
 */
public class EquipmentModel {
    ArrayList<Equipment> _list;
    static EquipmentModel _instance;
    private EquipmentModel(){
        Equipment e;

        _list=new ArrayList<>();
    }
    public static EquipmentModel getInstance(){
        if (_instance==null) _instance=new EquipmentModel();
        return _instance;
    }
    public ArrayList<Equipment> getList() {
        return _list;
    }
    public Equipment getEquipment(int id) {
        for(Equipment e: getList()) if (e.getId()==id)return e;
        return null;
    }
    public Equipment add(Equipment equipment) {
        _list.add(equipment);
        return equipment;
    }
    public void delete(int equipmentId) {
        Equipment e;

        e=getEquipment(equipmentId);
        _list.remove(e);
    }
    public Equipment update(Equipment equipment) {
        delete(equipment.getId());
        add(equipment);
        return  equipment;
    }

    public Equipment getTemplate(Equipment equipment){
        return equipment;
    }

}
