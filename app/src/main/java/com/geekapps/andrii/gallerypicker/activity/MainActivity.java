package com.geekapps.andrii.gallerypicker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.picker.PickerDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.press_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickerDialogFragment pickerDialogFragment = PickerDialogFragment.newInstance();
                pickerDialogFragment.show(getSupportFragmentManager(), PickerDialogFragment.TAG);
            }
        });
    }
}
