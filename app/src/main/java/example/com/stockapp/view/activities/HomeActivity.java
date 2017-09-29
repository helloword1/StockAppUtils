package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/29.
 */

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        //初始化titlebar
        titleBack.setVisibility(View.GONE);
        title.setText(R.string.inventorySystem);
    }


    @Override
    public int getContentView() {
        return R.layout.home_activity;
    }

}
