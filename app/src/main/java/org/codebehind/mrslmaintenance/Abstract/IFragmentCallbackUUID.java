package org.codebehind.mrslmaintenance.Abstract;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Gavin on 01/02/2015.
 */
public interface IFragmentCallbackUUID {
    public void onFragmentCallback(UUID id);//todo: this will eventually be deleted and the name refactored
    public void onFragmentCallback(int id);
}
