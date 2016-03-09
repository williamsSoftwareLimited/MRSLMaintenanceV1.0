package org.codebehind.mrslmaintenance.Models.Abstract;

import org.codebehind.mrslmaintenance.Entities.SiteEquipment;

import java.util.ArrayList;

/**
 * Created by root on 08/03/16.
 */
public interface ISiteEquipmentDbModel {
    int add(SiteEquipment entity);

    int delete(SiteEquipment entity);

    ArrayList<SiteEquipment> getSiteEquipments(int siteId);

    ArrayList<SiteEquipment>getSiteEquipmentListForReport(int reportId);
}
