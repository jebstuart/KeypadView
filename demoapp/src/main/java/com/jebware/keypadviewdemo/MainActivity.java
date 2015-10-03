package com.jebware.keypadviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.main_btn_numeric)
    void onClickNumeric() {
        Intent intent = new Intent(this, GenericKeypadActivity.class)
                .putExtra(GenericKeypadActivity.EXTRA_KEYPAD_LAYOUT_RESOURCE_ID, R.layout.kpv_numeric_keypad);
        startActivity(intent);
    }

    @OnClick(R.id.main_btn_telephone)
    void onClickPin() {
        Intent intent = new Intent(this, GenericKeypadActivity.class)
                .putExtra(GenericKeypadActivity.EXTRA_KEYPAD_LAYOUT_RESOURCE_ID, R.layout.kpv_telephone_keypad);
        startActivity(intent);
    }
}
