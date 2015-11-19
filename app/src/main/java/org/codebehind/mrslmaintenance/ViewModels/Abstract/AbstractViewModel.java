package org.codebehind.mrslmaintenance.ViewModels.Abstract;

import android.widget.TextView;

/**
 * Created by root on 19/11/15.
 */
public abstract class AbstractViewModel {
    private TextView _textView;

    public void setText(String text){
        _textView.setText(text);
    }

    public String getText(){
        return _textView.getText().toString();
    }

    public AbstractViewModel(TextView textViewType){
        _textView=textViewType;
    }
}
