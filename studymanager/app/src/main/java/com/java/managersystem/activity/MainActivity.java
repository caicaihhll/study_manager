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
import com.java.managersystem.entity.LocalUserInfoBean;
import com.java.managersystem.entity.StudyBean;
import com.java.managersystem.sqlate.ContactsDatabase;
import com.java.managersystem.studySqlate.StudyContactsDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;

    StudyContactsDatabase contactsDatabase;
    List<StudyBean> studyBeanList;
    StudyAdapter studyAdapter;
    LinearLayoutManager llManager;

    String getAccount;

    ContactsDatabase logincontactsDatabase;
    List<LocalUserInfoBean> loginBeanList;
    LocalUserInfoBean myBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getAccount = getIntent().getStringExtra("account");
        initview();
    }

    private void initview(){
        //获取角色
        logincontactsDatabase = new ContactsDatabase(MainActivity.this);
        loginBeanList = logincontactsDatabase.queryAllData();

        for (LocalUserInfoBean localUserInfoBean : loginBeanList) {
            if (localUserInfoBean.getAccount().equals(getAccount)){
                myBean = localUserInfoBean;
            }
        }

        contactsDatabase = new StudyContactsDatabase(MainActivity.this);
        studyBeanList = contactsDatabase.queryAllData();

        studyAdapter = new StudyAdapter(MainActivity.this);
        llManager = new LinearLayoutManager(MainActivity.this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyc.setAdapter(studyAdapter);
        binding.recyc.setLayoutManager(llManager);
        studyAdapter.setDatas(studyBeanList);

        //角色
        if (myBean.getRole()==0){
            binding.llTop1.setVisibility(View.GONE);
            binding.llTop2.setVisibility(View.GONE);
            binding.tvAdd.setVisibility(View.GONE);
            studyAdapter.setShowDelete(false);
        }else {
            binding.llTop1.setVisibility(View.VISIBLE);
            binding.llTop2.setVisibility(View.VISIBLE);
            binding.tvAdd.setVisibility(View.VISIBLE);
            studyAdapter.setShowDelete(true);
        }

        studyAdapter.setMyonitemclicklistener(new StudyAdapter.myOnItemClickListener() {
            @Override
            public void itemClickListener(int type, StudyBean bean, int position) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View view = layoutInflater.inflate(R.layout.dialog_et,null,false);
                EditText etDialogSearch = view.findViewById(R.id.et_dialog_search);
                TextView tv_dialog_search = view.findViewById(R.id.tv_dialog_search);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                if (type == 1){
                    etDialogSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etDialogSearch.setHint("输入学号进行修改");
                }else if (type == 2){
                    etDialogSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                    etDialogSearch.setHint("输入名字进行修改");
                }else if (type == 3){
                    etDialogSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                    etDialogSearch.setHint("输入课程进行修改");
                }else if (type == 4){
                    etDialogSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etDialogSearch.setHint("输入成绩进行修改");
                }
                tv_dialog_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(etDialogSearch.getText().toString())){
                            //修改
                            if (type == 1){
                                bean.setStudyCode(etDialogSearch.getText().toString());
                            }else if (type == 2){
                                bean.setName(etDialogSearch.getText().toString());
                            }else if (type == 3){
                                bean.setCourse(etDialogSearch.getText().toString());
                            }else if (type == 4){
                                bean.setScore(etDialogSearch.getText().toString());
                            }

                            contactsDatabase.update(bean);
                            //重新查
                            studyBeanList = contactsDatabase.queryAllData();
                            studyAdapter.setDatas(studyBeanList);

                            alertDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void itemDeleteListener(StudyBean bean, int position) {
                //删除
                contactsDatabase.delete(bean);
                //重新查
                studyBeanList = contactsDatabase.queryAllData();
                studyAdapter.setDatas(studyBeanList);
            }
        });

        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(binding.etCode.getText().toString())
                        &&!TextUtils.isEmpty(binding.etName.getText().toString())
                        &&!TextUtils.isEmpty(binding.etCourse.getText().toString())
                        &&!TextUtils.isEmpty(binding.etScore.getText().toString())){
                    //新增
                    contactsDatabase.insert(new StudyBean(binding.etCode.getText().toString(),
                            binding.etName.getText().toString(),
                            binding.etCourse.getText().toString(),
                            binding.etScore.getText().toString()));

                    //重新查
                    studyBeanList = contactsDatabase.queryAllData();
                    studyAdapter.setDatas(studyBeanList);

                    binding.etCode.getText().clear();
                    binding.etName.getText().clear();
                    binding.etCourse.getText().clear();
                    binding.etScore.getText().clear();

                }
            }
        });

        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(binding.etSearch.getText().toString())){
                    //查询
                    studyAdapter.setQueryName(binding.etSearch.getText().toString());
                    studyAdapter.notifyDataSetChanged();
                    binding.etSearch.getText().clear();
                }
            }
        });

        binding.tvRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studyAdapter.setQueryName("");
                studyAdapter.notifyDataSetChanged();
                binding.etSearch.getText().clear();
            }
        });

        binding.ivTongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (studyBeanList.size()>0){
                    startActivity(new Intent(MainActivity.this,TongjiActivity.class));
                }
            }
        });

        binding.ivPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PersonActivity.class)
                        .putExtra("account",getAccount));
            }
        });
    }


}
