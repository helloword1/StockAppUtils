package example.com.stockapp.view.activities;

import android.os.SystemClock;
import android.view.View;

import com.jude.swipbackhelper.SwipeBackHelper;

import example.com.stockapp.R;

import static example.com.stockapp.view.tools.Constant.IS_LOGIN;


/**
 * 程序入口界面
 * <p SPkeyConstantUtil.SInitialize 新版本安装的时候要把sp保存的设置为true,
 * 新版本进入继续进入引导页>
 * 修改时间:
 * 修改内容:
 */
public class StartActivity extends BaseActivity {

    @Override
    public void initView() {
        ll.setVisibility(View.GONE);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jumpNextPage();
                    }
                });
            }
        }).start();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_start;
    }

    private void jumpNextPage() {
        boolean isLogin = (Boolean) preferences.getValue(IS_LOGIN, Boolean.class, false);
        if (isLogin) {
            showActivity(HomeActivity.class);
            finish();
            return;
        } else {
            showActivity(LoginActivity.class);
            finish();
        }
    }
}
