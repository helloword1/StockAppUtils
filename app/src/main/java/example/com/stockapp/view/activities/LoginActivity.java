package example.com.stockapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/29.
 * 登录
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void LoginClick(View view) {
        startActivity(new Intent(this,HomeActivity.class));
    }
}