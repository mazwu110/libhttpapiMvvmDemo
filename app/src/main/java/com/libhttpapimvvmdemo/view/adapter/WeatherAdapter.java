package com.libhttpapimvvmdemo.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.libhttpapimvvmdemo.BR;
import com.libhttpapimvvmdemo.R;
import com.libhttpapimvvmdemo.javaBean.FutureBean;
import com.qlib.libadapter.BaseRecyclerAdapter;
import com.qlib.libadapter.BindingViewHolder;

// BaseRecyclerAdapter 是我自己封装的，大家可以任意改，只要任意一个适配器继承BaseRecyclerAdapter
// 重新setViewHolder 和 bindData 方法，就能加载您的列表数据了
public class WeatherAdapter extends BaseRecyclerAdapter<FutureBean> {
    public WeatherAdapter(Context context) {
        super(context);
    }

    @Override
    public BindingViewHolder setViewHolder(ViewGroup viewGroup) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater,
                R.layout.item_weather, viewGroup, false);

        return new BindingViewHolder(binding);
    }

    @Override
    public void bindData(BindingViewHolder bindingViewHolder, int positon) {
        FutureBean item = mRecordList.get(positon);
        ViewDataBinding binding = bindingViewHolder.getBinding();
        binding.setVariable(BR.listData, item);
        binding.executePendingBindings(); // 防止闪烁
    }

}
