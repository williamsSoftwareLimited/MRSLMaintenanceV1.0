package org.codebehind.mrslmaintenance.Singletons;

import org.codebehind.mrslmaintenance.Entities.ParameterType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

/**
 * Created by root on 18/11/15.
 */
public class ParameterTypesSingleton {

    private ArrayList<ParameterType> _paramTypes;
    private static ParameterTypesSingleton _instance;

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
            type=new ParameterType(5, "Text");
            _paramTypes.add(type);
            type=new ParameterType(6, "TextLarge");
            _paramTypes.add(type);
        }
    }

}
