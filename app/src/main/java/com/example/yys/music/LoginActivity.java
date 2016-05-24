package com.example.yys.music;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yys.music.bean.Post;
import com.example.yys.music.core.Api;
import com.example.yys.music.core.SimpleCallback;
import com.example.yys.music.core.ZhuanLanApi;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;


public class LoginActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.user_name) EditText usernameText;
    @BindView(R.id.user_password) EditText passwordText;
    @BindView(R.id.buton_login) Button loginButton;
    @BindView(R.id.buton_signup) Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buton_login:
                login();
                break;
            case R.id.buton_signup:
                signup();
                break;
        }

    }
    private void login() {
        String username =  usernameText.getText().toString();
        String password = passwordText.getText().toString();
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password))
            Toast.makeText(this,"请输入用户名密码",Toast.LENGTH_LONG).show();
        if(username.equals("11")&& password.equals("12"))
            startActivity(new Intent(this,MainActivity.class));
        else
            Toast.makeText(this,"用户名密码错误"+username+password,Toast.LENGTH_LONG).show();


    }
    private void signup() {
        startActivity(new Intent(this,SignupActivity.class));
    }

}
