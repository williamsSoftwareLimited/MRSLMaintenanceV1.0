package org.codebehind.mrslmaintenance.ViewModels;

import android.text.InputType;
import android.widget.EditText;

/**
 * Created by root on 18/11/15.
 */
public class EditTextViewModel {
    private EditText _editText;

    public void setText(String text){
        _editText.setText(text);
    }

    public String getText(){
        return _editText.getText().toString();
    }

    public EditTextViewModel(EditText editText){
        _editText=editText;
    }

    // all the different types - http://developer.android.com/reference/android/widget/TextView.html#attr_android:inputType
    public void setType(int type){
        // for an idea of the types look at ParameterTypesSingleton
        switch(type){

            case 1:
                _editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); // Decimal
                break;

            case 2:
                _editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED); // Number
                break;

            case 3:
                _editText.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE); // Date
                break;

            case 4:
                _editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD); // Password
                break;

            case 5:
                _editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE); //Text
                break;

            case 6:
                _editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE); // Long Text
                break;

            default:
                _editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE); // Text
                break;
        }
    }
}
