package example.com.stockapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected android.widget.FrameLayout ll;
    private android.widget.FrameLayout flbase;
    private LayoutInflater inflation;
    protected android.widget.Button titleBack;
    protected android.widget.TextView title;
    protected android.widget.ImageView titleRight;
    protected View baseLine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.base_content_view);
        this.flbase = (FrameLayout) findViewById(R.id.fl_base_content_view);
        this.ll = (FrameLayout) findViewById(R.id.ll_title);
        baseLine = findViewById(R.id.baseLine);
        inflation = LayoutInflater.from(this);
        initTitleView();
        initContentView();
        initView();
        initData();
    }

    protected  void initView(){

    }protected  void initData(){

    }

    private void initContentView() {
        flbase.addView(inflation.inflate(getContentView(), null));
    }

    public abstract int getContentView();

    private void initTitleView() {
        View titleView = inflation.inflate(R.layout.base_title_view, null);
        this.titleRight = (ImageView) titleView.findViewById(R.id.titleRight);
        this.title = (TextView) titleView.findViewById(R.id.title);
        this.titleBack = (Button) titleView.findViewById(R.id.titleBack);
        ll.addView(titleView);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转到对应的Activity
     */
    public <T> void showActivity(Class<T> activityCls) {
        showActivity(activityCls, null);
    }

    public <T> void showActivity(Class<T> activityCls, Bundle extras) {
        Intent intent = new Intent(this, activityCls);
        if (null == extras) {
            startActivity(intent);
        } else {
            intent.putExtras(extras);
            startActivity(intent);
        }
    }


    public <T> void showActivity(Class<T> activityCls, String key, Serializable obj) {
        Intent intent = new Intent(this, activityCls);
        intent.putExtra(key, obj);
        startActivity(intent);
    }

    public <T> void showActivityByLoginFilter(Class<T> activityCls,
                                              Bundle extras) {
        Intent intent = new Intent(this, activityCls);
        if (null != extras) {
            intent.putExtras(extras);
        }

        showActivity(activityCls, extras);

    }

    /**
     * 跳转到对应的Activity返回的时候可以接受到结果
     *
     * @param activityCls 对应的Activity.class
     * @param requestCode 请求码
     */
    public <T> void showActivityForResult(Class<T> activityCls,
                                          int requestCode) {
        showActivityForResult(activityCls, null, requestCode);
    }

    public <T> void showActivityForResult(Class<T> activityCls,
                                          Bundle extras, int requestCode) {
        Intent intent = new Intent(this, activityCls);
        if (null == extras) {
            startActivityForResult(intent, requestCode);
        } else {
            intent.putExtras(extras);
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 跳转到对应的Activity by Flags的过滤
     *
     * @param activityCls
     * @param flags
     */
    public <T> void showActivityByFlags(Class<T> activityCls, int flags) {
        Intent intent = new Intent(this, activityCls);
        intent.setFlags(flags);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public <T> void showActivityByFlags(Class<T> activityCls, Bundle extras, int flags) {
        Intent intent = new Intent(this, activityCls);
        intent.setFlags(flags);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
