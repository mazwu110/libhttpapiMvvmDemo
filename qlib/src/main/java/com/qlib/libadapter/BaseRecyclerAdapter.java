package com.qlib.libadapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlib.R;
import com.qlib.base.QApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzw on 2019/7/25.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BindingViewHolder>
        implements BaseViewHolder.OnNotifyChangeListener {
    public static Context mContext;
    public LayoutInflater mLayoutInflater;
    public List<T> mRecordList = new ArrayList();
    public static final int TYPE_CONTENT = 1;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return setViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        bindData(holder, position);
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }

    //空布局类型返回1，正常布局类型返回0
    @Override
    public int getItemViewType(int position) {
        return TYPE_CONTENT;
    }

    @Override
    public void onNotify() {
        //提供给BaseViewHolder方便刷新视图
        notifyDataSetChanged();
    }

    //添加第一页数据
    public void addRecordList(List<T> datas) {
        mRecordList.clear();
        if (null != datas) {
            mRecordList.addAll(datas);
        }

        notifyDataSetChanged();
    }

    // 添加更多数据
    public void addMoreRecordList(List<T> datas) {
        if (null != datas) {
            mRecordList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void clearDatas() {
        mRecordList.clear();
        notifyDataSetChanged();
    }


    public T getItem(int position) {
        if (mRecordList != null && position < mRecordList.size()) {
            return mRecordList.get(position);
        } else {
            return null;
        }
    }

    //删除单条数据
    public void deletItem(T data) {
        mRecordList.remove(data);
        notifyDataSetChanged();
    }

    /**
     * 子类重写实现自定义ViewHolder
     */
    public abstract BindingViewHolder setViewHolder(ViewGroup parent);

    //用给定的 data 对 holder 的 view 进行赋值,交给子类自己实现
    public abstract void bindData(BindingViewHolder bindingViewHolder, int positon);


    // 图片加载
    @BindingAdapter({"app:imageUrl"})
    public static void loadImageFromUrl(ImageView imageView, String url) {
        if (url.startsWith("http") || url.startsWith("HTTP")) { // 网络图片
            QApp.mImageLoader.displayNetImage(imageView, url);
        } else { // base64图片
            //QApp.mImageLoader.addBase64Image(url, imageView);
        }
    }

    // 解决为图片赋值本地资源时 运行效果不见图片的问题
    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter("android:textColor")
    public static void setTextColor(TextView view, int resId) {
        view.setTextColor(mContext.getResources().getColor(resId));
    }
}
