package com.example.yys.music;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.BindView;


public class LoginActivity extends Activity {
    @BindView(R.id.user_name) EditText usernameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        usernameText.setText("aa");
    }
}
