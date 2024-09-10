package com.java.managersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.java.managersystem.R;
import com.java.managersystem.adapter.StudyAdapter;
import com.java.managersystem.databinding.ActivityMainBinding;
import com.java.managersystem.databinding.ActivityPersonBinding;
import com.java.managersystem.entity.LocalUserInfoBean;
import com.java.managersystem.entity.StudyBean;
import com.java.managersystem.sqlate.ContactsDatabase;
import com.java.managersystem.studySqlate.StudyContactsDatabase;

import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private ActivityPersonBinding binding;

    ContactsDatabase contactsDatabase;
    List<LocalUserInfoBean> studyBeanList;
    LocalUserInfoBean myBean;

    String getAccount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person);
        getAccount = getIntent().getStringExtra("account");
        initview();
    }

    private void initview(){
        contactsDatabase = new ContactsDatabase(PersonActivity.this);
        studyBeanList = contactsDatabase.queryAllData();

        for (LocalUserInfoBean localUserInfoBean : studyBeanList) {
            if (localUserInfoBean.getAccount().equals(getAccount)){
                myBean = localUserInfoBean;
            }
        }

        binding.tvName.setText(myBean.getNickName());

        if (myBean.getAvatar() == 0){
            binding.siv.setImageResource(R.mipmap.ic_avatar1);
        }else if (myBean.getAvatar() == 1){
            binding.siv.setImageResource(R.mipmap.ic_avatar2);
        }else if (myBean.getAvatar() == 2){
            binding.siv.setImageResource(R.mipmap.ic_avatar3);
        }else if (myBean.getAvatar() == 3){
            binding.siv.setImageResource(R.mipmap.ic_avatar4);
        }else if (myBean.getAvatar() == 4){
            binding.siv.setImageResource(R.mipmap.ic_avatar5);
        }else if (myBean.getAvatar() == 5){
            binding.siv.setImageResource(R.mipmap.ic_avatar6);
        }else if (myBean.getAvatar() == 6){
            binding.siv.setImageResource(R.mipmap.ic_avatar7);
        }

        binding.tvPic.setText("头像" + (myBean.getAvatar() + 1));

        if (myBean.getRole() == 0){
            binding.tvRole.setText("学生");
        }else {
            binding.tvRole.setText("教师");
        }

        binding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.llLoginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonActivity.this,LoginActivity.class));
                finish();
            }
        });
    }


}
