package com.qlib.libadapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.qlib.BR;

//SuperBaseAdapter 所有适配器基类
public abstract class SuperBaseAdapter<T> extends BaseRecyclerAdapter<T> {
    private int layoutId;

    public SuperBaseAdapter(Context context, int layoutId) {
        super (context);
        this.layoutId = layoutId;
    }

    @Override
    public BindingViewHolder setViewHolder(ViewGroup viewGroup) {
        ViewDataBinding binding = DataBindingUtil.inflate (mLayoutInflater, layoutId, viewGroup, false);
        return new BindingViewHolder (binding);
    }

    @Override
    public void bindData(BindingViewHolder bindingViewHolder, int positon) {
        T item = mRecordList.get (positon);
        ViewDataBinding binding = bindingViewHolder.getBinding ();
        binding.setVariable (BR.item, item); // item布局中名称要和这个设置的一致，否则这个基类不能用,或者自己改item为布局中的名称，以下类似
        binding.setVariable (BR.click, getClickListener (item, positon, binding)); // 设置点击事件,item布局中名称要和这个设置的一致，否则这个基类不能用
        binding.executePendingBindings (); // 防止闪烁
    }

    //由子类实现点击事件,然后子类自己在布局中绑定即可
    public abstract AdapterClickListener getClickListener(T item, int postion, ViewDataBinding binding);
}

