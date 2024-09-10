package com.java.managersystem;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;

import com.java.managersystem.databinding.BgAvatarBinding;

public class MyPopChangeBg {
    private final LayoutInflater layoutInflater;
    private Activity context;
    private BgAvatarBinding bgAvatarBinding;

    private PopupWindow popupWindow;

    public MyPopChangeBg(Context context) {
        this.context = (Activity) context;
        this.layoutInflater = LayoutInflater.from(context);
        popupUpWindowsInit();

    }

    public void show(View parentView){
        popupWindow.setAnimationStyle(R.style.TopFunctionPopAnim);
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f);
    }

    private void popupUpWindowsInit(){
        View view = layoutInflater.inflate(R.layout.bg_avatar, null);
        bgAvatarBinding = DataBindingUtil.bind(view);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.MyAnimBottom);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });
    }

    private void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams attributes = this.context.getWindow().getAttributes();
        attributes.alpha = alpha;
        context.getWindow().setAttributes(attributes);

    }

    public BgAvatarBinding getBgAvatarBinding() {
        return bgAvatarBinding;
    }

    public void setBgAvatarBinding(BgAvatarBinding bgAvatarBinding) {
        this.bgAvatarBinding = bgAvatarBinding;
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }
}
