package org.codebehind.mrslmaintenance.ViewModels;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.widget.EditText;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.FragmentMode;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.AbstractTextViewViewModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;

/**
 * Created by root on 18/11/15.
 */
public class EditTextViewModel  extends AbstractTextViewViewModel{

    private static final int MIN_TEXT_LENGTH=2; //the is ems
    protected EditText _editText;
    private IEditTextViewModelDelegate _editTextViewModelDelegate;
    private int _uniqueNo; // this is for the event to update the passed in delegate

    public void setEnabled(boolean b){
        _editText.setEnabled(b);
    }

    public EditTextViewModel(TextView textViewType, final IEditTextViewModelDelegate editTextViewModelDelegate){
        super(textViewType);

        _editText=(EditText)textViewType;
        _uniqueNo=textViewType.getId();
        _editTextViewModelDelegate=editTextViewModelDelegate;

        setAttributes();
        setEvents();
    }

    public void setNonEditable(){
        _editText.setKeyListener(null);
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

    private void setAttributes(){
        _editText.setMinEms(MIN_TEXT_LENGTH);
    }

    private void setEvents(){

        _editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _editTextViewModelDelegate.textUpdated(_uniqueNo, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
