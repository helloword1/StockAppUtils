package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.security.MessageDigest;

import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.https.MD5Code;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.tools.Constant;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/29.
 * 登录
 */

public class LoginActivity extends AppCompatActivity {

    private Observer<BaseEntity> obLg;
    private Subscription sb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initData();
    }

    private void initData() {
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
                Log.d("onNext", "------->>" + data);

            }
        };

    }

    private void requset(boolean b) {
        RequestParam param = new RequestParam();
        param.put("usercode", "AppTest");
        param.put("password", "a123456");
//        param.put("page", currentPage);
//        param.put("type", "");
        sb = NetWorkUtil.getTokenApi(netCacheInterceptor)
                .getToken(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obLg);
    }

    private Interceptor netCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl httpUrl = request.url().newBuilder() // add common parameter
                    .build();
            String value = getToken() + "|" + getSign() + "|" + getTime();
            Log.d("intercept", "------->>" + value);
            Request build = request.newBuilder()
                    // add common header
                    .addHeader("APP-AUTH-HEADER", value).url(httpUrl).build();
            Response response = chain.proceed(build);
            return response;
        }
    };

    public void LoginClick(View view) {
        requset(true);
//        startActivity(new Intent(this, HomeActivity.class));
    }

    private String getToken() {
        return "";
    }

    private String getTime() {
      return "" + System.currentTimeMillis();
    }

    private String getSign() {
        return getMD51(getToken() + getTime() + Constant.KEY);
    }

    public String getMD5(String message) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2 将消息变成byte数组
            byte[] input = message.getBytes();

            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }

    public String getMD51(String str1) {
        String md5 = new MD5Code().md5(str1);
//        System.out.println(md5);
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        StringBuilder result = new StringBuilder();
//        byte bytes[] = md.digest(str1.getBytes());
//        for (int i = 0; i < bytes.length; i++) {
//            // 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
//            String str = Integer.toHexString(bytes[i] & 0xFF);
//            if (str.length() == 1) {
//                result.append(0);
//            }
//            result.append(str);
//        }
        return md5;
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    public String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
