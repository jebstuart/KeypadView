package com.jebware.keypadviewdemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.jebware.keypadview.KeypadView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NumericKeypadActivity extends AppCompatActivity {

    @Bind(android.R.id.text1)
    EditText inputField;
    @Bind(R.id.kpv_keypad)
    KeypadView keypad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeric_keypad);
        ButterKnife.bind(this);

        keypad.setContainer(new KeypadView.NumericKeypadContainer() {
            @Nullable
            @Override
            public EditText getActiveField() {
                return inputField;
            }
        });
    }
}
