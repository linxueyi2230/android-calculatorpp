package org.solovyev.android;

import android.content.Intent;

import org.solovyev.android.calculator.CalculatorActivity;

import co.bxvip.sdk.ui.BxStartActivityImpl;

public class SplashActivity extends BxStartActivityImpl {
    @Override
    public void toYourMainActivity() {
        startActivity(new Intent(this, CalculatorActivity.class));
    }
}
