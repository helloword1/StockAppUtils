package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.graphs.CleanableEditText;
import example.com.stockapp.view.tools.SAApplication;
import example.com.stockapp.view.tools.SysInterceptor;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static example.com.stockapp.view.tools.Constant.CURRENT_USER;
import static example.com.stockapp.view.tools.Constant.IS_LOGIN;
import static example.com.stockapp.view.tools.Constant.TOKEN;

/**
 * Created by Administrator on 2017/9/29.
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Observer<BaseEntity> obLg;
    private Subscription sb;
    private CleanableEditText tvLoginUser;
    private EditText tvLoginPassword;
    private String userStr;
    private String psdStr;
    private double canLookIndex = 2;
    private ImageView tvLoginPasswordDelete;
    private ImageView tvLoginUserDelete;
    private double exitTime;
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void initData() {
        ll.setVisibility(View.GONE);
        tvLoginUser = (CleanableEditText) findViewById(R.id.tvLoginUser);
        tvLoginPassword = (EditText) findViewById(R.id.tvLoginPassword);
        tvLoginUser.setText("AppTest");
        tvLoginPassword.setText("a123456");
        tvLoginPasswordDelete = (ImageView) findViewById(R.id.tvLoginPasswordDelete);
        tvLoginUserDelete = (ImageView) findViewById(R.id.tvLoginUserDelete);
        login = (Button) findViewById(R.id.btn_confir);
        login.setOnClickListener(this);
        tvLoginPasswordDelete.setOnClickListener(this);
        tvLoginUserDelete.setOnClickListener(this);
        tvLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        boolean islogin = (Boolean) preferences.getValue(IS_LOGIN, Boolean.class, false);
        if (islogin) {
            showActivity(HomeActivity.class);
            return;
        }
        obLg = new Observer<BaseEntity>() {

            @Override
            public void onCompleted() {
                Log.d("onCompleted", "------->>");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError", "------->>" + e);
            }

            @Override
            public void onNext(BaseEntity string) {
                String data = string.getData().toString();
                if (NotNull.isNotNull(data)) {
                    preferences.putValue(TOKEN, data);
                    preferences.putValue(IS_LOGIN, true);
                    preferences.putValue(CURRENT_USER, tvLoginUser.getText().toString());
                    Log.d("onNext", "------->>" + data);
                    showActivity(HomeActivity.class);
//                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        };

    }



    @Override
    public int getContentView() {
        return R.layout.login_activity;
    }

    private void requset(boolean b) {
        if (isVail()) {
            showProgressDialog();
            RequestParam param = new RequestParam();
            param.put("usercode", userStr);
            param.put("password", psdStr);
            sb = NetWorkUtil.getTokenApi(new SysInterceptor(this))
                    .getToken(param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(obLg);
        }

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                // TODO 退出客户端
                // 退出
                ((SAApplication) getApplication()).exit();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    private boolean isVail() {
        userStr = tvLoginUser.getText().toString();
        psdStr = tvLoginPassword.getText().toString();
        if (!NotNull.isNotNull(userStr)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!NotNull.isNotNull(psdStr)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLoginPasswordDelete:
                if (canLookIndex == 1) {
                    tvLoginPasswordDelete.setImageResource(R.mipmap.login_password_open);
                    tvLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    canLookIndex = 2;
                } else {
                    tvLoginPasswordDelete.setImageResource(R.mipmap.login_password_close);
                    tvLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    canLookIndex = 1;
                }
                break;
            case R.id.tvLoginUserDelete:
                break;
            case R.id.btn_confir:
                requset(true);
                break;
        }
    }
}
