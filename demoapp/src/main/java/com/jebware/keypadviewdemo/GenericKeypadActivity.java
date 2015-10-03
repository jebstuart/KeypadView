package com.jebware.keypadviewdemo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.jebware.keypadview.KeypadView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GenericKeypadActivity extends AppCompatActivity {

    /**
     * REQUIRED EXTRA = you must set a layout resource ID to inflate as the keypad
     */
    public static final String EXTRA_KEYPAD_LAYOUT_RESOURCE_ID = "keypadLayoutResId";

    /**
     * OPTIONAL EXTRA = the view ID of the KeypadView.  If not set, defaults to R.id.kpv_keypad
     */
    public static final String EXTRA_KEYPAD_VIEW_ID = "keypadViewId";

    private static final String TAG = "Generic Keypad Activity";

    @Bind(android.R.id.text1)
    EditText inputField;
    @Bind(R.id.generic_keypad_placeholder)
    FrameLayout keypadPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_keypad);
        ButterKnife.bind(this);

        //suppress the system keyboard.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        int keypadLayoutResourceId = getIntent().getIntExtra(EXTRA_KEYPAD_LAYOUT_RESOURCE_ID, -1);
        int keypadViewId = getIntent().getIntExtra(EXTRA_KEYPAD_VIEW_ID, R.id.kpv_keypad);
        if (keypadLayoutResourceId == -1) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        getLayoutInflater().inflate(keypadLayoutResourceId, keypadPlaceholder, true);

        KeypadView keypadView = (KeypadView) findViewById(keypadViewId);
        if (keypadView == null) {
            //This is a problem, let the user know it's not going to work
            Log.w(TAG, "no keypad found. set EXTRA_KEYPAD_VIEW_ID, or include a KeypadView with id R.id.kpv_keypad");
            Snackbar.make(inputField, "No Keypad", Snackbar.LENGTH_LONG).show();
        } else {
            //let the keypad know where to send characters
            keypadView.setField(inputField);
        }
    }

}
