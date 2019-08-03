package com.qlib.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qlib.R;
import com.qlib.qutils.MySharedPreferences;

/**
 * Created by mzw on 2019/6/26.
 */

public class BaseActivity extends AppCompatActivity {
    protected MySharedPreferences msp;
    protected Context act_instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act_instance = this;
        msp = new MySharedPreferences(this);
    }

    protected void showToast(String content) {
        Toast.makeText(BaseActivity.this, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = findViewById(R.id.title);
        if (tvTitle == null) {
            super.setTitle(title);
        } else {
            tvTitle.setText(title);
        }
    }

    // 设置顶部条栏左返回按钮
    public void showLeftBack() {
        ImageView ivLeft = findViewById(R.id.iv_back);
        if (ivLeft == null) {
            return;
        }

        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(R.drawable.ic_back);
        ivLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();// 调用返回按钮
            }
        });
    }
}
