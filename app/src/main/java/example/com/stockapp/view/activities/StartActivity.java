package example.com.stockapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.jude.swipbackhelper.SwipeBackHelper;

import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.InventoryEntity;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.tools.LogUtils;
import example.com.stockapp.view.tools.SysInterceptor;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!this.isTaskRoot()) {

            //如果你就放在launcher Activity中话，这里可以直接return了

            Intent mainIntent = getIntent();

            String action = mainIntent.getAction();

            if(mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {

                //finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception

                finish();

                return;

            }

        }
    }

    @Override
    public void initView() {
        ll.setVisibility(View.GONE);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2500);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RequestParam param = new RequestParam();
                        param.put("PageIndex", 1);
                        param.put("PageSize", 5);
                        NetWorkUtil.getUserInfoApi(new SysInterceptor(StartActivity.this))
                                .getOutDateGods(param)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<BaseEntity<InventoryEntity>>() {

                                    @Override
                                    public void onCompleted() {
                                        Log.d("onCompleted", "------->>");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtils.d("onError", "------->>" + e);
                                    }

                                    @Override
                                    public void onNext(BaseEntity<InventoryEntity> baseEntity) {
                                        LogUtils.d("onNext", "------->>" + baseEntity);
                                        boolean isLogin = (Boolean) preferences.getValue(IS_LOGIN, Boolean.class, false);
                                        if (baseEntity.getErrorcode()==0||isLogin){
                                            showActivity(HomeActivity.class);
                                        }else if (baseEntity.getErrorcode()==401){
                                            showActivity(LoginActivity.class);
                                        }
                                        finish();
                                    }
                                });
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
