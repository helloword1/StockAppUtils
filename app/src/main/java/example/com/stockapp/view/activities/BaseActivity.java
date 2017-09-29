package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private android.widget.FrameLayout ll;
    private android.widget.FrameLayout flbase;
    private LayoutInflater inflation;
    protected android.widget.Button titleBack;
    protected android.widget.TextView title;
    protected android.widget.Button titleRight;

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
        inflation = LayoutInflater.from(this);
        initTitleView();
        initContentView();
    }

    private void initContentView() {
        flbase.addView(inflation.inflate(getContentView(), null));
    }

    public abstract int getContentView();

    private void initTitleView() {
        View titleView = inflation.inflate(R.layout.base_title_view, null);
        this.titleRight = (Button) titleView.findViewById(R.id.titleRight);
        this.title = (TextView) titleView.findViewById(R.id.title);
        this.titleBack = (Button) titleView.findViewById(R.id.titleBack);
        ll.addView(titleView);
    }


}
