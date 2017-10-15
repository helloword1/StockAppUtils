package example.com.stockapp.https;

import android.content.Context;
import android.content.Intent;

import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.view.activities.BaseActivity;
import example.com.stockapp.view.activities.LoginActivity;
import example.com.stockapp.view.graphs.MyToast;
import rx.Observer;

import static example.com.stockapp.view.tools.Constant.IS_LOGIN;

/**
 * Created by Administrator on 2017/10/15.
 */

public abstract class DefaultObserver<T> implements Observer<T> {
    private Context context;
    private final BaseActivity baseBctivity;

    public DefaultObserver(Context context) {
        this.context = context;
        baseBctivity = (BaseActivity) this.context;
    }

    @Override
    public void onNext(T response) {
        if (((BaseEntity) response).getErrorcode() == 401) {
            baseBctivity.preferences.putValue(IS_LOGIN, false);
            baseBctivity.showActivityByFlags(LoginActivity.class, Intent.FLAG_ACTIVITY_SINGLE_TOP);
            MyToast.showToastCustomerStyleText(baseBctivity, "登录过期");
        }
        baseBctivity.dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
        baseBctivity.dismissDialog();
    }

    @Override
    public void onCompleted() {

    }
}
