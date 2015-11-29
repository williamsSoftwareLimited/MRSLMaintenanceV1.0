package org.codebehind.mrslmaintenance.ViewModels;

import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.ViewModels.Abstract.AbstractTextViewViewModel;

/**
 * Created by root on 18/11/15.
 */
public class EditTextViewModel  extends AbstractTextViewViewModel {

    protected EditText _editText;

    public EditTextViewModel(TextView textViewType){
        super(textViewType);

        _editText=(EditText)textViewType;
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
