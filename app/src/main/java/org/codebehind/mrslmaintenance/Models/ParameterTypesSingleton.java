package org.codebehind.mrslmaintenance.Models;

import java.util.Hashtable;

/**
 * Created by root on 18/11/15.
 */
public class ParameterTypesSingleton {
    private Hashtable<Integer, String> _parameterTypeList;
    private static ParameterTypesSingleton _instance;

    public Hashtable<Integer, String> getParameterTypeList(){
        return _parameterTypeList;
    }

    public static ParameterTypesSingleton getInstance(){
        if (_instance==null)_instance=new ParameterTypesSingleton();

        return _instance;
    }

    private ParameterTypesSingleton(){
        populate();
    }

    private void populate(){
        if (_parameterTypeList==null){
            _parameterTypeList=new Hashtable<>();
            _parameterTypeList.put(1, "Decimal");
            _parameterTypeList.put(2, "Number");
            _parameterTypeList.put(3, "Date");
            _parameterTypeList.put(4, "Password");
            _parameterTypeList.put(5, "Text");
            _parameterTypeList.put(6, "TextLarge");
        }
    }

}
