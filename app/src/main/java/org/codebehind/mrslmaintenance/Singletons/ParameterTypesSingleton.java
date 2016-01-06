package org.codebehind.mrslmaintenance.Singletons;

import android.util.Log;

import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.ParameterType;

import java.util.ArrayList;


/**
 * Created by root on 18/11/15.
 */
public class ParameterTypesSingleton {

    private static final String LOG_TAG="ParameterTypesSingleton";
    private ArrayList<ParameterType> _paramTypes;
    private static ParameterTypesSingleton _instance;
    private static final int DEFAULT_PARAM=4;

    public ArrayList<ParameterType> getParamTypes(){ return _paramTypes;}

    public static ParameterTypesSingleton getInstance(){

        if (_instance==null)_instance=new ParameterTypesSingleton();

        return _instance;
    }

    private ParameterTypesSingleton(){
        ParameterType type;

        if (_paramTypes==null){

            _paramTypes=new ArrayList<>();
            type=new ParameterType(1, "Decimal");
            _paramTypes.add(type);
            type=new ParameterType(2, "Number");
            _paramTypes.add(type);
            type=new ParameterType(3, "Date");
            _paramTypes.add(type);
            type=new ParameterType(4, "Password");
            _paramTypes.add(type);
            type=new ParameterType(5, "Text"); // this is the default
            _paramTypes.add(type);
            type=new ParameterType(6, "TextLarge");
            _paramTypes.add(type);
        }
    }

    public ParameterType getParamType(int id){

        if (id<1 )Log.wtf(LOG_TAG, "getParamType: invariant violation id<1, id="+id);


        for (ParameterType pt:_paramTypes)
            if (pt.getId()==id) return pt;

        return _paramTypes.get(DEFAULT_PARAM); // this is 'text'
    }

}
