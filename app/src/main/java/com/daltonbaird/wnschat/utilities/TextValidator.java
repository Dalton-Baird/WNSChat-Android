package com.daltonbaird.wnschat.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Used to validate TextViews
 *
 * Created by Dalton on 3/26/2016.
 *
 * See http://stackoverflow.com/questions/2763022/android-how-can-i-validate-edittext-input/11838715#11838715
 */
public abstract class TextValidator implements TextWatcher
{
    /** The TextView to validate */
    private final TextView textView;

    /**
     * Creates a new TextValidator for the specified TextView
     * @param textView The TextView to validate
     */
    public TextValidator(TextView textView)
    {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    public void afterTextChanged(Editable editable)
    {
        this.validate(this.textView, textView.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        //Don't care
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        //Don't care
    }
}
