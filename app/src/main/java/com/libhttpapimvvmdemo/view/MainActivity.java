package com.libhttpapimvvmdemo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.libhttpapimvvmdemo.R;
import com.libhttpapimvvmdemo.databinding.ActivityMainBinding;
import com.libhttpapimvvmdemo.view.adapter.WeatherAdapter;
import com.libhttpapimvvmdemo.viewModel.MainActivityVM;
import com.qlib.base.BaseActivity;

// 所谓的MVVM模式，M就是获取数据的一个独立类，比如做实际的网络请求等，
// V就是界面显示的一个类，说白了就是MainActivity类似的界面，VM其实就是
// 从MainActivity分离出来，然后用来处理数据罢了,注意如果在VM中引用到了Activity中的视图控件等，记得释放
// 这里使用谷歌的AAC框架，其实就是典型的MVVM框架来解析下如何使用
// 遇到任何问题，可以加Q一起探讨交流  QQ:315145320 或者发邮箱
public class MainActivity extends BaseActivity {
    // 注意： 必须要在布局的根节点上增加<layout></layout>，要不下面这个家伙出不来
    // private 多余的，默认就是，没必要写了，除了private外，其他访问权限需要写哦
    ActivityMainBinding mBinding;
    MainActivityVM VM;
    WeatherAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载界面
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    // 其他初始化
    void init() {
        // 注意 mAdapter 顺序，有列表的时候才需要
        mAdapter = new WeatherAdapter(this);
        VM = new MainActivityVM(this, mAdapter);
        // 注意 lambda表达式不支持Java8以下的，所以JDK必须是8及以上的哦，要不会报错
        VM.getTestData().observe(this, data -> mBinding.setUserName(data));
        // 设置监听
        mBinding.setQClickListener(VM.getQClickListener());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(layoutManager); // 设置recyclerview布局方式
        mBinding.recyclerView.setAdapter(mAdapter); //给recyclerview 添加适配器

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VM.freeObject();
    }
}
