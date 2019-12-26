package com.libhttpapimvvmdemo.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.libhttpapimvvmdemo.BR;
import com.libhttpapimvvmdemo.R;
import com.libhttpapimvvmdemo.javaBean.FutureBean;
import com.libhttpapimvvmdemo.javaBean.QTestBean;
import com.qlib.libadapter.AdapterClickListener;
import com.qlib.libadapter.BaseRecyclerAdapter;
import com.qlib.libadapter.BindingViewHolder;
import com.qlib.libadapter.SuperBaseAdapter;

// SuperBaseAdapter 是我自己封装的，大家可以任意改，SuperBaseAdapter
// 使用处，只需要new WeatherAdapter，传入相应的参数即可使用，
// 如果还要实现点击事件，是要重新getClickListener，然后在其内部定义相应的方法，然后绑定到item布局即可,
// 点击事件具体可参考UserInfoAdapter中的，已实现
public class WeatherAdapter extends SuperBaseAdapter<FutureBean>  {

    public WeatherAdapter(Context context, int layoutId) {
        super (context, layoutId);
    }

    // 没有点击事件，不用实现下面此方法
    @Override
    public AdapterClickListener getClickListener(FutureBean item, int postion, ViewDataBinding binding) {
        return null;
    }

}
