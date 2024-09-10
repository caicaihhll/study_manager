package com.java.managersystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.java.managersystem.R;
import com.java.managersystem.databinding.ItemStudyBinding;
import com.java.managersystem.databinding.ItemTongjiBinding;
import com.java.managersystem.entity.StudyBean;

import java.util.ArrayList;
import java.util.List;

public class TongjiAdapter extends RecyclerView.Adapter<TongjiAdapter.ViewHolder> {
    protected List<StudyBean> datas;
    private LayoutInflater inflater;
    private Context context;
    private String courseName;
    private String scoreName;
    private TongjiAdapter.myOnItemClickListener myonitemclicklistener;

    public TongjiAdapter(Context context) {
        this.datas =new ArrayList<StudyBean>();
        this.context =context;
        courseName = "";
        scoreName = "";
        this.inflater =LayoutInflater.from(context);//把环境放到填充器里。
    }

    @Override
    public TongjiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getInflater();//这是用到父类方法，布局填充器。
        View view = inflater.inflate(R.layout.item_tongji, parent,false);//这里是指小布局。
        TongjiAdapter.ViewHolder viewHolder = new TongjiAdapter.ViewHolder(view);
        return viewHolder;
    }

    public void setDatas(List<StudyBean> datas) {  //设置数据
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public interface myOnItemClickListener{   //接口回调
        void itemClickListener(int type,StudyBean bean,int position);
        void itemDeleteListener(StudyBean bean,int position);
    }

    public TongjiAdapter.myOnItemClickListener getMyonitemclicklistener() {
        return myonitemclicklistener;
    }

    public void setMyonitemclicklistener(TongjiAdapter.myOnItemClickListener myonitemclicklistener) {
        this.myonitemclicklistener = myonitemclicklistener;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(TongjiAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StudyBean dataBean = datas.get(position);
        holder.binding.tvCode.setText(String.valueOf(dataBean.getStudyCode()));
        holder.binding.tvName.setText(dataBean.getName());
        holder.binding.tvCourse.setText(dataBean.getCourse());
        holder.binding.tvScore.setText(String.valueOf(dataBean.getScore()));

        boolean courseShow = true;
        if (!TextUtils.isEmpty(courseName)){
            if (courseName.equals("所有课程")){
                courseShow = true;
            }else {
                if (dataBean.getCourse().equals(courseName)){
                    courseShow = true;
                }else {
                    courseShow = false;
                }
            }
        }else {
            courseShow = true;
        }

        boolean scoreShow = true;
        if (!TextUtils.isEmpty(scoreName)){
            if (scoreName.equals("所有成绩")){
                scoreShow = true;
            }else {
                try {
                    int mScore = Integer.parseInt(dataBean.getScore());
                    if (scoreName.equals("90分以上")){
                        if (mScore>89){
                            scoreShow = true;
                        }else {
                            scoreShow = false;
                        }
                    }else if (scoreName.equals("60分以上")){
                        if (mScore>59){
                            scoreShow = true;
                        }else {
                            scoreShow = false;
                        }
                    }else if (scoreName.equals("不合格")){
                        if (mScore<60){
                            scoreShow = true;
                        }else {
                            scoreShow = false;
                        }
                    }
                }catch (NumberFormatException e){
                    scoreShow = false;
                }
            }
        }else {
            scoreShow = true;
        }

        LinearLayout.LayoutParams layoutParams;
        if (courseShow&&scoreShow){
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }else {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        }
        holder.binding.llItem.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        if (datas!=null&&datas.size()>0){
            return datas.size();
        }else {
            return 0;
        }
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public class Baseviewholder extends RecyclerView.ViewHolder{

        public Baseviewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ViewHolder extends TongjiAdapter.Baseviewholder {
        ItemTongjiBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}