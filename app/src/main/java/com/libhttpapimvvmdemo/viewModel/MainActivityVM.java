package com.libhttpapimvvmdemo.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.view.View;
import com.httpapi.QHttpApi;
import com.httpapi.apiservice.OnHttpApiListener;
import com.httpapi.codeconfig.HttpWhatConfig;
import com.libhttpapimvvmdemo.Constants;
import com.libhttpapimvvmdemo.javaBean.FutureBean;
import com.libhttpapimvvmdemo.javaBean.TestDataBean;
import com.libhttpapimvvmdemo.javaBean.WeatherBean;
import com.libhttpapimvvmdemo.view.adapter.WeatherAdapter;
import com.qlib.base.BaseViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 所有的数据获取我统一用了泛型，大家可以看下我对MVVM中MEDEL的封装，及里面对
//retrofit2 rxjava2, ApiService 的封装。可以根据自身情况进行修改，但是这里只需要一个泛型
//model就行了，避免创建太多的model 代码文件太多不好维护,但是VM是需要多个的，
// 就像每一个界面需要一个Activity一样，有问题发我邮箱或者加Q吧 QQ:315145320
public class MainActivityVM extends BaseViewModel implements OnHttpApiListener {
    QClickListener qClickListener;
    WeatherAdapter mAdapter;
    int index = 0;

    // 动态数据绑定
    MutableLiveData<TestDataBean> testData;

    public MutableLiveData<TestDataBean> getTestData() {
        return testData;
    }

    // 还可以在构造函数中增加你乐意的参数或者控件传过来
    public MainActivityVM(Context context, WeatherAdapter adapter) {
        this.mContext = context;
        this.mAdapter = adapter;
        qClickListener = new QClickListener();
        testData = new MutableLiveData<>(); // 记得初始化，要不会报错哦
    }

    // 返回给Activity绑定点击事件
    public QClickListener getQClickListener() {
        return qClickListener;
    }

    public class QClickListener {
        // key-value get 获取后台数据
        public void doGet(View view) {
            Map<String, Object> params = new HashMap<>();
            params.put("city", "北京");
            params.put("key", "0132423b3e085efed24b7b8f00d83a91");
            // 第三个参数，需要用到哪个类解析数据结果就传哪个类进去就行，这里采用了泛型解析
            QHttpApi.doGet(Constants.getWeather, params, WeatherBean.class, HttpWhatConfig.CODE_10, MainActivityVM.this);
           // QHttpApi.doStrGet(Constants.getWeather, params, HttpWhatConfig.CODE_10, MainActivityVM.this);
        }

        // key-value post获取后台数据
        public void doPost(View view) {
            // 另外有QHttpApi.doStrGet方法可用，此返回是后台返回什么，解析出就直接返回给您，需要您自己接受了解析，包括CODE等都返回来了
            Map<String, Object> params = new HashMap<>();
            params.put("city", "上海");
            params.put("key", "0132423b3e085efed24b7b8f00d83a91");
            // 第三个参数，需要用到哪个类解析数据结果就传哪个类进去就行，这里采用了泛型解析
            //
            QHttpApi.doPost(Constants.getWeather, params, WeatherBean.class, HttpWhatConfig.CODE_11, MainActivityVM.this);
        }

        //post json格式的参数获取后台数据
        public void doJsonPost(View view) {
        }

        // 设置数据，可以通过网络获取
        public void getUserName(View view) {
            TestDataBean bean = new TestDataBean();
            index += 1;
            bean.setUsreName("张三" + index);

            // 更新数据，界面上也会同步更新
            testData.setValue(bean);
        }
    }

    @Override
    public void onSuccess(int what, Object data) {
        switch (what) {
            case HttpWhatConfig.CODE_10: {
                // 使用请求数据的时候的class反解析就行
                WeatherBean bean = (WeatherBean) data;
                ArrayList<FutureBean> list = bean.getFuture();
                // 清除数据，然后刷新, 如果您做的是分页，可以使用 mAdapter.addMoreRecordList(list);
                mAdapter.addRecordList(list);
                break;
            }
            // 数据格式一样 就拷贝上面的解析了
            case HttpWhatConfig.CODE_11:
                // 使用请求数据的时候的class反解析就行
                WeatherBean bean = (WeatherBean) data;
                ArrayList<FutureBean> list = bean.getFuture();
                // 清除数据，然后刷新, 如果您做的是分页，可以使用 mAdapter.addMoreRecordList(list);
                mAdapter.addRecordList(list);
                break;
        }
    }

    @Override
    public void onFailure(int what, String msg, int code) {
        showToast(msg);
    }

    public void freeObject() {
        if (qClickListener != null)
            qClickListener = null;

        if (mAdapter != null)
            mAdapter = null;
    }
}
