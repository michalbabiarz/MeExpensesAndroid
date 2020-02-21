package com.pyskacz.android.myexpenses.ui.utils;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.pyskacz.android.myexpenses.R;

public class TextViewUtils {

    private TextViewUtils() {
    }

    public static void setAmountColor(TextView textView) {
        CharSequence text = textView.getText();
        if (text.length() > 0) {
            int colorIncome = ContextCompat.getColor(textView.getContext(), R.color.colorIncome);
            int colorOutcome = ContextCompat.getColor(textView.getContext(), R.color.colorOutcome);
            textView.setTextColor(text.charAt(0) != '-' ? colorIncome : colorOutcome);
        }
    }
}
