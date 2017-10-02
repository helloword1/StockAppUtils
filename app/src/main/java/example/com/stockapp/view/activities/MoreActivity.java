package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/29.
 * 更多
 */

public class MoreActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.more_activity;
    }
    @Override
    protected void initView() {
        title.setText(R.string.more);
    }
}
