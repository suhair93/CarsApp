package com.cars.cars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_layout);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.material_light_theme_form:
                startActivity(new Intent(this, LightThemeActivity.class));
                break;
            case R.id.material_dark_theme_form:
                startActivity(new Intent(this, DarkThemeActivity.class));
            default:
                break;
        }
    }
}
