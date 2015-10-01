package com.jebware.keypadviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.jebware.keypadview.KeypadView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PinEntryActivity extends AppCompatActivity {

    @Bind(R.id.pin_box_1)
    EditText pinBox1;
    @Bind(R.id.pin_box_2)
    EditText pinBox2;
    @Bind(R.id.pin_box_3)
    EditText pinBox3;
    @Bind(R.id.pin_box_4)
    EditText pinBox4;
    @Bind(R.id.keypad)
    KeypadView keypad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_entry);
        ButterKnife.bind(this);

        keypad.setContainer(keypadContainer);

        //suppress the system keyboard.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    private KeypadView.KeypadContainer keypadContainer = new KeypadView.KeypadContainer() {
        @Nullable
        @Override
        public EditText getActiveField() {
            View currentFocus = getCurrentFocus();
            return currentFocus instanceof EditText ? (EditText) currentFocus : null;
        }
    };

}
