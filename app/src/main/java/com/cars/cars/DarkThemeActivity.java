package com.cars.cars;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

public class DarkThemeActivity extends BaseCardFormActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if (mCardForm.isCardScanningAvailable()) {
            getMenuInflater().inflate(R.menu.card_io_dark, menu);
            if (extras != null) {
                typeC= extras.getString("typecar");
                modelC= extras.getString("modelcar");
                namecustomer= extras.getString("name_custome");
                typeViewC= extras.getString("typeview");
                id_company= extras.getString("id_company");

            }
        }

        return true;
    }
}
