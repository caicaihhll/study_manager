package com.java.managersystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.java.managersystem.R;
import com.java.managersystem.custom.PUtil;
import com.java.managersystem.databinding.ItMyBgBinding;

import java.util.ArrayList;
import java.util.List;

public class MyBgAdapter extends RecyclerView.Adapter<MyBgAdapter.ViewHolder> {
    protected List<Integer> datas;
    private LayoutInflater inflater;
    private Context context;
    private myOnItemClickListener myonitemclicklistener;

    public MyBgAdapter(Context context) {
        this.datas =new ArrayList<Integer>();
        this.context =context;
        this.inflater =LayoutInflater.from(context);//把环境放到填充器里。
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getInflater();//这是用到父类方法，布局填充器。
        View view = inflater.inflate(R.layout.it_my_bg, parent,false);//这里是指小布局。
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setDatas(List<Integer> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public interface myOnItemClickListener{
        void itemClickListener(int position,Integer bean);
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
        Integer dataBean = datas.get(position);

        int mWidth = (int) ((PUtil.getScreenW(context) - PUtil.dip2px(context,30))/4 - PUtil.dip2px(context,30));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, PUtil.dip2px(context,75));
        layoutParams.leftMargin = PUtil.dip2px(context,15);
        layoutParams.rightMargin = PUtil.dip2px(context,15);
        layoutParams.bottomMargin = PUtil.dip2px(context,18);
        holder.binding.rView.setLayoutParams(layoutParams);

        holder.binding.mrvBg.setImageResource(dataBean);

        holder.binding.mrvBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=myonitemclicklistener){
                    myonitemclicklistener.itemClickListener(position,dataBean);
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
        ItMyBgBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}