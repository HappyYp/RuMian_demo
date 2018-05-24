package com.example.chenm.rumian;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

/**
 * Created by zyp on 2018/5/23.
 */

public class LoginSuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
    }
}
