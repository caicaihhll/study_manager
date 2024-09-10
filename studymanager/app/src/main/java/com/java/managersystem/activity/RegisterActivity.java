package com.java.managersystem.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.java.managersystem.MyPopChangeBg;
import com.java.managersystem.R;
import com.java.managersystem.adapter.MyBgAdapter;
import com.java.managersystem.databinding.BgAvatarBinding;
import com.java.managersystem.entity.LocalUserInfoBean;
import com.java.managersystem.sqlate.ContactsDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView iv_close;
    EditText et_account;
    EditText et_password;
    EditText et_ensure_password;
    EditText et_nickname;
    TextView tv_role;
    ImageView iv_avatar;
    TextView tv_register;

    ContactsDatabase contactsDatabase;


    MyPopChangeBg popAvatar;
    BgAvatarBinding bgAvatarBinding;
    MyBgAdapter myBgAdapter;
    LinearLayoutManager linearLayoutManager;
    List<Integer> bgList;

    int mRole = 0;
    int mAvatar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        popBgInit();
    }
    private void initView() {
        contactsDatabase = new ContactsDatabase(RegisterActivity.this);
        bgList = new ArrayList<>();

        iv_close = findViewById(R.id.iv_close);
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        et_ensure_password = findViewById(R.id.et_ensure_password);
        et_nickname = findViewById(R.id.et_nickname);
        tv_role = findViewById(R.id.tv_role);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_register = findViewById(R.id.tv_register);

        iv_close.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_role.setOnClickListener(this);
        iv_avatar.setOnClickListener(this);

        et_account.addTextChangedListener(new mytextWatcher());
        et_password.addTextChangedListener(new mytextWatcher());
        et_ensure_password.addTextChangedListener(new mytextWatcher());
    }

    private void popBgInit() {
        popAvatar = new MyPopChangeBg(RegisterActivity.this);
        bgAvatarBinding = popAvatar.getBgAvatarBinding();

        myBgAdapter = new MyBgAdapter(RegisterActivity.this);
        linearLayoutManager = new LinearLayoutManager(RegisterActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bgAvatarBinding.rcBg.setAdapter(myBgAdapter);
        bgAvatarBinding.rcBg.setLayoutManager(linearLayoutManager);

        myBgAdapter.setMyonitemclicklistener(new MyBgAdapter.myOnItemClickListener() {
            @Override
            public void itemClickListener(int position, Integer bean) {
                iv_avatar.setImageResource(bgList.get(position));
                mAvatar = position;

                popAvatar.getPopupWindow().dismiss();
            }
        });

        bgList.add(R.mipmap.ic_avatar1);
        bgList.add(R.mipmap.ic_avatar2);
        bgList.add(R.mipmap.ic_avatar3);
        bgList.add(R.mipmap.ic_avatar4);
        bgList.add(R.mipmap.ic_avatar5);
        bgList.add(R.mipmap.ic_avatar6);
        bgList.add(R.mipmap.ic_avatar7);

        myBgAdapter.setDatas(bgList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_register:
                if (!et_password.getText().toString().equals(et_ensure_password.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                List<LocalUserInfoBean> localUserInfoBeanList = contactsDatabase.queryAllData();

                boolean haveAccount = false;
                if (null!=localUserInfoBeanList&&localUserInfoBeanList.size()>0){
                    for (LocalUserInfoBean localUserInfoBean : localUserInfoBeanList) {
                        if (et_account.getText().toString().equals(localUserInfoBean.getAccount())){
                            haveAccount = true;
                        }
                    }
                }

                if (haveAccount){
                    Toast.makeText(RegisterActivity.this,"账号已注册",Toast.LENGTH_SHORT).show();
                    return;
                }

                int count = contactsDatabase.dbCount();
                LocalUserInfoBean mLocalUserInfoBean = new LocalUserInfoBean();
                mLocalUserInfoBean.setAccount(et_account.getText().toString());
                mLocalUserInfoBean.setPassword(et_password.getText().toString());
                mLocalUserInfoBean.setUserId(count);
                mLocalUserInfoBean.setRole(mRole);
                mLocalUserInfoBean.setAvatar(mAvatar);
                if (!TextUtils.isEmpty(et_nickname.getText().toString())){
                    mLocalUserInfoBean.setNickName(et_nickname.getText().toString());
                }else {
                    mLocalUserInfoBean.setNickName("默认昵称");
                }
                contactsDatabase.insert(mLocalUserInfoBean);
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.tv_role:
                PopupMenu popup = new PopupMenu(RegisterActivity.this, tv_role);
                popup.getMenuInflater().inflate(R.menu.menu2, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 处理菜单项点击事件
                        tv_role.setText((String) item.getTitle());
                        if (item.getTitle().equals("角色：学生")){
                            mRole = 0;
                        }else {
                            mRole = 1;
                        }
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.iv_avatar:
                popAvatar.show(getWindow().getDecorView());
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
            if (!TextUtils.isEmpty(et_account.getText()) &&!TextUtils.isEmpty(et_password.getText()) &&!TextUtils.isEmpty(et_ensure_password.getText())){
                tv_register.setBackgroundResource(R.drawable.bg_button_login_yes);
                tv_register.setEnabled(true);
            }else {
                tv_register.setBackgroundResource(R.drawable.bg_button_login_no);
                tv_register.setEnabled(false);
            }
        }
    }
}