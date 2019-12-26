package com.libhttpapimvvmdemo.view.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import com.libhttpapimvvmdemo.databinding.ItemMyWeatherBinding;
import com.libhttpapimvvmdemo.javaBean.QTestBean;
import com.qlib.libadapter.AdapterClickListener;
import com.qlib.libadapter.SuperBaseAdapter;

public class UserInfoAdapter extends SuperBaseAdapter<QTestBean> {
    public UserInfoAdapter(Context context, int layoutId) {
        super (context, layoutId);
    }

    // 不需要点击事件的直接重载，然后放null即可
    @Override
    public AdapterClickListener getClickListener(QTestBean item, int postion, ViewDataBinding binding) {
        // 每个item都有一个点击对象
        return new QClickListener(item, postion, binding);
    }

    // 点击事件逻辑处理
    public  class QClickListener extends AdapterClickListener {
        private QTestBean item;
        private int postion;
        private ItemMyWeatherBinding binding; // 可以直接使用binding.获取要控制的组件,方便灵活，不用亦不影响
        public QClickListener(QTestBean item, int postion, ViewDataBinding binding) {
            this.item = item;
            this.postion = postion;
            this.binding = (ItemMyWeatherBinding) binding;
        }
        public void showToast(View v){
            Toast.makeText (mContext, item.getName () + ";" + binding.btnTest.getText().toString (), Toast.LENGTH_LONG).show ();
        }
    }
}
