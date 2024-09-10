package com.java.managersystem.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.java.managersystem.R;
import com.java.managersystem.adapter.StudyAdapter;
import com.java.managersystem.adapter.TongjiAdapter;
import com.java.managersystem.databinding.ActivityMainBinding;
import com.java.managersystem.databinding.ActivityTongjiBinding;
import com.java.managersystem.entity.StudyBean;
import com.java.managersystem.studySqlate.StudyContactsDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TongjiActivity extends AppCompatActivity {
    private ActivityTongjiBinding binding;

    //筛选列表
    private ListPopupWindow listPopupWindow;
    private List<String> mList = new ArrayList<>();

    StudyContactsDatabase contactsDatabase;
    List<StudyBean> studyBeanList;
    TongjiAdapter tongjiAdapter;
    LinearLayoutManager llManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tongji);
        initview();
        selectBox();
    }

    private void initview(){
        contactsDatabase = new StudyContactsDatabase(TongjiActivity.this);
        studyBeanList = contactsDatabase.queryAllData();

        tongjiAdapter = new TongjiAdapter(TongjiActivity.this);
        llManager = new LinearLayoutManager(TongjiActivity.this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyc.setAdapter(tongjiAdapter);
        binding.recyc.setLayoutManager(llManager);
        tongjiAdapter.setDatas(studyBeanList);

        binding.tvCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPopupWindow.show();
            }
        });

        binding.tvScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(TongjiActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu1, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 处理菜单项点击事件
                        binding.tvScore.setText((String) item.getTitle());

                        tongjiAdapter.setScoreName((String) item.getTitle());
                        tongjiAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
                popup.show();
            }
        });
    }


    private void selectBox() {
        List<String> xList = new ArrayList<>();
        for (StudyBean studyBean : studyBeanList) {
            xList.add(studyBean.getCourse());
        }
        //去除相同元素
        mList = new ArrayList<>(new HashSet<>(xList));
        mList.add(0,"所有课程");

        //初始化ListPopupWindow，适配
        listPopupWindow = new ListPopupWindow(this);
        //系统布局
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
        //自定义适配器，布局
//        MyApader adapter = new MyApader(this,mList);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(binding.tvCourse);//设置ListPopupWindow的锚点，关联mButton位置
        listPopupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.tvCourse.setText(mList.get(position));

                tongjiAdapter.setCourseName(mList.get(position));
                tongjiAdapter.notifyDataSetChanged();

                listPopupWindow.dismiss(); //关闭listPopupWindow
            }
        });
    }
}
