package org.codebehind.mrslmaintenance.Abstract;

import org.codebehind.mrslmaintenance.Entities.Site;

import java.util.UUID;

/**
 * Created by Gavin on 03/02/2015.
 */
public interface IFragmentSiteCallback{
    public void onFragmentCallback(Site site);
    public void onFragmentCallbackLocation(UUID siteId);
}
