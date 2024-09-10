package com.java.managersystem.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.java.managersystem.R;
import com.java.managersystem.entity.LocalUserInfoBean;
import com.java.managersystem.sqlate.ContactsDatabase;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_account;
    EditText et_password;
    TextView tv_register;
    TextView tv_login;

    ContactsDatabase contactsDatabase;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView() {
        contactsDatabase = new ContactsDatabase(LoginActivity.this);

        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = getSharedPreferences("data",MODE_PRIVATE).edit();

        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        tv_register = findViewById(R.id.tv_register);
        tv_login = findViewById(R.id.tv_login);

        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String getName = pref.getString("name","");
        String getPassword = pref.getString("password","");
        et_account.setText(getName);
        et_password.setText(getPassword);
        if (!TextUtils.isEmpty(et_account.getText().toString()) &&!TextUtils.isEmpty(et_password.getText().toString())){
            tv_login.setBackgroundResource(R.drawable.bg_button_login_yes);
            tv_login.setEnabled(true);
        }else {
            tv_login.setBackgroundResource(R.drawable.bg_button_login_no);
            tv_login.setEnabled(false);
        }

        tv_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);

        et_account.addTextChangedListener(new mytextWatcher());
        et_password.addTextChangedListener(new mytextWatcher());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.tv_login:
                List<LocalUserInfoBean> localUserInfoBeanList = contactsDatabase.queryAllData();

                boolean haveAccount = false;
                String dbPassword = "";
                if (null!=localUserInfoBeanList&&localUserInfoBeanList.size()>0){
                    for (LocalUserInfoBean localUserInfoBean : localUserInfoBeanList) {
                        if (et_account.getText().toString().equals(localUserInfoBean.getAccount())){
                            haveAccount = true;
                            dbPassword = localUserInfoBean.getPassword();
                        }
                    }

                }
                if (!haveAccount){
                    Toast.makeText(LoginActivity.this,"暂无此账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_password.getText().toString().equals(dbPassword)){
                    Log.i("孙", "et_account.getText().toString(): "+et_account.getText().toString());
                    startActivity(new Intent(LoginActivity.this,MainActivity.class)
                            .putExtra("account",et_account.getText().toString()));
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                    editor.putString("name",et_account.getText().toString());
                    editor.putString("password",et_password.getText().toString());
                    editor.commit();

                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private class mytextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!TextUtils.isEmpty(et_account.getText().toString()) &&!TextUtils.isEmpty(et_password.getText().toString())){
                tv_login.setBackgroundResource(R.drawable.bg_button_login_yes);
                tv_login.setEnabled(true);
            }else {
                tv_login.setBackgroundResource(R.drawable.bg_button_login_no);
                tv_login.setEnabled(false);
            }
        }
    }
}