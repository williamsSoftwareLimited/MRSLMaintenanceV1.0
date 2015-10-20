package org.codebehind.mrslmaintenance;

import android.content.Context;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by Gavin on 09/02/2015.
 */
public class Debugger {
    private static Debugger _instance;

    private Debugger(){ }

    public static Debugger getInstance(){
        if (_instance==null)_instance=new Debugger();
        return _instance;
    }
    public void debugMessage(Context context, String message){
        if (StaticConstants.Debug==StaticConstants.debugModes.debug && !StaticConstants.DebugLog) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
