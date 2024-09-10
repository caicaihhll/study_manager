package com.java.managersystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.java.managersystem.R;
import com.java.managersystem.databinding.ItemStudyBinding;
import com.java.managersystem.entity.StudyBean;

import java.util.ArrayList;
import java.util.List;

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.ViewHolder> {
    protected List<StudyBean> datas;
    private LayoutInflater inflater;
    private Context context;
    private String queryName;
    private boolean showDelete = true;
    private myOnItemClickListener myonitemclicklistener;

    public StudyAdapter(Context context) {
        this.datas =new ArrayList<StudyBean>();
        this.context =context;
        queryName = "";
        this.inflater =LayoutInflater.from(context);//把环境放到填充器里。
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getInflater();//这是用到父类方法，布局填充器。
        View view = inflater.inflate(R.layout.item_study, parent,false);//这里是指小布局。
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setDatas(List<StudyBean> datas) {  //设置数据
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    public interface myOnItemClickListener{   //接口回调
        void itemClickListener(int type,StudyBean bean,int position);
        void itemDeleteListener(StudyBean bean,int position);
    }

    public myOnItemClickListener getMyonitemclicklistener() {
        return myonitemclicklistener;
    }

    public void setMyonitemclicklistener(myOnItemClickListener myonitemclicklistener) {
        this.myonitemclicklistener = myonitemclicklistener;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StudyBean dataBean = datas.get(position);
        holder.binding.tvCode.setText(String.valueOf(dataBean.getStudyCode()));
        holder.binding.tvName.setText(dataBean.getName());
        holder.binding.tvCourse.setText(dataBean.getCourse());
        holder.binding.tvScore.setText(String.valueOf(dataBean.getScore()));

        if (dataBean.getName().contains(queryName)){
            holder.binding.llItem.setVisibility(View.VISIBLE);
        }else {
            holder.binding.llItem.setVisibility(View.GONE);
        }

        if (showDelete){
            holder.binding.tvDelete.setVisibility(View.VISIBLE);
        }else {
            holder.binding.tvDelete.setVisibility(View.INVISIBLE);
        }

        holder.binding.tvCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null!=myonitemclicklistener){
                    myonitemclicklistener.itemClickListener(1,dataBean,position);
                }
                return true;
            }
        });
        holder.binding.tvName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null!=myonitemclicklistener){
                    myonitemclicklistener.itemClickListener(2,dataBean,position);
                }
                return true;
            }
        });
        holder.binding.tvCourse.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null!=myonitemclicklistener){
                    myonitemclicklistener.itemClickListener(3,dataBean,position);
                }
                return true;
            }
        });
        holder.binding.tvScore.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null!=myonitemclicklistener){
                    myonitemclicklistener.itemClickListener(4,dataBean,position);
                }
                return true;
            }
        });


        holder.binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=myonitemclicklistener){
                    myonitemclicklistener.itemDeleteListener(dataBean,position);
                }
            }
        });
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

    public class ViewHolder extends Baseviewholder {
        ItemStudyBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}